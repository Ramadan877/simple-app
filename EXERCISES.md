# Übungen 

## Mini-Anwendung auf Heroku

### 1) Anwendung erstellen
1. Erstellen Sie mit dem Spring Initializer ein einfaches Projekt inkl. der "web" Starter Dependency
2. Programmieren Sie einen simplen Controller, der auf eine Web-Anfrage ein "Hello!" ausgibt

### 2) Anwendung via Maven Plugin starten

1. Bauen Sie die Anwendung mittels `mvnw clean package`
4. Starten Sie Ihre Anwendung mittels `mvnw spring-boot:run`
5. Testen Sie den Web-Endpunkt lokal, z.B. via http://localhost:8080/hello

### 3) Anwendung via Docker starten

1. Legen Sie ein Dockerfile an, um ein Image zur Ausführung der Anwendung mittels Container bauen zu können
   ````dockerfile
   FROM adoptopenjdk/openjdk11:latest
   RUN mkdir -p /app
   ADD target/simple-1.0-SNAPSHOT.jar /app/simple.jar
   CMD java -Dserver.port=$PORT $JAVA_OPTS -jar /app/simple.jar
   ````
2. Bauen Sie das Image, z.B. mit `docker build --tag simple:latest .`
3. Führen Sie das Image aus und exportieren Sie den Port 8080 nach außen: `docker run -it -p 8080:8080 simple:latest`
4. Testen Sie den Web-Endpunkt lokal, z.B. via http://localhost:8080/hello

### 4) Heroku vorbereiten
1. Melden Sie sich bei Heroku an oder registrieren Sie dort einen Account (kostenfrei)
2. Installieren Sie die Heroku CLI: https://devcenter.heroku.com/articles/heroku-cli#install-the-heroku-cli
   (auf Windows z.B. den 64-bit Installer herunterladen)
3. Führen Sie `heroku login` aus, um die CLI mit Ihrem Account anzumelden
4. Führen Sie ein `heroku container:login` aus, um sich auch gegenüber der Heroku Container Registry anzumelden

### 5) Deployment auf Heroku
1. Erstellen Sie eine neue Heroku App: `heroku create`
2. Pushen Sie den Container in die Container Registry der App: `heroku container:push web --app=<app-name>`
3. Geben Sie den Container frei: `heroku container:release web --app=<app-name>`
4. Öffnen Sie die App: `heroku open --app=<app-name>`
5. Ergänzen Sie die URL mit dem von Ihnen programmierten Pfad für die Begrüßung
6. Lassen Sie sich die Logs von Heroku anzeigen (siehe README.md für Heroku Kommandos)


## A) ConfigServer

### A1) Setup
1. Erstellen Sie ein neues Maven-Modul mit Namen "my-configserver" -- bitte darauf achten, dass
das neue Modul mit der übergeordneten "pom.xml" via <parent> verbunden ist
2. Fügen Sie die `spring-cloud-config-server` Dependency der pom.xml hinzu
3. Erstellen Sie die Applikationsklasse
4. Konfigurieren Sie mittels einer neuen `src/main/resources/application.properties` Datei
im Projekt folgende Werte:
    ````properties
   server.port=8888
   spring.profiles.active=native
   spring.cloud.config.server.native.searchLocations=classpath:/configurations
    ````
5. Erzeugen Sie im Internet ein hübsche ASCII Art Ausgabe für den Text "Config Server" und
   speichern dies in einer neuen Datei `src/main/resources/banner.txt` ab.

### A2) Konfigurationen hinterlegen
1. Legen Sie im Projekt ein leeres Verzeichnis `src/main/resources/configurations`
6. Erstellen Sie dort Dateien folgende Dateien, jeweils mit mind. dem Property `greeting.message`:
   1. `someApp.yml`
   2. `testApp-default.yml`
   3. `testApp-testProfile.yml`

### A3) Konfigurationen abfragen
1. Starten Sie die Anwendung
8. Rufen Sie folgende URLs auf -- was fällt Ihnen auf?
   - http://localhost:8888/someApp/default
   - http://localhost:8888/someApp/testProfile
   - http://localhost:8888/someApp/xyz
   - http://localhost:8888/testApp/default
   - http://localhost:8888/testApp/testProfile
   - http://localhost:8888/testApp/xyz

## B) OrderService als Config-Client

### B1) Setup

1. Erstellen Sie ein neues Maven-Modul mit Namen "my-orderservice" -- bitte darauf achten, dass
das neue Modul mit der übergeordneten "pom.xml" via <parent> verbunden ist
1. Fügen Sie folgende Dependencies der pom.xml hinzu:
    ````xml
   <dependencies>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>   
        <dependency>
           <groupId>org.projectlombok</groupId>
           <artifactId>lombok</artifactId>
       </dependency>
   </dependencies>
    ````
2. Erstellen Sie die Applikationsklasse
3. Definieren Sie den Applikationsnamen und die Quelle der zu importierenden Properties fest (`application.properties`):
    ````properties
    spring.application.name=someApp
    spring.config.import=configserver:http://localhost:8888
    ````

### B2) Erster Test der Konfiguration

1. Erstellen Sie eine Klasse "OrderRestApi", die mit `@RestController` annotiert ist
2. Injecten Sie das Property `greeting.message` mittels `@Value`
3. Implementieren Sie einen `GET /orders/greeting` Endpoint, der diese Message ausgibt
4. Starten Sie die Anwendung und rufen Sie http://localhost:8080/orders/greeting auf
5. Ändern Sie den Anwendungsnamen auf "testApp"
6. Starten Sie die Anwendung und rufen Sie http://localhost:8080/orders/greeting auf
7. Starten Sie die Anwendung mit dem aktiven Profil "testProfile" und rufen 
Sie http://localhost:8080/orders/greeting auf

### B3) Spezifische konfiguration
1. Ändern Sie den Anwendungsnamen auf "orderservice"
2. Legen Sie eine neue `orderservice-default.yml` Datei im "configserver" Projekt an, die
ebenfalls das Property `greeting.message` enthält
4. Starten Sie die Orderservice-Anwendung erneut und rufen Sie http://localhost:8080/orders/greeting auf


## C) Registry-Server

### C1) Setup

1. Erstellen Sie ein neues Maven-Modul mit Namen "my-registryserver" -- bitte darauf achten, dass
das neue Modul mit der übergeordneten "pom.xml" via <parent> verbunden ist
2. Fügen Sie folgende Dependency der pom.xml hinzu:
    ````xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
    ````
3. Erstellen Sie die Applikationsklasse, die folgende Annotation tragen muss: `@EnableEurekaServer`
4. Hinterlegen Sie folgende Konfiguration  in der `application.properties`, womit der Server auf
   einem alternativen Port im Standalone-Modus gestartet wird:
    ````properties
    server.port=8761
    eureka.instance.hostname=localhost
    eureka.client.register-with-eureka=false
    eureka.client.fetch-registry=false
    eureka.client.service-url.default-zone=http://${eureka.instance.hostname}:${server.port}/eureka/
    ````

### C2) Start

1. Starten Sie den Registry-Server
2. Rufen Sie folgende URL auf, um das Admin Dashboard anzuzeigen: http://localhost:8761/

### C3) High-Availability

1. Erstellen Sie zwei Konfigurationsprofile, um zwei Instanzen mit gegenseitigem 
Register/Fetch zu betreiben
2. Starten Sie beide Instanzen mit Ihrem jeweiligen Profil
3. Was verändert sich im Dashboard jedes Servers?

### C4) Zones

1. Erstellen Sie zwei weitere Konfigurationsprofile, um zwei Instanzen in 
unterschiedlichen Zones zu betreiben
2. Starten Sie beide Instanzen mit Ihrem jeweiligen Profil
3. Was verändert sich im Dashboard jedes Servers?
4. Optional: Sie können auch weitere Instanzen starten, z.B. die in einer anderen Region liegen


## D) ProductService als Registry-Client

### D1) Setup

1. Erstellen Sie ein neues Maven-Modul mit Namen "my-productservice"
2. Erstellen Sie die Applikationsklasse
3. Definieren Sie den Applikationsnamen und die Quelle der zu importierenden Properties fest (`application.properties`):
    ````properties
    spring.application.name: productservice
    spring.config.import=configserver:http://localhost:8888
    ````
4. Denken Sie an die Basic-Auth Konfiguration des Config-Servers, wenn dies erfolgt ist (analog OrderService)

### D2) Geschäftslogik

1. Fügen Sie folgende Dependencies der pom.xml hinzu:
    ````xml
   <dependencies>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>   
        <dependency>
           <groupId>org.projectlombok</groupId>
           <artifactId>lombok</artifactId>
       </dependency>
   </dependencies>
    ````
2. Erstellen Sie eine Klasse "ProductService", der eine Methode `Product getProduct(String productId)` enthält.
Diese soll ein Dummy-Produkt mit Namen und Id ausliefern
3. Machen Sie die Ausführung der Methode absichtlich langsam, indem Sie ein `Thread.sleep(1000)` einbauen
4. Zu Beginn der Methode (vor dem sleep) soll eine Logging-Ausgabe erfolgen, sodass wir sehen
können, dass diese Methode und für welche `productId` sie aufgerufen wurde

### D3) REST API

1. Erstellen Sie eine Klasse "ProductRestApi", die mit `@RestController` annotiert ist
2. Injecten Sie hierein den `ProductService`
3. Implementieren Sie einen `GET /products/{productId}` Endpoint, der ein Produkt 
mittels dieses Service ausliefert

### D4) Service Registrierung

1. Fügen Sie der pom.xml folgende Dependency hinzu:
    ````xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    ````
2. Starten Sie die `ProductServiceApplication` Klasse (läuft der Registry-Server noch/schon?)
3. Rufen Sie folgende URL auf: http://localhost:8761/ -- Dort ist nun der PRODUCTSERVICE als 
registrierte Anwendung zu sehen.
4. Ein Click auf den Link "localhost:productservice" zeigt welches Ergebnis? Warum wohl?

### D5) Konfiguration

1. Erstellen Sie im **Config-Server** Projekt eine Konfigurationsdatei namens
`productservice-default.yml` mit folgendem Inhalt:
````yaml
server:
   port: 8081

management:
  endpoints:
    web:
      exposure:
        include: info, health

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
````
2. Nun den Config-Server und dann den Product-Service neu starten. Geht es nun?

### D6) Stress-Testing
1. Laden Sie den Apache HTTPD Server 2.4 oder 2.5 als ZIP von https://www.apachelounge.com/download/ herunter
2. Kopieren Sie aus dem "bin" Verzeichnis das Tool `ab.exe` in das Projektverzeichnis
3. Führen Sie folgenden Befehl aus: `ab.exe -n 10 -c 1 http://localhost:8081/products/123`
4. Und dann `ab.exe -n 100 -c 10 http://localhost:8081/products/123` -- was können Sie aus den Ergebnissen ablesen?

### D7) Optional: Zones
1. Sie können mehrere Clients in verschiedenen Zones und/oder Regionen konfigurieren
2. Was verändert sich beim Start dieser Clients?


## E) OrderService nutzt ProductService

In diesem Abschnitt erweitern wir den bestehenden **Order-Service** um eine API Anbindung an den Product-Service
mittels des Spring Cloud OpenFeign Projekts.

### E1) Feign Client implementieren

1. Fügen Sie der pom.xml folgende Dependencies hinzu:
    ````xml
   <dependencies>   
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-openfeign</artifactId>
       </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-test</artifactId>
       </dependency>
   </dependencies>   
    ````
2. Fügen Sie der Anwendungsklasse diese Annotation hinzu: `@EnableFeignClients`
3. Schreiben Sie ein `ProductClient` Interface gemäß der 
Anleitung https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/ und "productservice"
für den Feign-Client Namen.

### E2) Feign Client nutzen

1. Erstellen Sie eine Klasse `OrderService`
2. Injecten Sie den `ProductClient`
3. Schreiben Sie eine Methode mit der Geschäftslogik zur Abwicklung einer Bestellung, welche folgende 
Signatur hat und die als Implementierung für jede ProductId (das sind die Keys der Map) das Produkt
über den Client lädt. Return Wert ist vorerst `null`.
````java
public Order placeOrder(String mobilePhoneNumber, Map<String,Integer> productQuantities)
````

### E3) (optional) Test mit gemocktem ProductClient

Wir erstellen einen einfachen Test, der die Integration mit einem echten ProductClient mockt.

1. Erstellen Sie im `src/main/test` Order die Klasse `OrderServiceTest_MockedClients`
2. Ergänzen Sie den folgenden Testfall, sodass dieser erfolgreich durchläuft:
````java
@SpringBootTest
class OrderServiceTest_MockedClients {

    @MockBean
    ProductClient productClient;

    @Autowired
    OrderService orderService;

    @Test
    void placeOrder() {
        // given
        Mockito.when(productClient.getProduct(Mockito.anyString()))
                .thenAnswer(invocation -> {
                    Product p = new Product(invocation.getArgument(0), "Mocked Product");
                    return p;
                });

        // when
        // TODO implement call to OrderService here with 2 products

        // then
       // TODO implement Mockito.verify() checks here
    }
}
````

### E4) REST API

Wir erstellen nun einen Endpunkt für die Annahme einer Bestellung.

1. Injecten Sie in die bestehende Klasse `OrderRestApi` nun den `OrderService`
2. Erstellen Sie bitte einen Endpunkt für einen "POST /orders"
Request. Dieser kann folgendes JSON im Body übertragen:
   ````json
   {
       "phoneNumber": "123-456",
       "productQuantities": {
           "DE123X21": 12
       }
   }
   ````
3. Starten Sie die Anwendung neu und rufen Sie den Endpunkt auf mit dem Beispiel JSON von 
oben auf. Wie lange dauert die Anfrage?
4. Fügen Sie zwei weitere Produkte (unterschiedliche productId) dem Aufruf hinzu. 
Wie lange dauert der Aufruf nun? Warum?



## F) Load-Balancing mit mehreren Service-Instanzen

Wir wollen nun den langsamen Product-Service in mehreren Instanzen laufen lassen und
somit einen schnelleren Bestellprozess bewirken.

### F1) Bestellung gegen mehrere Instanzen

1. Starten Sie zwei zusätzliche Instanzen vom Product-Service, die auf den Ports 8082 und 8083
hören (z.B. mit der VM Option `-Dserver.Port=8082`)
2. Starten Sie den Order-Service neu, damit dieser auf jeden Fall alle neuen Instanzen findet
3. Führen Sie erneut den Bestellprozess aus. Welcher Product-Service wird mit welcher `productId`
aufgerufen?
4. Geht es nun schneller?

### F2) Optimierung

1. Schauen Sie sich Ihre Implementierung der `OrderService.placeOrder()` Methode an. Was muss
hier geändert werden, damit der Programmablauf gleichzeitig mehrere Services ansprechen kann?
2. Starten Sie den Order-Service neu
3. Führen Sie erneut den Bestellprozess aus. Geht es nun schneller?



## G) Heroku Deployment

Wir wollen nun unsere bisherige Infrastruktur nach Heroku deployen, dafür brauchen wir

- mindestens einen ConfigServer
- mindestens zwei RegistryServer
- einen OrderService
- mehrere ProductServices 

Jedes dieser Deployments soll unter einem "prod" Profil laufen. Die Konfiguration aller
Instanzen soll mit Git (nicht mehr Dateisystem) ausgeliefert werden.

### G1) Planung

1. Planen Sie das Deployment -- wer deployt welche Anwendungen?

### G2) Vorbereitung

1. Erstellen Sie in den von Ihnen zu deployenden Anwendungen eine `application-prod.properties`,
welche
   - im Falle des Config-Servers ein GitHub repository als Konfigurations-Quelle nutzt
   - im Falle des Registry-Servers den Heroku Hostname und Port nutzt
2. Erstellen Sie das GitHub Repository mit den benötigten Konfigurationsdateien. 
3. Erstellen Sie Dockerfiles, um jede Anwendung als Container nach Heroku pushen zu können. 
Bitte daran denken, dass das "prod" Profil auf aktiv gesetzt wird (`-Dspring.profiles.active=prod`)!

### G3) Durchführung

1. Führen Sie die Deployments durch und testen Sie nach jedem Deployment, ob das Zusammenspiel
funktioniert
2. Lassen Sie sich die Log-Ausgaben eines Containers anzeigen (siehe README.md für Befehl)



## H) Distributed Tracing

### H1) Zipkin

1. Starten Sie Zipkin via Docker: `docker run -d -p 9411:9411 openzipkin/zipkin`

### H2) Anwendung ergänzen

1. Fügen Sie benötigte Dependencies für die Services `product` und `order` hinzu:
    ````xml
   <dependencies>   
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-sleuth</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
            <version>2.2.8.RELEASE</version>        <!-- why is this version not in cloud BOM?? -->
        </dependency>
    </dependencies>
    ````
2. Falls noch nicht vorhanden, fügen Sie in beiden Services Log-Ausgaben in die 
`NnnnnService` und `NnnnnRestApi` Klassen ein

### H3) Traces erzeugen und sehen
1. Starten Sie die Anwendungen neu
4. Führen Sie per REST API eine Bestellung aus
5. Öffnen Sie http://localhost:9411 zur Darstellung der Traces in Zipkin

### H4) Span einfügen
1. Ergänzen Sie die Geschäftslogik-Methoden um eine `@NewSpan` Annotation
2. Starten Sie die Anwendungen neu
3. Führen Sie per REST API eine Bestellung aus
4. Öffnen Sie http://localhost:9411 zur Darstellung der Traces in Zipkin -- was hat sich geändert?
5. Warum werden trotz eines Requests drei Traces erzeugt? Wie könnte man das beheben?
6. Optional: verändern Sie Ihren Code, so dass nur noch ein Trace erzeugt wird



## I) CircuitBreaker

### I1) CustomerService

1. Implementieren Sie in einem neuen Modul "my-customerservice" einen weiteren Business-Service
namens "customerservice" (analog des "productservice", also Service und Api Klasse)
2. Dieser Dienst kann Kunden anhand deren Mobilnummer abfragen, wie z.B.
````
GET http://localhost:8082/customers?phoneNumber=0172-345678
{
   "id": 112233,
   "name": "Enrico Pallazzo",
   "phoneNumber": "0172-345678"
}
````
3. Dieser Dienst soll bewusst schlecht arbeiten, d.h. alle 10 Sekunden schaltet er um zwischen
funktionierend (Status 200) Antworten und nicht funktionierend (Status 500)
4. Im Falle eines Fehlers soll jeweils eine deutlich sichtbare Log-Ausgabe rausgeschrieben werden

### I2) OrderService erweitern

1. Der Order-Service soll nun bei einem Bestellprozess ebenfalls den Kunden abfragen.

### I3) CircuitBreaker einbauen

1. Diese Abfrage soll durch einen CircuitBreaker geschützt werden, der nach 3 Fehlern aufmacht 
und nach 5 Sekunden in halb-offen umschaltet
2. Als Fallbackverhalten wird ein Customer-Objekt zurückgegeben, welches nur die `phoneNumber`
enthält



### J) Bulkhead

#### J1) Neue registerOrder Methode

1. Erweitern Sie den OrderService um eine Business-Method namens `registerOrder`, welches ein
sehr schnelles und asynchrones Platzieren einer Bestellung darstellen soll.
2. Statt einer Implementierung dieser Methode können Sie einfach ein `Thread.sleep(50)` hinterlegen.
3. Der REST Endpunkt gibt deswegen Status ACCEPTED statt OK zurück.

#### J2) Tomcat drosseln und testen

1. Konfigurieren Sie den Tomcat, sodass dieser mit nur maximal 15 Threads arbeitet:
`server.tomcat.threads.max=15`
2. Führen Sie einen Lasttest durch, der 10 parallele User auf dem langsamen Bestellprozess "placeOrder"
simuliert und 10 andere auf dem schnellen "registerOrder"
3. Wie ist der Transaktions-Throughput?

#### J3) Bulkhead einbauen

1. Fügen Sie folgende Dependency der pom.xml hinzu:
````xml
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot2</artifactId>
        </dependency>
````
2. Drosseln Sie die Zugriffe auf `placeOrder()` mittels einer `@Bulkhead` Annotation (Name "placeOrder")
3. Erstellen Sie dazu folgende Konfiguration:
````yaml
resilience4j.bulkhead:
  instances:
     placeOrder:
      maxConcurrentCalls: 10
      maxWaitDuration: 10ms
````

#### J4) Tomcat erneut testen
2. Führen Sie den Lasttest erneut durch
3. Wie ist nun der Transaktions-Throughput?



## K) Prometheus & Grafana

### K1) Docker Setup

1. Legen Sie ein Unterverzeichnis "my-prometheus-grafana" an
2. Erstellen Sie dort ein `docker-compose.yml` File, welches zwei Services definiert:
   ````yaml
   version: "3"
   services:
     prometheus:
       image: prom/prometheus:latest
       container_name: prometheus
       volumes:
         - ./prometheus/prometheus.yml:/etc/prometheus/prometheus.yml
       ports:
         - "9090:9090"
       extra_hosts:
         - "host.docker.internal:host-gateway"
     grafana:
       image: grafana/grafana-oss:latest
       container_name: grafana
       volumes:
         - ./grafana/provisioning/datasources:/etc/grafana/provisioning/datasources/
       ports:
         - "3000:3000"
   ````
3. Schauen Sie sich diese Datei näher an -- was wird hier alles konfiguriert?
4. Erstellen (oder kopieren Sie aus dem Beispielprojekt) die notwendige prometheus.yml Datei. Hilfreiche
Links sind:
   - https://prometheus.io/docs/prometheus/latest/configuration/configuration/#eureka_sd_config
   - https://github.com/prometheus/prometheus/blob/release-2.24/documentation/examples/prometheus-eureka.yml

### K2) Docker Run

1. Starten Sie die Docker Container mit `docker-compose up` (aus dem richtigen Unterverzeichnis)
2. Öffnen Sie http://localhost:9090
3. Öffnen Sie http://localhost:3000 (User und Kennwort sind "Admin", Änderung mit "Skip" überspringen)

### K3) Erster Check

1. Kann Prometheus schon via Eureka Dienste finden und dort Metriken abgreifen? 
Siehe http://localhost:9090/targets

### K4) Micrometer in Business-Services aktivieren

1. Fügen Sie die benötigten Dependencies der Parent-Pom hinzu, sodass jedes Modul diese automatisch erhält
2. Starten Sie einen Business-Service neu
3. Öffnen Sie (richtige Port nehmen!) zB http://localhost:8080/actuator/prometheus

### K5) Metrik in Prometheus abfragen

1. Auch Prometheus kann Daten visualisieren, fragen Sie dort eine der Metriken ab, die der Actuator-Endpoint
Ihnen genannt hat

### K6) Grafana Dashboard anlegen

1. Erstellen Sie in Grafana ein neues Dashboard
2. Fügen Sie ein Panel hinzu, in dem Sie die vorhin genutzte Metrik nun mittels Grafana visualisieren lassen

### Kn) Optional: Datasource provisionieren

1. Legen Sie einen Ordner `./grafana/provisioning/datasources` und darin eine Datei zur
Provisionierung einer Datasource in Grafana.

### Kn) Optional: Verbindungsprobleme lösen

1. Falls Sie aus dem Prometheus Docker Container Verbindungsprobleme auf andere URLs haben,
so empfiehlt es sich per compose einen weiteren Container zu starten, in dem die "bash" Shell
verfügbar ist. Image: "bash:latest" und Container-Name "bash-for-curl"
2. Dann ist möglich:
   - `docker exec -it bash-for-curl bash`
   - `apk add curl` (um curl zu installieren)
   - `curl http://host.docker.internal:8761/eureka/apps` (um Abfragen zu testen, URL ggf ändern)