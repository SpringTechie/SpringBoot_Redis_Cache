job('SpringBoot Maven Compile Jenkins JOB using DSL Script') {

    description('SpringBoot_Redis_Cache compile job')
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

    publishers {
        downstream('SpringBoot Maven Install Jenkins JOB using DSL Script','SUCCESS')
    }
}
queue('SpringBoot Maven Compile Jenkins JOB using DSL Script')