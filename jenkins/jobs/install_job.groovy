evaluate(readFileFromWorkspace('jenkins/jobs/BuildConstants.groovy'))
job('SpringBoot Maven Install Jenkins JOB using DSL Script') {

    description('SpringBoot_Redis_Cache install job')

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
            goals('clean install')
            mavenInstallation(Constants.MAVEN_TOOL)
            rootPOM('pom.xml')
        }
    }

}