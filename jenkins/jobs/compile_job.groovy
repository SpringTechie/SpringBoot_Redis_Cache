def constants = evaluate(
        readFileFromWorkspace('jenkins/jobs/constants.groovy')
)

job('SpringBoot Maven Compile Jenkins JOB using DSL Script') {

    description('SpringBoot_Redis_Cache compile job')
    scm {
       git {
           remote {
               url(constants.REPO_URL)
           }
           branches(constants.BRANCH)
       }
    }
    steps {
        maven {
            goals('clean compile')
            mavenInstallation(constants.MAVEN_TOOL)
            rootPOM('pom.xml')

        }
    }

    publishers {
        downstream('SpringBoot Maven Install Jenkins JOB using DSL Script','SUCCESS')
    }
}
queue('SpringBoot Maven Compile Jenkins JOB using DSL Script')