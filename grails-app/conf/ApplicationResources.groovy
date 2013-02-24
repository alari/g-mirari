modules = {
    /*'ca.base' {
        resource plugin: 'infra-ui-ca', url: 'coffee/ca_base.coffee'
        dependsOn 'angular'
        defaultBundle 'ca'
    }
    'ca.render' {
        resource plugin: 'infra-ui-ca', url: 'coffee/ca_render.coffee'
        dependsOn "angular", "angular-ui", 'jquery-file-upload', 'mediaelementplayer', 'autoResize', 'ca.base'
        defaultBundle 'ca'
    }
    'ca.ctr' {
        resource plugin: 'infra-ui-ca', url: 'coffee/ca_ctr.coffee'
        dependsOn 'angular', 'ca.base', 'ca.render'
        defaultBundle 'ca'
    }
    'ca.app' {
        dependsOn 'ca.base', 'ca.render', 'ca.ctr'
        defaultBundle 'ca'
    }*/

/**
 * VENDORS RESOURCES
 */
    /*'angular-ui' {
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
        defaultBundle 'ca'
    }

    'mediaelementplayer' {
        resource plugin: 'infra-ui-ca', url: "vendor/mediaelement/mediaelement-and-player.min.js"
        resource plugin: 'infra-ui-ca', url: "vendor/mediaelement/mediaelementplayer.min.css"
        dependsOn "jquery"
        defaultBundle 'ca'
    }

    'autoResize' {
        resource plugin: 'infra-ui-ca', url: "vendor/autoResize/autoResize.js"
        dependsOn 'jquery'
        defaultBundle 'ca'
    }*/
}