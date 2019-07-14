FROM openjdk:12-jdk

WORKDIR /project
COPY target target
COPY src src
COPY pom.xml pom.xml
COPY mvnw mvnw
COPY .mvn .mvn

# TODO: image build時にmavenビルドもやるようにすると、毎回ライブラリがダウンロードされてしまう。
# TODO: CI環境とかでは良いかもだけど、普段はビルドされたwarとかをコンテナに渡したほうが良いかな。
#RUN ./mvnw -U package -Dmaven.test.skip=true
#VOLUME /root/.m2

ENTRYPOINT ["java","-Dspring.datasource.password=password", "-Dspring.datasource.url=jdbc:mysql://192.168.100.2:3306/test?autoReconnect=true&autoReconnectForPools=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", "-Djava.security.egd=file:/dev/./urandom", "-jar","/project/target/springsandbox-0.0.1-SNAPSHOT.jar"]