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
        --link wsserver \
        --env JAVA_OPTS="-Dfr.univtln.bruno.demo.jaxrs.server.ip=wsserver" \
        docker.io/brunoe/demojaxrs.javaclient:develop
```

Launch one or more web clients and open http://localhost:8080 (or map another port).
Warning, the client is Javascript executed in the browser, so the web socket server must 
be reachable from there and not from the container (so it needs to be mapped to an host port). 
```shell script
docker run \
        --link wsserver \
        --rm \
        -p 8080:8080 \
        brunoe/demowebsocket.webclient:develop
```