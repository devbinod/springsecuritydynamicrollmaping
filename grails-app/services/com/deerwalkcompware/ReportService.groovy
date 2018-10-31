package com.deerwalkcompware


import static org.springframework.security.acls.domain.BasePermission.ADMINISTRATION

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.DefaultPermissionFactory
import org.springframework.security.acls.model.AccessControlEntry
import org.springframework.security.acls.model.MutableAcl
import org.springframework.security.acls.model.Permission
import org.springframework.security.acls.model.Sid

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.acl.AclService
import grails.plugin.springsecurity.acl.AclUtilService
import grails.transaction.Transactional

@Transactional
class ReportService {


    DefaultPermissionFactory defaultPermissionFactory
    AclService aclService
    AclUtilService aclUtilService
    SpringSecurityService springSecurityService


    void addPermission(Report report, String username, int permission){
        addPermission report, username, defaultPermissionFactory.buildFromMask(permission)
    }

    @PreAuthorize('hasPermission(#report,admin)')
    void addPermission(Report report, String username,Permission permission){
        aclUtilService.addPermission report, username, permission
    }

    @PreAuthorize('hasRole("ROLE_USER")')
    Report create(String name){
        Report report = new Report(name : name).save(flush : true, failOnError : true)
        addPermission(report,springSecurityService.authentication.name, ADMINISTRATION )
        report
    }


    @PreAuthorize('hasPermission(#id,"com.deerwalkcompware.Report",read) or or hasPermission(#id, "com.deerwalkcompware.Report", admin)')
    Report get(long  id){
        Report.get id
    }


    @PreAuthorize('hasRole("ROLE_USER")')
    @PostFilter('hasPermission(filterObject, read) or hasPermission(filterObject, admin)')
    List<Report> list(Map params) {
        Report.list params
    }


    int count() {
        Report.count()
    }


    @Transactional
    @PreAuthorize('hasPermission(#report, write) or hasPermission(#report, admin)')
    void update(Report report, String name) {
        report.name = name
    }



    @Transactional
    @PreAuthorize('hasPermission(#report, delete) or hasPermission(#report, admin)')
    void delete(Report report) {
        report.delete()

        // Delete the ACL information as well
        aclUtilService.deleteAcl report
    }


    @Transactional
    @PreAuthorize('hasPermission(#report, admin)')
    void deletePermission(Report report, Sid recipient, Permission permission) {
        MutableAcl acl = (MutableAcl)aclUtilService.readAcl(report)

        // Remove all permissions associated with this particular
        // recipient (string equality to KISS)
        acl.entries.eachWithIndex { AccessControlEntry entry, int i ->
            if (entry.sid == recipient && entry.permission == permission) {
                acl.deleteAce i
            }
        }

        aclService.updateAcl acl
    }


}