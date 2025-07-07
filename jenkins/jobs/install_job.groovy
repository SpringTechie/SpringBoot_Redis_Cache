def constants = evaluate(
        readFileFromWorkspace('jenkins/jobs/constants.groovy')
)
job('SpringBoot Maven Install Jenkins JOB using DSL Script') {

    description('SpringBoot_Redis_Cache install job')

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
            goals('clean install')
            mavenInstallation(constants.MAVEN_TOOL)
            rootPOM('pom.xml')
        }
    }
    publihsers {
        archieveArtifacts('target/*.jar')
        downstream('Job to create docker-image','SUCCESS')
    }

}