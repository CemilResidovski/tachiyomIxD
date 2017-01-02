package eu.kanade.tachiyomi.data.source.model

import eu.kanade.tachiyomi.data.database.models.Manga
import java.io.Serializable

interface SManga : Serializable {

    var url: String

    var title: String

    var artist: String?

    var author: String?

    var description: String?

    var genre: String?

    var status: Int

    var thumbnail_url: String?

    var initialized: Boolean

    fun copyFrom(other: Manga) {
        if (other.author != null)
            author = other.author

        if (other.artist != null)
            artist = other.artist

        if (other.description != null)
            description = other.description

        if (other.genre != null)
            genre = other.genre

        if (other.thumbnail_url != null)
            thumbnail_url = other.thumbnail_url

        status = other.status

        initialized = true
    }

    companion object {
        const val UNKNOWN = 0
        const val ONGOING = 1
        const val COMPLETED = 2
        const val LICENSED = 3

        fun create(): SManga {
            return SMangaImpl()
        }
    }

}