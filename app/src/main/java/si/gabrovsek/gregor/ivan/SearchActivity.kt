package si.gabrovsek.gregor.ivan

import android.app.Activity
import android.content.Intent
import android.graphics.PixelFormat
import android.os.AsyncTask
import android.os.Bundle
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

                    val anim = magicText.animate().alpha(1.0f).setDuration(200)
                    Thread.sleep(1000)

                    val preparedSearchQuery = editText.text.toString().replace(" ", "+").toLowerCase()
                    val fullURL = "https://fran.si/iskanje?Query=$preparedSearchQuery"

                    var results = RetrieveSearchResults(fullURL).execute().get()

                    if (results == "#ERROR#") {
                        Toast.makeText(this, "Kaže, da Fran trenutno ni dosegljiv.", Toast.LENGTH_SHORT).show()
                    } else {
                        var intent = Intent(this, ResultActivity::class.java).apply {
                            putExtra("results", results)
                        }
                        startActivity(intent)
                    }

                    magicText.animate().alpha(0.0f)

                }
                true
            } else {
                false
            }
        }
    }

    private class RetrieveSearchResults(var s: String) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String? {
            val basicResponse = Jsoup.connect(s)
            val actualResponse = basicResponse.response()
            if (actualResponse.statusCode() != 200 && actualResponse.statusCode() != 0) {
                println("Status code: " + actualResponse.statusCode())
                return "#ERROR#"
            }


            return basicResponse.get().body().toString()
        }
    }


}
