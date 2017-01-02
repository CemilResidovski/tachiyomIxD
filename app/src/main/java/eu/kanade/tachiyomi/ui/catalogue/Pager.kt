package eu.kanade.tachiyomi.ui.catalogue

import eu.kanade.tachiyomi.data.source.model.MangasPage
import rx.Observable
import rx.subjects.PublishSubject

/**
 * A general pager for source requests (latest updates, popular, search)
 */
abstract class Pager {

    protected var lastPage: MangasPage? = null

    protected val results = PublishSubject.create<MangasPage>()

    fun results(): Observable<MangasPage> {
        return results.asObservable()
    }

    fun hasNextPage(): Boolean {
        return lastPage == null || !lastPage?.nextPageUrl.isNullOrBlank()
    }

    abstract fun requestNext(): Observable<MangasPage>
}