package com.deerwalkcompware

import grails.compiler.GrailsCompileStatic
import grails.gorm.services.Service
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.acl.AclService
import grails.plugin.springsecurity.acl.AclUtilService
import org.springframework.security.acls.domain.DefaultPermissionFactory

@Service(Permission)
@GrailsCompileStatic
interface PermissionService {

    DefaultPermissionFactory aclPermissionFactory
    AclService aclService
    AclUtilService aclUtilService
    SpringSecurityService securityService








    Permission get(Serializable id)

    List<Permission> list(Map args)

    Long count()

    void delete(Serializable id)

    Permission save(Permission permission)

}