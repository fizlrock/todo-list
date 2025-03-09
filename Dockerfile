# Второй этап: используем минималистичный образ с JRE 21
FROM eclipse-temurin:21-jre-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR-файл из первого этапа


COPY  /temp/*.jar app/

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app/app-1.0.0.jar"]
