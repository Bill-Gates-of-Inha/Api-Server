#!/bin/bash

arg=${1}
base64 -i ./src/main/resources/${arg} -o output_${arg}.b64