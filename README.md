## Run local

```shell
export $(cat .env | xargs)  
mvn install
mvn package
java --jar target/projectzero-0.0.1-SNAPSHOT.jar
```

## Deploy to Heroku

```shell
heroku login
heroku container:login
heroku container:push web --app=<app_name>
heroku container:release web --app=<app_name>
```