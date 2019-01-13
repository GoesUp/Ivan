package si.gabrovsek.gregor.ivan

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.jsoup.Jsoup

class ResultActivity : Activity() {
    var resultEntryList = mutableListOf<ResultEntry>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var srAdapter: SearchResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        recyclerView = findViewById(R.id.searchResultsEntries)
        srAdapter = SearchResultsAdapter(resultEntryList)
        val srLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = srLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = srAdapter

        prepareSearchData()
    }

    private fun prepareSearchData() {
        val parentResult = intent.getStringExtra("results")
        val results = Jsoup.parse(parentResult).select("div.list-group-item")

        var unwantedDictionaries = arrayOf("Jezikovna", "Terminološka", "Besedje16", "SLA 1", "SLA 2")

        for (r in results) {

            val properTitle = r.select("span.font_xlarge a").text()
            r.select("span.font_xlarge a").remove()

            val properHeader = r.select("div.entry-content>span[data-group=header]").text()
            r.select("div.entry-content>span[data-group=header]").remove()

            val properName = r.select("span.dictionary-name").text()
            if (properName in unwantedDictionaries) continue
            r.select("span.dictionary-name").remove()

            // Removes the subscripted numbers next to entry names. See: "pet".
            r.select("div.entry-content>span.font_xsmal").remove()
            r.select("div.entry-content>span.color_orange").remove()

            val dictionaryText = r.select("div.entry-content>span").text()
            if (dictionaryText == "") continue


            val resultEntry = ResultEntry(properTitle, properHeader, properName, dictionaryText)
            resultEntryList.add(resultEntry)
        }

    }


}
