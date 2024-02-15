FROM openjdk:17-jdk-alpine As builder

EXPOSE 8080

WORKDIR /app

RUN apk add maven

COPY ./pom.xml .

COPY ./libs/finsight-client-libs-1.0-SNAPSHOT.jar ./libs/finsight-client-libs-1.0-SNAPSHOT.jar

RUN mvn install:install-file -Dfile=./libs/finsight-client-libs-1.0-SNAPSHOT.jar -DgroupId=com.investment.neosurge -DartifactId=finsight-client-libs -Dversion=1.0-SNAPSHOT -Dpackaging=jar

RUN mvn dependency:go-offline -B

COPY src src

RUN mvn package -DskipTests 

FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY --from=builder /app/target/FinSight-LLMService-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT [ "java", "-jar", "FinSight-LLMService-0.0.1-SNAPSHOT.jar" ]