#!/bin/sh

zellij run -- java -jar out/api_avaliacao.jar
zellij run -- java -jar out/api_filme.jar
zellij run -- java -jar out/api_gateway.jar
zellij run -- java -jar out/api_recomedacao.jar
zellij run -- java -jar out/api_usuario.jar
