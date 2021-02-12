FROM openjdk:8-alpine

COPY target/uberjar/bootcamp-luminus.jar /bootcamp-luminus/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/bootcamp-luminus/app.jar"]
