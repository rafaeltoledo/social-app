#!/bin/sh

# Run tests on test lab
gcloud firebase test android run \
    --type instrumentation \
    --app debug-apk/app-debug.apk \
    --test test-apk/app-debug-androidTest.apk \
    --device model=Nexus6P,version=27,locale=en_US,orientation=portrait \
    --timeout 30m \
    --results-bucket cloud-test-social-app-development \
    --no-record-video \
    --no-performance-metrics \
    --use-orchestrator
