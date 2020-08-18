#!/usr/bin/env bash

SCRIPT_PATH=$(cd "$(dirname "$0")" ; pwd -P)
APP=${1}
PROFILE=${2}
PROJECT=

if [[ -z ${PROFILE} ]]; then
  PROFILE="default"
fi


if [[ ${APP} == "version" ]]; then
  PROJECT="version"
elif [[ ${APP} == "restdocs" ]]; then
  PROJECT="restdocs"
elif [[ ${APP} == "client" ]]; then
  PROJECT="client"
else
  echo "start.sh [version|restdocs|client] [profile]"
  exit 1
fi

cd "${SCRIPT_PATH}/.." && ./mvnw clean install --projects=${PROJECT} -Dspring-boot.run.profiles=${PROFILE}