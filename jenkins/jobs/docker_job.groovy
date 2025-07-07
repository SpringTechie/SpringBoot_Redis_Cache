def constants = evaluate(
        readFileFromWorkspace('jenkins/jobs/constants.groovy')
)
job('Job to create docker-image') {
    description('Jenkins Job to create Docker file')
    scm{
        git {
            remote {
                url(constants.REPO_URL)
            }
            branches(constants.BRANCH)
        }
    }

    steps {
        // Copy the built JAR from the previous job
        copyArtifacts('SpringBoot Maven Install Jenkins JOB using DSL Script') {
            includePatterns('target/*.jar')
            buildSelector {
                latestSuccessful(true)
            }
        }

        // Now build the Docker image with the copied jar
        shell('''\
        echo "🛠️  Building Docker image..."
        docker build -t springboot-redis:latest .

        docker images --no-trunc --quiet springboot-redis:latest > image-id.txt
    '''.stripIndent())
    }
    wrappers {
        credentialsBinding {
            usernamePassword('DOCKER_USERNAME',
                    'DOCKER_PASSWORD',
                    'docker-hub-creds')
        }
        environmentVariables {
            env('IMAGE_NAME', 'springboot-redis')
        }
    }

    publishers{
        archiveArtifacts('image-id.txt')
    }


}
