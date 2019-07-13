FROM openjdk:12-jdk

COPY ./src /project/src
COPY ./target /project/target
COPY ./pom.xml /project/pom.xml
COPY ./mvnw /project/mvnw
COPY ./.mvn /project/.mvn
# TODO: image build時にmavenビルドもやるようにすると、毎回ライブラリがダウンロードされてしまう。良い方法ないか？
# RUN  cd /project && ./mvnw package -Dmaven.test.skip=true

ENTRYPOINT ["java","-Dspring.datasource.password=password", "-Dspring.datasource.url=jdbc:mysql://192.168.100.2:3306/test?autoReconnect=true&autoReconnectForPools=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", "-Djava.security.egd=file:/dev/./urandom", "-jar","/project/target/springsandbox-0.0.1-SNAPSHOT.jar"]