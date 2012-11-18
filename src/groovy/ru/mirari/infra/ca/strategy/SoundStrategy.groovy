package ru.mirari.infra.ca.strategy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.mirari.infra.FileStorageService
import ru.mirari.infra.ca.Atom
import ru.mirari.infra.ca.AtomStrategy
import ru.mirari.infra.file.BasicFileHolder
import ru.mirari.infra.file.FileHolder

/**
 * @author alari
 * @since 11/18/12 10:36 PM
 */
@Component
class SoundStrategy extends AtomStrategy {
    private final Map<String, String> TYPES = [
            mpeg: "sound.mp3",
            webm: "sound.webm",
            ogg: "sound.ogg"
    ]

    @Autowired
    FileStorageService fileStorageService
    String filesBucket = "mirarisounds"

    private FileHolder getFileHolder(Atom atom) {
        FileHolder holder = new BasicFileHolder(atom.id, filesBucket)
        if (atom.sounds) holder.fileNames = atom.sounds.values()
        holder
    }

    @Override
    boolean isContentSupported(Atom.Push data) {
        if (!data.file) return false
        assert data.fileType != null
        if (!data.fileType.startsWith("audio/")) return false
        for (t in TYPES.keySet()) {
            if (data.fileType.endsWith("/${t}")) return true
        }
        false
    }

    @Override
    void setContent(Atom atom, Atom.Push data) {
        if (data.file) {
            FileHolder holder = getFileHolder(atom)
            String type = data.fileType.substring(data.fileType.indexOf("/") + 1)
            String fileName = TYPES.get(type)
            if (!fileName) throw new IllegalArgumentException("Wrong data object given")

            fileStorageService.store(data.file, holder, fileName)

            if (!atom.sounds) atom.sounds = [:]
            atom.sounds.put(type, fileName)
            atom.title = data.originalFilename.substring(0, data.originalFilename.lastIndexOf("."))
        }
    }

    @Override
    void delete(Atom atom) {
        if (atom.sounds && atom.sounds.size()) {
            FileHolder holder = getFileHolder(atom)
            fileStorageService.delete(holder)
            atom.sounds = null
        }
    }
}
