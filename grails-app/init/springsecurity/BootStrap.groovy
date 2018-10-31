package springsecurity

import com.deerwalkcompware.Role
import com.deerwalkcompware.User
import com.deerwalkcompware.UserRole

class BootStrap {
    def sampleDataService

    def init = { servletContext ->


        sampleDataService.createSampleData()

    }
    def destroy = {
    }
}
