package infra.ui.ca

import grails.converters.JSON

class ExampleAtomRestController {
    def query() {
        render params as JSON
    }

    def create() {
        render request.JSON as JSON
    }

    def show() {
        render params as JSON
    }

    def save() {
        render params as JSON
    }

    def delete() {
        render params as JSON
    }
}
