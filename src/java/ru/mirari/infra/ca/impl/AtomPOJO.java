package ru.mirari.infra.ca.impl;

import ru.mirari.infra.ca.Atom;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Map;

/**
 * @author alari
 * @since 11/13/12 11:47 PM
 */
public class AtomPOJO implements Atom {
    private String title;
    private String id;
    private String type;

    private String text;
    private String externalId;

    private Map<String, String> sounds;
    private Map<String, String> images;

    private Date dateCreated;
    private Date lastUpdated;

    static public class Push extends AtomPOJO implements Atom.Push {
        private File file;
        private String originalFilename;
        private String externalUrl;

        private URL url;
        private String fileType;

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public String getOriginalFilename() {
            return originalFilename;
        }

        public void setOriginalFilename(String originalFilename) {
            this.originalFilename = originalFilename;
        }

        public String getExternalUrl() {
            return externalUrl;
        }

        public void setExternalUrl(String externalUrl) {
            this.externalUrl = externalUrl;
        }

        public URL getUrl() {
            return url;
        }

        public void setUrl(URL url) {
            this.url = url;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Map<String, String> getSounds() {
        return sounds;
    }

    public void setSounds(Map<String, String> sounds) {
        this.sounds = sounds;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
