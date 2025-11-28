#!/usr/bin/env bash
set -euo pipefail

# Build and run the Swing app using a workspace-local Maven repo to avoid ~/.m2.
REPO_DIR=".m2repo"
JAR="target/TeamProject-1.0-SNAPSHOT.jar"

mvn -Dmaven.repo.local="${REPO_DIR}" -q -DskipTests package
mvn -Dmaven.repo.local="${REPO_DIR}" -q dependency:copy-dependencies

java -cp "${JAR}:target/dependency/*" app.Main
