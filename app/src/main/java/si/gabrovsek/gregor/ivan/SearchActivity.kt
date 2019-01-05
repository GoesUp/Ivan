package si.gabrovsek.gregor.ivan

import android.app.Activity
import android.graphics.PixelFormat
import android.os.AsyncTask
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_search.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class SearchActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFormat(PixelFormat.RGBA_8888)

        setContentView(R.layout.activity_search)

        editText.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                if (editText.text.isEmpty()) {
                    Toast.makeText(this, "Poizvedba ne sme biti prazna.", Toast.LENGTH_SHORT).show()
                } else {

                    val preparedSearchQuery = editText.text.toString().replace(" ", "+").toLowerCase()
                    val fullURL = "https://fran.si/iskanje?Query=" + preparedSearchQuery

                    var results = RetrieveSearchResults(fullURL).execute().get()

                    val textView: TextView = findViewById(R.id.textView3) as TextView


                    for (r in results) {
                        var itemTitle = r.select("span.font_xlarge a").text()
                        textView.setText(textView.text.toString() + itemTitle + "\n")
                    }

                }
                true
            } else {
                false
            }
        }
    }

    private class RetrieveSearchResults(var s: String) : AsyncTask<String, Void, Elements>() {
        override fun doInBackground(vararg params: String?): Elements? {
            val doc = Jsoup.connect(s.toString()).get()
            val items = doc.body().select("div.entry-content")
            return items
        }
    }


}
