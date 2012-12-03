class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        "/rest/ca/$id?"(resource: "exampleAtomRest")
        "/rest/ca"(controller: "exampleAtomRest", parseRequest: true) {
            action = [POST: "create", GET: "query"]
        }

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
