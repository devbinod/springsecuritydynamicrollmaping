package com.deerwalkcompware

import grails.plugin.springsecurity.annotation.Secured
@Secured('permitAll')

class SecureController {


    def index() {
        render "Welcome"
    }
}
