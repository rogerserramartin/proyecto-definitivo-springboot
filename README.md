# Pasos a seguir
### Crear el proyecto maven
<p>http://people.apache.org/~ltheussl/maven-stage-site/guides/getting-started/maven-in-five-minutes.html</p>
<p>Hay que ejecutar el comando mvn que nos dice y luego eliminar el src que nos genera</p>
<p>Abrir el proyecto con intellij y configurar el POM.XML padre</p>

### Primer microservicio
<p>Click derecho en la carpeta de proyecto -> new module</p>
<p>Cada modulo va a ser un microservicio, tendra su propio POM.XML con sus propias dependencias</p>
<p>Crear Main, Modelo, Repo, Servicio y Controlador</p>
<p>El repository que siempre SIEMPRE devuelva opcional. EL isPresent nos ahorra dolores de cabeza</p>
<p>Si una query retorna Optional customer, el Servicio puede retornar Customer a secas, no confundirse</p>
<p>Hacer los tests -> Hacer un 2o application.properties o yml que apunte a una bbdd H2 embebida</p>
<p>control + shift + T nos crea un test. Hay que testear las queries que creemos nosotros, las por defecto no</p>
<p>El servicio se testea con Mockito (verify quiere testear un solo metodo de repositorio (solo save, solo findById etc)) </p>
<p>when() en Mockito, si que nos permite testear un servicio con varias llamadas al repositorio: ej: ver si el email esta pillado con un find y luego save</p>
<p>El controlador se testea con postman https://www.guru99.com/postman-tutorial.html</p>
<p>Un codigo 200 es ok. Para refrescar -> https://developer.mozilla.org/en-US/docs/Web/HTTP/Status</p>
<p>Opcional: seguridad -> mirar tutorial Nelson sobre Spring Security (permisos, roles, etc)</p>

### Base de datos y Docker
<p>La base de datos tambien es un microservicio que corre en un contenedor</p>
<p>Crear un docker-compose con 2 contenedores: postgres y pgadmin (base de datos e interfaz grafica)</p>
<p>IMPORTANTE: si da error de puerto, es porque por defecto postgres se inicia cuando se enciende el ordenador</p>
<p>En tal caso -> sudo service postgresql stop</p>
<p>dentro del proyecto ->   docker-compose up -d   ""de detached"". O bien clic en Intellij para arrancar contenedores</p>
<p>docker container ls / docker ps -> nos muestra los contenedores, pero -> docker-compose ps muestra los puertos</p>
<p>Cuando conectemos con http://localhost:5050 para pgadmin, al crear un server, connection hostname/adress debe tener EL MISMO NOMBRE que networks: en el docker-compose.yml</p>

### Segundo microservicio
Click derecho en el proyecto -> new module. Nos crea otra carpeta con el microservicio.
Crear la clase con el main, meter @SpringBootApplication, el .run etc.
Recordar que vez que metamos dependencias en el POM hijo, hacer un reload del maven, para aplicar la dependencia.
Cambiar el puerto, ya que customer ya escucha el 8080.
Crear el application.properties. NOTA: me imagino que en algun momento los puertos se meteran a 0 y se asignaran cuando arranque la aplicacion.


### Segunda base de datos
En una arquitectura de microservicios, queremos una base de datos para cada microservicio.
Pero meto cada base de datos en un contenedor distino, me voy a quedar sin RAM en nada.
Por lo tanto, en el mismo contenedor de Postgres metere otra BBDD, en lugar de 2 contenedores 2 bases de datos
NOTA: leer los comentarios de application.propertis/yml y docker-compose.yml de Fraud microservice

###Eureka
<p>Soluciona la deteccion de puertos. Un cliente Eureka es un microservicio.</p>
<p>Service discovery: the process of automatically detecting devices and services on a network.</p>
<p>Los clientes Eureka o microservicios, se registran en el Servidor Eureka, le preguntan y él les conecta con los demás microservcios.</p>
<p>Copiar la dependencia de Spring Cloud en el POM.XML principal, en dependencymanagement.</p>
<p>La dependencia es esta en el pom hijo: spring-cloud-starter-eureka-server, en el video la tiene mal porque pone netflix-eureka y creo que ya no está hoy en día.</p>
<p>Meter en al application.properties lo necesario para que sea un server.</p>
<p>Ir a los microservicios y declararlos como clientes.</p>
<p>Para hacerlo, como en el modulo de eureka le metimos la dependencia de server, a los microservicios les meteremos la de client.</p>
<p>Ir a los main y meter @EnableEurekaClient</p>
<p>Ir a los yml y poner eureka:client:service-url:defaultZOne: meter la url del eureka server</p>

# Documentacion

SIEMPRE UTILIZAR OPTIONAL EN LOS SERVICIOS, PORQUE LOS NULOS DAN POR SACO

Al arrancar un contenedor de postgres, tener cuidado con los puertos
