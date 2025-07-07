evaluate(readFileFromWorkspace('jenkins/jobs/Constants.groovy'))
job('SpringBoot Maven Compile Jenkins JOB using DSL Script') {

    description('SpringBoot_Redis_Cache compile job')
    scm {
       git {
           remote {
               url(Constants.REPO_URL)
           }
           branches(Constants.BRANCH)
       }
    }
    steps {
        maven {
            goals('clean compile')
            mavenInstallation(Constants.MAVEN_TOOL)
            rootPOM('pom.xml')

        }
    }

    publishers {
        downstream('SpringBoot Maven Install Jenkins JOB using DSL Script','SUCCESS')
    }
}
queue('SpringBoot Maven Compile Jenkins JOB using DSL Script')