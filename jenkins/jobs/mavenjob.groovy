job('SpringBoot Jenkins JOB using DSL Script') {

    description('Demo DSL script to create maven job')
    scm {
       git {
           remote {
               url('https://github.com/SpringTechie/SpringBoot_Redis_Cache.git')
           }
           branches('*/main')
       }
    }
    triggers {
        githubPush()
    }
    steps {
        maven {
            goals('clean compile')
            mavenInstallation('M3')
            rootPOM('pom.xml')

        }
    }
}
queue('SpringBoot Jenkins JOB using DSL Script')