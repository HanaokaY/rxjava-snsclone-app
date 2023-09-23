# ビルドと実行を同一ステージで行う
FROM gradle:7.2-jdk17-alpine
WORKDIR /app
ADD . /app
RUN gradle clean build
RUN mv /app/app/build/libs/app-0.1.0.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
