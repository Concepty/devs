#! /bin/bash

a='Str1'
( 
    a='Str2'
    mkdir $a
    ret=$?
    ls 
    #Str2
    if [[ $ret -eq 0 ]]; then
        echo "removing temp directory $a"
        rmdir $a
    fi
)
echo -e "a is $a"
# a is Str1
