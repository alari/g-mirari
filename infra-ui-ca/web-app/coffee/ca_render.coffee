m = angular.module("ca_render", ['ui', 'ca_base'])

m.directive "caSound", (caUrls)->
  (scope, element, attrs)->
    element.attr "src", scope.$eval(attrs['caSound'])
    $(element).mediaelementplayer
      pluginPath: caUrls.mediaelementPluginPath
      src: scope.$eval("atom.sounds['mpeg']")

m.directive "caRussiaRu", ->
  (scope, element, attrs)->
    externalId = scope.$eval(attrs['russiaRu'])
    $(element).html("<embed name='playerblog#{externalId}'
                           src='http://www.russia.ru/player/main.swf?103'
                                  flashvars='name=#{externalId}&from=blog&blog=true' width='448' height='252'
                                  bgcolor='#000000' allowScriptAccess='always' allowFullScreen='true'></embed>")