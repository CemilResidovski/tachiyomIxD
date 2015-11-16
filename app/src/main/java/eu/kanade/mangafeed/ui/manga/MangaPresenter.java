package eu.kanade.mangafeed.ui.manga;

import android.os.Bundle;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import eu.kanade.mangafeed.data.database.DatabaseHelper;
import eu.kanade.mangafeed.data.database.models.Manga;
import eu.kanade.mangafeed.ui.base.presenter.BasePresenter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MangaPresenter extends BasePresenter<MangaActivity> {

    @Inject DatabaseHelper db;

    private long mangaId;
    private Manga manga;

    private static final int DB_MANGA = 1;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableLatestCache(DB_MANGA,
                () -> getDbMangaObservable()
                        .doOnNext(manga -> this.manga = manga),
                (view, manga) -> {
                    view.setManga(manga);
                    EventBus.getDefault().postSticky(manga);
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Avoid fragments receiving wrong manga
        EventBus.getDefault().removeStickyEvent(Manga.class);
    }

    private Observable<Manga> getDbMangaObservable() {
        return db.getManga(mangaId).createObservable()
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void queryManga(long mangaId) {
        this.mangaId = mangaId;
        start(DB_MANGA);
    }

}