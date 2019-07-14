FROM openjdk:11.0.3-jdk-stretch

RUN apt-get update; \
    apt-get install -y build-essential git;

# 参考: https://qiita.com/Targityen/items/67682d6c80cdcbe1186c
WORKDIR /tmp
RUN git clone https://github.com/wolfcw/libfaketime.git
WORKDIR /tmp/libfaketime/src
RUN make install \
 && echo -e '/usr/local/lib/faketime/libfaketime.so.1' > /etc/ld.so.preload

# 環境変数の設定
# Java プログラムを実行する場合は DONT_FAKE_MONOTONIC が必要です。
# システム日付の変更は1秒間隔でチェックします。
ENV DONT_FAKE_MONOTONIC=1
ENV FAKETIME_CACHE_DURATION=1
# システム時刻の変更 (docker run時に変更可能)
# ENV FAKETIME='2020-12-24 20:30:00'

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