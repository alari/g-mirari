package ru.mirari.infra.ca

import grails.plugin.spock.IntegrationSpec
import org.springframework.core.io.ClassPathResource
import ru.mirari.infra.ca.impl.AtomPOJO
import org.apache.commons.lang.RandomStringUtils
import spock.lang.Stepwise

@Stepwise
class SoundStrategySpec extends IntegrationSpec {

    AtomsManager atomsManager

    File soundFile
    String id = RandomStringUtils.random(5)

    def setup() {
        soundFile = new ClassPathResource("sound.mp3", this.class).getFile();
    }

    def cleanup() {
    }

    void "can upload a sound"() {
        given:
        Atom.Push data = new AtomPOJO.Push(
                file: soundFile,
                id: id,
                originalFilename: "test.mp3"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "sound"
        atom.title == "test"
        atom.id == id
        atom.sounds == [mpeg: "sound.mp3"]
    }

    void "can delete a sound"() {
        given:
        Atom atom = new AtomPOJO(id: id, sounds: [mpeg: "sound.mp3"], type: "sound")
        when:
        atomsManager.delete(atom)
        then:
        atom.sounds == null
    }
}