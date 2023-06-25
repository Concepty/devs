#!/bin/bash

set -e
target_package="$1"
mkdir -p "$target_package"
cd "$target_package"
IFS=$'\n'


rm -f "download_log"
touch "download_log"

downloads=()

function is_included_in_d_stack {
    local target="$1"
    for e in "${downloads[@]}"; do
        if [[ "$e" == "$target" ]]; then
            return 0
        fi
    done
    return 1
}

function download_pkg {
    #apt download "$1"
    echo "trying to download $1 and deps"
    downloads+=("$1")
    apt download "$1"

    local deps_arr deps_str
    deps_str=$(apt-cache depends "$1" | grep Depends | awk '{ print $2 }')
    if [[ -z "$deps_str" ]]; then
        return 0
    fi
    readarray -t deps_arr <<< "$deps_str"

    for pkg in "${deps_arr[@]}"; do
        echo "dependency $pkg"
        if ! is_included_in_d_stack "$pkg"; then
            download_pkg "$pkg"
        fi
    done
}

download_pkg "$target_package"

for ((i=${#downloads[@]}-1; i>=0; i--)); do
    echo "${downloads[i]}"
    echo "${downloads[i]}" >> download_log
done
