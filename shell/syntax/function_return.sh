#!/bin/bash

a="0"

function is_a_zero {
    if [[ "$a" == "0" ]]; then
        echo "checking $a"
        return  0
    else
        return 1
    fi
}

# return 0 is true
if is_a_zero; then
    echo "a is zero"
fi