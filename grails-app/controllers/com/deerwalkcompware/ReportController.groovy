package com.deerwalkcompware

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class ReportController {

    ReportService reportService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond reportService.list(params), model:[reportCount: reportService.count()]
    }

    def show(Long id) {
        respond reportService.get(id)
    }

    def create() {
        respond new Report(params)
    }

    def save(Report report) {
        if (report == null) {
            notFound()
            return
        }

        try {
            reportService.save(report)
        } catch (ValidationException e) {
            respond report.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'report.label', default: 'Report'), report.id])
                redirect report
            }
            '*' { respond report, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond reportService.get(id)
    }

    def update(Report report) {
        if (report == null) {
            notFound()
            return
        }

        try {
            reportService.save(report)
        } catch (ValidationException e) {
            respond report.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'report.label', default: 'Report'), report.id])
                redirect report
            }
            '*'{ respond report, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        reportService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'report.label', default: 'Report'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'report.label', default: 'Report'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
