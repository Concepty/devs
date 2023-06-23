#!/bin/bash

set -e
function_result=""

target_package="$1"
mkdir -p "$target_package"
cd "$target_package"

rm -f download_log
touch download_log

# pop moves index so that downloaded pkgs remain in stack 
# and can be checked later if already added/downloaded
download_stack=("$target_package")

# TODO
function str_to_array { # Str, Array, Delimiter='\n'
    echo "TODO"
}

function is_in_array {
    if [[ -z "$2" ]]; then
        return 2
    fi

    # TODO refactoring
    local -n local_arr=$2
    for e in "${local_arr[@]}"
    do
        if [[ "$1" == $e ]]; then
            function_result="0"
            return
        fi
    done
    function_result="1"
}

function find_dependencies {
    apt-cache depends "$1" | grep Depends | awk '{ print $2 }'
}

function add_dependencies {
    local s=$(find_dependencies "$target_package")
    local dependencies
    str_to_array "$s" dependencies

    for p in "${dependencies[@]}"
    do
        is_in_array "$p" download_stack
        if [[ $function_result == "1" ]]; then # not in download_stack
            echo "adding $p"
            download_stack+=("$p")
        fi
    done
}

function download_packages {
    for ((i=${#download_stack[@]}-1; i>=0; i--))
    do
        apt download "${download_stack[$i]}"
    done
}

add_dependencies
for p in "${download_stack[@]}"
do
    echo "deps $p"
done

download_packages


# download_package "$target_package"





# Debug
#s=$(detect_dependencies "$target_package")
#array_=()
#str_to_array $s array_
#for x in "${array_[@]}"
#do
#    echo "$x"
#done

#s="abc"
#arr=("abc" "def" "ghi")

#is_in_array $s arr

#echo "ret: $function_result"

cd ..
rm -r "$target_package"