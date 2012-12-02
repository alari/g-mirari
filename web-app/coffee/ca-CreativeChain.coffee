m = angular.module("ca.CreativeChain", ["ngResource", "ca.base"])

m.factory 'CreativeChain', ($resource, caUrls)->
  $resource caUrls.restChainResource

# Single chain controller
m.controller 'ChainCtr', ($scope, CreativeChain)->
  $scope.pushAtom = ->
    $scope.chain.processPushAtom = !$scope.chain.processPushAtom

  $scope.update = ->
    if $scope.chain.processUpdate
      data =
        title: $scope.chain.title
        draft: $scope.chain.draft
      CreativeChain.save {id: $scope.chain.id}, data, ->
        $scope.chain.processUpdate = false
    else
      $scope.chain.processUpdate = true

  $scope.delete = ->
    $scope.chain.$delete {id: $scope.chain.id}

# Chains query controller
m.controller 'ChainQueryCtr', ($scope, CreativeChain)->
  $scope.chains = CreativeChain.query() || []

  $scope.createChain = ->
    chain = new CreativeChain()
    chain.$save ->
      chain.processUpdate = true
      chain.atoms ||= []
      $scope.chains.unshift chain