#!/bin/zsh

gradle bootJar
mkdir temp
find . -name "*.jar" -exec cp {} ./temp/ \;


docker build -t fizlrock/todo-backend:latest .

rm -rf temp


docker push fizlrock/todo-backend:latest
