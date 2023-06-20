#!/bin/bash

echo h{a,e,i,o,u}p
# => hap hep hip hop hup
echo "I am "{cool.,great.,awesome}
# => I am cool I am great I am awesome

test_file=test_exp_friends

touch $test_file.txt
cp $test_file.txt{,.bak}
# => braces are expanded first, so the command is `mv friends.txt friends.txt.bak`
ls $test_file*

rm $test_file.txt{,.bak}