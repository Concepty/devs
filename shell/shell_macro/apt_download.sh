#!/bin/bash

set -e

target_package="$1"
mkdir -p "$target_package"
cd "$target_package"

rm -f download_log
touch download_log

download_stack=("$target_package")

function str_to_array { # Str, Array, Delimiter='\n'
    OFIS=$IFS
    if [[ -z "$3" ]]; then
        IFS="$3"
    else
        IFS='\n'
    fi
    local -n local_array=$2
    for x in $1
    do
        local_array+=("$x")
    done
    IFS=$OFIS
}

function download_package {
    apt download "$1" 
}

function detect_dependencies {
    apt-cache depends "$1" | grep Depends | awk '{ print $2 }'
}

function recursion {
    local s=$(detect_dependencies "$target_package")
    local 
}

#download_package "$target_package"

s=$(detect_dependencies "$target_package")




# Debug
cd ..
rm -r "$target_package"