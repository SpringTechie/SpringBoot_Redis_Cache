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
        echo "🛠️ Logging in to Docker Hub..."
        echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin

        echo "🛠️ Building Docker image..."
        docker build -t springboot-redis:latest .

        echo "📤 Pushing to Docker Hub..."
        docker push springboot-redis:latest

        docker logout
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
