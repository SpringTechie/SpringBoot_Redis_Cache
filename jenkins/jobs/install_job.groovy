job('SpringBoot Maven Install Jenkins JOB using DSL Script') {

    description('SpringBoot_Redis_Cache install job')

    scm {
        git {
            remote {
                url('https://github.com/SpringTechie/SpringBoot_Redis_Cache.git')
            }
            branches('*/main')
        }
    }

    steps {
        maven {
            goals('clean install')
            mavenInstallation('M3')
            rootPOM('pom.xml')
        }
    }

}