FROM openjdk:15
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY start.sh start.sh
RUN chmod +x start.sh
CMD ["/start.sh"]