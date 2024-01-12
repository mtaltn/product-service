# Resmi OpenJDK 17 Debian tabanlı Docker imajını kullan
FROM openjdk:17

ARG JAR_FILE=target/*.jar
# Çalışma dizinini belirle
WORKDIR /opt/app

# Uygulama dosyalarını kopyala
COPY ${JAR_FILE} app.jar

# Uygulamayı çalıştır
CMD ["java", "-jar", "app.jar"]
