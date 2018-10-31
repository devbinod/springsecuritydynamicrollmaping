package com.deerwalkcompware

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Permission {

    String entity
    String name



    static belongsTo = [role :Role]

    static constraints = {

    }
}
