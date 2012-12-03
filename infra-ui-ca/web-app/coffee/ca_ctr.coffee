m = angular.module "ca_ctr", ['ca_base', 'ca_render']

m.controller "AtomCtr", ['$scope', 'CreativeAtom', 'caUrls', ($scope, CreativeAtom, caUrls)->
  atom = new CreativeAtom($scope.atom)
  $scope.update = ->
    if(atom.processUpdate)
      delete atom.class
      atom.$save {id: atom.id}, atom, ->
        atom.processUpdate = false
    else
      atom.$get {id: atom.id, update: true}, ->
        atom.processUpdate = true

  $scope.delete = ->
    atom.$delete id: atom.id, ->
      $scope.atom.id = null

  $scope.updateFile = ->
    (o, e)->
      delete atom.class
      e.url = caUrls.restAtom(atom)
      e.formData = atom
      e.submit()

  $scope.updateFileDone = ->
    (e, data)->
      $scope.$apply ->
        atom[k] = v for k,v in data.result if atom[k]
        (atom.sounds[k] = v + "?" + Math.random()) for k,v in data.result.sounds if data.result.sounds
        (atom.images[k] = v + "?" + Math.random()) for k,v in data.result.images if data.result.images
        atom.processUpdate = false
]

m.controller "NewAtomCtr", ['$scope', 'CreativeAtom', ($scope, CreativeAtom)->
  renewAtom = ->
    $scope.newAtom = new CreativeAtom()
    $scope.newAtom.chainId = $scope.chain?.id

  pushAtom = (atom)->
    ($scope.atoms || $scope.chain.atoms).push atom if atom.type

  renewAtom()

  $scope.push = ->
    $scope.newAtom.$save ->
      pushAtom($scope.newAtom)
      renewAtom()

  $scope.pushFile = (o, e)->
    e.formData = $scope.newAtom
    e.submit()

  $scope.fileDone = (e, data)->
    $scope.$apply ->
      pushAtom(new CreativeAtom(data.result))
      renewAtom()
]