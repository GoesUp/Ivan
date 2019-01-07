package si.gabrovsek.gregor.ivan

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.KeyEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_search.*
import org.jsoup.Jsoup

class SearchActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFormat(PixelFormat.RGBA_8888)

        setContentView(R.layout.activity_search)
        magicText.alpha = 0.0f

        editText.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                if (editText.text.isEmpty()) {
                    Toast.makeText(this, "Poizvedba ne sme biti prazna.", Toast.LENGTH_SHORT).show()
                } else {

                    val appearingAnimation = magicText.animate()
                    appearingAnimation.duration = 1000
                    appearingAnimation.alpha(1.0f)
                    appearingAnimation.start()

                    val preparedSearchQuery = editText.text.toString().replace(" ", "+").toLowerCase()
                    val fullURL = "https://fran.si/iskanje?Query=$preparedSearchQuery"

                    RetrieveSearchResults(fullURL, this).execute()

                }
                true
            } else {
                false
            }
        }
    }

    private class RetrieveSearchResults(
        val s: String,
        val context: Context
    ) : AsyncTask<String, Int, String>() {

        override fun doInBackground(vararg params: String?): String? {

            val basicResponse = Jsoup.connect(s)
            val stCode = basicResponse.response().statusCode()
            publishProgress(stCode)
            if (stCode != 200 && stCode != 0) {
                return "#ERROR#"
            }

            var intent = Intent(context, ResultActivity::class.java).apply {
                putExtra("results", basicResponse.get().body().toString())
            }

            startActivity(context, intent, Bundle())
            return ""
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)

            if (values[0] != 200 && values[0] != 0) {
                Toast.makeText(context, "Kaže, da Fran trenutno ni dosegljiv.", Toast.LENGTH_SHORT).show()
                println("Status code: " + values[0])
            }

        }
    }

    override fun onResume() {
        super.onResume()

        val disappearingAnimation = magicText.animate()
        disappearingAnimation.duration = 0
        disappearingAnimation.alpha(0.0f)
        disappearingAnimation.start()
    }


}
