# ビルドステージ
FROM gradle:7.2-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN gradle clean build
# ビルド成果物があるかどうかを確認
RUN find /app -type f

# 実行ステージ
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
