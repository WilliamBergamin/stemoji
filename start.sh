#!/bin/bash

env=${1:-.env}
[ ! -f "${env}" ] && { echo "Environment file ${env} doesn't exist"; return 1; }
eval "$(sed -e '/^\s*$/d' -e '/^\s*#/d' -e 's/=/="/' -e 's/$/"/' -e 's/^/export /' "${env}")"

mvn clean compile exec:java -Dexec.mainClass="Stemoji"