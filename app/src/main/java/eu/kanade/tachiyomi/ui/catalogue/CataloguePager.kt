package eu.kanade.tachiyomi.ui.catalogue

import eu.kanade.tachiyomi.data.source.CatalogueSource
import eu.kanade.tachiyomi.data.source.model.FilterList
import eu.kanade.tachiyomi.data.source.model.MangasPage
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

open class CataloguePager(val source: CatalogueSource, val query: String, val filters: FilterList) : Pager() {

    override fun requestNext(): Observable<MangasPage> {
        val page = currentPage

        val observable = if (query.isBlank() && filters.isEmpty())
            source.fetchPopularManga(page)
        else
            source.fetchSearchManga(page, query, filters)

        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { onPageReceived(it) }
    }

}