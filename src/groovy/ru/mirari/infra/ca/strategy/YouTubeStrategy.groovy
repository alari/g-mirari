package ru.mirari.infra.ca.strategy

@Grab("org.apache.httpcomponents:httpclient:4.2.1")
import org.apache.http.client.utils.URLEncodedUtils
import org.springframework.stereotype.Component
import ru.mirari.infra.ca.Atom
import ru.mirari.infra.ca.AtomStrategy

/**
 * @author alari
 * @since 11/18/12 12:54 AM
 */
@Component
class YouTubeStrategy extends AtomStrategy {
    @Override
    boolean isContentSupported(Atom.Push data) {
        (data.url && (data.url.host == "youtu.be" || (data.url.host == "www.youtube.com" && data.url.path == "/watch")))
    }

    @Override
    void setContent(Atom atom, Atom.Push data) {
        if (data.url.host == "youtu.be") {
            // http://youtu.be/zi3AqicZgEk
            atom.externalId = data.url.path.substring(1)
        } else if (data.url.host == "www.youtube.com" && data.url.path == "/watch") {
            // http://www.youtube.com/watch?v=zi3AqicZgEk&feature=g-logo&context=G2e33cabFOAAAAAAABAA
            atom.externalId = URLEncodedUtils.parse(data.url.toURI(), "UTF-8").find {it.name == "v"}.value
        }
    }
}
