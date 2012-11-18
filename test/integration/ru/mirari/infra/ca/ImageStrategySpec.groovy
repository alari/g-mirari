package ru.mirari.infra.ca

import grails.plugin.spock.IntegrationSpec
import org.apache.commons.lang.RandomStringUtils
import org.springframework.core.io.ClassPathResource
import spock.lang.Stepwise
import ru.mirari.infra.ca.impl.AtomPOJO

@Stepwise
class ImageStrategySpec extends IntegrationSpec {

    AtomsManager atomsManager

    File imageFile
    String id = RandomStringUtils.random(5)

    def setup() {
        imageFile = new ClassPathResource("image.jpg", this.class).getFile();
    }


    def cleanup() {
    }

    void "can upload an image"() {
        given:
        Atom.Push data = new AtomPOJO.Push(
                file: imageFile,
                id: id,
                originalFilename: "test.jpg"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "image"
        atom.title == "test"
        atom.id == id
        atom.images.size() > 1
    }

    void "can delete an image"() {
        given:
        Atom atom = new AtomPOJO(id: id, type: "image")
        when:
        atomsManager.delete(atom)
        then:
        atom.images == null
    }
}