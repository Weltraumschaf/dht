#!/bin/bash

x=1

while [ $x -le 10000 ]
do
    echo -e "hello_1\nhello_2\nhello_3\nhello_4\nhello_5" | \
        xargs -n 1 -P 10 -I '{}' sh -c 'echo "{}"  | nc localhost 8888'
    x=$(( $x + 1 ))
    sleep 1
done
