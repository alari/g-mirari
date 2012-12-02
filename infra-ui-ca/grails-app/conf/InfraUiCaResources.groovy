modules = {
    'ca-ui' {
        resource plugin: 'infra-ui-ca', url: 'coffee/ca-ui.coffee'
        dependsOn "angular", "angular-ui", 'jquery-file-upload', 'mediaelementplayer', 'autoResize', 'ca-base'
    }
    'ca-CreativeAtom' {
        resource plugin: 'infra-ui-ca', url: 'coffee/ca-CreativeAtom.coffee'
        dependsOn 'angular', 'ca-base'
    }
    'ca-CreativeChain' {
        resource plugin: 'infra-ui-ca', url: 'coffee/ca-CreativeChain.coffee'
        dependsOn 'angular', 'ca-base'
    }
    'ca-app' {
        dependsOn 'ca-ui', 'ca-CreativeAtom', 'ca-CreativeChain', 'ca-base'
    }
    'ca-base' {
        resource plugin: 'infra-ui-ca', url: 'coffee/ca-base.coffee'
        dependsOn 'angular'
    }

/**
 * VENDORS RESOURCES
 */
    'angular-ui' {
        resource plugin: 'infra-ui-ca', url: 'vendor/angular-ui/angular-ui.min.js'
        resource plugin: 'infra-ui-ca', url: 'vendor/angular-ui/angular-ui.min.css'
        resource plugin: 'infra-ui-ca', url: 'vendor/angular-ui/angular-ui-ieshiv.min.js'
        dependsOn "angular", "jquery-ui"
    }

    'jquery-file-upload' {
        resource plugin: 'infra-ui-ca', url: "vendor/blueimp-jQuery-File-Upload/js/jquery.iframe-transport.js"
        resource plugin: 'infra-ui-ca', url: "vendor/blueimp-jQuery-File-Upload/js/vendor/jquery.ui.widget.js"
        resource plugin: 'infra-ui-ca', url: "vendor/blueimp-jQuery-File-Upload/js/jquery.fileupload.js"
        dependsOn "jquery"//, "jquery-ui"
    }

    'mediaelementplayer' {
        resource plugin: 'infra-ui-ca', url: "vendor/mediaelement/mediaelement-and-player.min.js"
        resource plugin: 'infra-ui-ca', url: "vendor/mediaelement/mediaelementplayer.min.css"
        dependsOn "jquery"
    }

    'autoResize' {
        resource plugin: 'infra-ui-ca', url: "vendor/autoResize/autoResize.js"
        dependsOn 'jquery'
    }
}