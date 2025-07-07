def constants = evaluate(
        readFileFromWorkspace('jenkins/jobs/constants.groovy')
)
job('SpringBoot-Run-Container') {

    description('Runs the springboot‑redis container and pings a sample API endpoint')

    /* ── Trigger: only after the Docker image build succeeds ── */
    triggers {
        upstream('Job to create docker-image', 'SUCCESS')
    }

    /* ── Build steps ── */
    steps {
        shell('''\
    #!/bin/bash -e
    IMAGE_NAME="springboot-redis:latest"
    CONTAINER_NAME="springboot-redis-test"

    docker run -d --rm --name "$CONTAINER_NAME" -p 9090:9090 "$IMAGE_NAME"

    # Wait until the app responds on port 9090
    for i in {1..30}; do
      if curl -s http://localhost:9090/ > /dev/null ; then
        echo "✅  App is up on port 9090"
        break
      fi
      sleep 2
    done

    curl -v http://localhost:9090/
    docker stop "$CONTAINER_NAME"
'''.stripIndent())
    }

    /* ── Post‑build: archive curl output for reference (optional) ── */
    publishers {
        // If you want to keep the curl output, uncomment:
        // archiveArtifacts('curl-output.txt')
    }


}