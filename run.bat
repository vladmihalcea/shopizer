echo off

pushd sm-shop
mvnw spring-boot:run -D"spring-boot.run.profiles"=local
popd