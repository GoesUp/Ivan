package si.gabrovsek.gregor.ivan

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toolbar
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
        var srLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = srLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = srAdapter

        prepareSearchData()
    }

    private fun prepareSearchData() {
        var parentResult = intent.getStringExtra("results")

        var results = Jsoup.parse(parentResult).select("div.list-group-item")
        for (r in results) {

            var properTitle = r.select("span.font_xlarge a").text()
            r.select("span.font_xlarge a").remove()
            var properHeader = r.select("span[data-group=header]").text()
            r.select("span[data-group=header]").remove()
            var properName = r.select("span.dictionary-name").text()
            r.select("span.dictionary-name").remove()
            var resultEntry = ResultEntry(properTitle, properHeader, r.select("span").text(), properName)
            resultEntryList.add(resultEntry)
        }

    }


}
