package ru.mirari.infra.ca;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Map;

/**
 * @author alari
 * @since 11/13/12 11:41 PM
 */
public interface Atom {
    public String getTitle();

    public void setTitle(String title);

    public String getId();

    public void setId(String id);

    public String getType();

    public void setType(String type);

    public String getText();

    public void setText(String text);

    public String getExternalId();

    public void setExternalId(String externalId);

    public Map<String, String> getSounds();

    public void setSounds(Map<String, String> sounds);

    public Map<String, String> getImages();

    public void setImages(Map<String, String> images);

    public Date getDateCreated();

    public void setDateCreated(Date dateCreated);

    public Date getLastUpdated();

    public void setLastUpdated(Date lastUpdated);

    public static interface Push extends Atom {
        public File getFile();

        public void setFile(File file);

        public String getOriginalFilename();

        public void setOriginalFilename(String originalFilename);

        public String getExternalUrl();

        public void setExternalUrl(String externalUrl);

        public URL getUrl();

        public void setUrl(URL url);

        public String getFileType();

        public void setFileType(String fileType);
    }
}