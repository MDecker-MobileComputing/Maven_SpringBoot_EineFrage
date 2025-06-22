
@del /Q logdatei.log

mvnw clean spring-boot:run -Dspring-boot.run.profiles=postgres -Dspring-boot.run.arguments="--server.port=8080"

