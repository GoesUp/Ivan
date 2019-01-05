package si.gabrovsek.gregor.ivan

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SearchResultsAdapter(private val resultEntryList: List<ResultEntry>) :
    RecyclerView.Adapter<SearchResultsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var additionalHeaderInfo: TextView
        var dictionaryText: TextView
        var dictionaryName: TextView

        init {
            title = view.findViewById<View>(R.id.entryTitle) as TextView
            additionalHeaderInfo = view.findViewById<View>(R.id.additionalHeaderInfo) as TextView
            dictionaryText = view.findViewById<View>(R.id.dictionaryName) as TextView
            dictionaryName = view.findViewById<View>(R.id.dictionaryText) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.result_entry, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entry = resultEntryList[position]
        holder.title.text = entry.getTitle()
        holder.additionalHeaderInfo.text = entry.getAdditionalHeaderInfo()
        holder.dictionaryText.text = entry.getDictionaryText()
        holder.dictionaryName.text = entry.getDictionaryName()
    }

    override fun getItemCount(): Int {
        return resultEntryList.size
    }
}
