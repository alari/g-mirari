m = angular.module("ca_base", ['ngResource'])

m.service "caTemplates", ->
  @atom = "/html/atom/atom.html"
  @atomType = (atom)->
    '/html/atom/' + atom.type + '.html'
  @form = "/html/atom/atomNew.html"

m.service "caUrls", ->
  @restResource = '/rest/ca/:id'
  @restAtom = (atom)->
    "/rest/ca/" + atom.id
  @restChainResource = '/rest/creativeChain/:id'
  @mediaelementPluginPath = "/vendor/mediaelement/"

m.factory 'CreativeAtom', ($resource, caUrls)->
  $resource caUrls.restResource