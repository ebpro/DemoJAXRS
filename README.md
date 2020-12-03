# Demo Web Socket

## Objectives
A simple Java REST Server (JAX-RS with Jersey on top of Grizzly web server).

## Building
```shell script
mvn clean package
```

## Running
### Java
By default the server and the client bind on localhost:8080

Run the server
```shell script
java -jar Server/target/Server-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Run the client
```shell script
java -jar JavaClient/target/JavaClient-1.0-jar-with-dependencies.jar
```

### Docker
Launch the server with a name and mapped port.
```shell script
docker run \
        --rm -it \
        -p 8080:8080 \
        --name rsserver \
        docker.io/brunoe/demojaxrs.server:develop
```

Launch one linked to the server.
```shell script
docker run \
        --rm -it \
        --link rsserver \
        --env JAVA_OPTS="-Dfr.univtln.bruno.demo.jaxrs.server.ip=rsserver -Dfr.univtln.bruno.demo.jaxrs.server.port=8081" \
        docker.io/brunoe/demojaxrs.javaclient:develop
```
