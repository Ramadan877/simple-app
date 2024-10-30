# training.spring-cloud

Dieses Projekt ist das Begleitproject zu meinem "Spring Cloud" Training.

Es ist bewusst als **multi-module** Maven Projekt gestaltet, damit es im IDE Handling 
und Maven Dependency-Management einfacher ist. 

Diese Art der Code-Struktur ist **nicht empfohlen** für eine komplexe Microservice Architektur, wo 
jeder Service in einem eigenen Git Repository/Projekt leben sollte.

Die Konsequenz aus der gewählten Struktur ist auch, dass dieses Git Repository nicht mit einer
einzigen Heroku App verknüpft werden kann, da wir jedes Modul in eine andere Heroku App deployen
wollen. Somit erfolgt das Heroku Deployment auf Basis von Docker.

## Übersicht nützlicher Befehle

### docker

````shell
$ docker ps --all
$ docker build --tag simple:latest .
$ docker run -it -p 8080:8080 <image>
$ docker exec -it -<container> bash
$ docker rm -f $(docker ps -aq)
````

### heroku deployment

````shell
$ heroku create
$ mvn clean package
$ heroku container:push web --app <app-name-from-create>
$ heroku container:release web --app <app-name-from-create>
$ heroku open --app <app-name-from-create>
$ heroku logs --tail --app <app-name-from-create>
$ heroku logs -n 100 --app <app-name-from-create>
````