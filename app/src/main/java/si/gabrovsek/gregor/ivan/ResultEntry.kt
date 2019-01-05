package si.gabrovsek.gregor.ivan

class ResultEntry {
    private var title: String? = null
    private var additionalHeaderInfo: String? = null
    private var dictionaryText: String? = null
    private var dictionaryName: String? = null

    constructor()

    constructor(title: String, additionalHeaderInfo: String, dictionaryText: String, dictionaryName: String) {
        this.title = title
        this.additionalHeaderInfo = additionalHeaderInfo
        this.dictionaryText = dictionaryText
        this.dictionaryName = dictionaryName
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(t: String) {
        this.title = t
    }

    fun getAdditionalHeaderInfo(): String? {
        return additionalHeaderInfo
    }

    fun setAdditionalHeaderInfo(i: String) {
        this.additionalHeaderInfo = i
    }

    fun getDictionaryText(): String? {
        return dictionaryText
    }

    fun setDictionaryText(d: String) {
        this.dictionaryText = d
    }

    fun getDictionaryName(): String? {
        return dictionaryName
    }

    fun setDictionaryName(d: String) {
        this.dictionaryName = d
    }
}





