#!/bin/sh

java -jar out/api_avaliacao.jar &
java -jar out/api_filme.jar &
java -jar out/api_gateway.jar &
java -jar out/api_recomedacao.jar &
java -jar out/api_usuario.jar
