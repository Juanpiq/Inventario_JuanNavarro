La base de datos se llama Inventario
Ajustar las variables de los siguientes campos de application.propperties para que puedan ejecutar el SpringBoot:

spring.datasource.host=
spring.datasource.port=
spring.datasource.username=
spring.datasource.password=

en Postman, deben modificar la variable de la coleccion baseUrl al servidor donde están ejecutando su springboot, pero dejar sin alterar el "/inventario/api" que va al final de dicha baseUrl
Debe autenticarse cada vez que ejecuta el Springboot para que se actualice la variable token en Postman.

Admin en la base de datos con el que puede trabajar:
username = admin
password = admin123

Usuario en la base de datos con el que puede trabajar:
username = juanpi1
password = juanpi123
