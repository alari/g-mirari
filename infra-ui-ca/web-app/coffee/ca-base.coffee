angular.module("ca.base", [])
.service "caTemplates", ->
  @atom = "/html/atom/atom.html"
  @atomType = (atom)->
    '/html/atom/' + atom.type + '.html'
  @form = "/html/atom/atomNew.html"
  .service "caUrls", ->
    @restResource = '/rest/creativeAtom/:id'
    @restAtom = (atom)->
      "/rest/creativeAtom/" + atom.id
    @restChainResource = '/rest/creativeChain/:id'
    @mediaelementPluginPath = "/vendor/mediaelement/"