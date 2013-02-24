package mirari

import infra.ca.Atom

class Block implements Atom {

    String id
    String title
    String type
    Map<String,String> texts
    String text
    String externalId
    String externalUrl
    Map<String,String> sounds
    Map<String,String> images

    Date dateCreated
    Date lastUpdated

    static mapWith = "mongo"

    static constraints = {
        externalId nullable: true
        externalUrl nullable: true, url: true
        text nullable: true
        title nullable: true, blank: true
        type nullable: true, blank: false
    }
}
