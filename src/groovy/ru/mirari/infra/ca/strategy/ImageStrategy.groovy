package ru.mirari.infra.ca.strategy

import org.springframework.stereotype.Component
import ru.mirari.infra.ca.AtomStrategy
import ru.mirari.infra.ca.Atom
import ru.mirari.infra.image.ImageHolder
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.image.ImageStorageService
import ru.mirari.infra.image.ImageFormat
import ru.mirari.infra.image.ImageType
import ru.mirari.infra.image.ImageCropPolicy

/**
 * @author alari
 * @since 11/19/12 12:32 AM
 */
@Component
class ImageStrategy extends AtomStrategy {
    @Autowired
    private ImageStorageService imageStorageService

    private String imageBucket = "mirariimages"
    private ImageFormat imageDefaultFormat = new ImageFormat("980*750", "standard", ImageCropPolicy.NONE, ImageType.JPG)
    private List<ImageFormat> imageAllFormats = [
            new ImageFormat("1920*1920", "max", ImageCropPolicy.NONE, ImageType.JPG),
            new ImageFormat("980*750", "standard", ImageCropPolicy.NONE, ImageType.JPG),

            new ImageFormat("180*180", "medium", ImageCropPolicy.CENTER, ImageType.JPG),
            new ImageFormat("120*120", "small", ImageCropPolicy.CENTER, ImageType.JPG),
            new ImageFormat("90*90", "thumb", ImageCropPolicy.CENTER, ImageType.JPG)
    ]

    private class AtomImageHolder implements ImageHolder {
        public final String imagesPath

        AtomImageHolder(String id) {
            imagesPath = "i/".concat(id)
        }

        @Override
        String getImagesPath() {
            imagesPath
        }

        @Override
        String getImagesBucket() {
            imageBucket
        }

        @Override
        List<ImageFormat> getImageFormats() {
            imageAllFormats
        }

        @Override
        ImageFormat getDefaultImageFormat() {
            imageDefaultFormat
        }
    }

    private ImageHolder getImageHolder(Atom atom) {
        new AtomImageHolder(atom.id)
    }

    @Override
    boolean isContentSupported(Atom.Push data) {
        (data.file && data.fileType.startsWith("image/"))
    }

    @Override
    void setContent(Atom atom, Atom.Push data) {
        if(data.file) {
            ImageHolder holder = getImageHolder(atom)
            imageStorageService.format(holder, data.file)
            atom.title = data.originalFilename.substring(0, data.originalFilename.lastIndexOf("."))
            atom.images = [:]
            for (ImageFormat format : holder.imageFormats) {
                atom.images.put(format.name, imageStorageService.getUrl(holder, format))
            }
        }
    }

    @Override
    void delete(Atom atom) {
        imageStorageService.delete(getImageHolder(atom))
        atom.images = null
    }
}
