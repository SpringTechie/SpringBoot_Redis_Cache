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
        shell('''\
            #!/bin/bash -e
            echo "🛠️  Building Docker image..."
            docker build -t springboot-redis:latest .

            # Save the image digest for traceability
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
