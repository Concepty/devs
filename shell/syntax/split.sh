#!/bin/bash

IFS=$' '

str="abc def ghi"

read -r -a array <<< "$str"

for e in "${array[@]}"; do
    echo "element: $e"
done

str2="abc
def
ghi"
readarray -t array2 <<< "$str2"

for e in "${array2[@]}"; do
    echo "element: $e"
done
