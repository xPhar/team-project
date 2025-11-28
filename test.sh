#!/usr/bin/env bash
set -euo pipefail

# Run the project's test suite using a workspace-local Maven repo to avoid ~/.m2 pollution.
REPO_DIR=".m2repo"

mvn -Dmaven.repo.local="${REPO_DIR}" test
