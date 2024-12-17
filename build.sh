#!/bin/sh

mkdir -p out

pushd api-avaliacao
mvn clean package
cp target/*.jar ../out/api_avaliacao.jar
popd

pushd api-filme
mvn clean package
cp target/*.jar ../out/api_filme.jar
popd

pushd api-gateway
mvn clean package
cp target/*.jar ../out/api_gateway.jar
popd

pushd api-recomendacao
mvn clean package
cp target/*.jar ../out/api_recomedacao.jar
popd

pushd api-usuario
mvn clean package
cp target/*.jar ../out/api_usuario.jar
popd
