echo off

pushd shopizer
mvnw -DskipTests -Darguments=-DskipTests clean install
popd