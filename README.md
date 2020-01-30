# Iniciando despliegue del Backend

  Autor: Jose G Pacheco (jgp3500@gmail.com)

## Requisitos
* Java 8
* Spring Boot 2.2.4
* Postgres v11.6
* Para poder compilar el proyecto, se debe tener instalado y configurado en el IDE (Eclipse, Netbeans, IntelliJ IDEA) Lombok.

## Instalacion
* Descargar el proyecto desde github [https://github.com/malcomx/InGerenciaBackEnd]
* Instalar Manejador de base de datos Postgres v10 o en adelante
* Ejecutar el script que se encuentra **~/test/src/main/resources/static/ingerencia_ddl.sql**. El script se basa en ejecucion SQL y debe ser modificado la clave del usuario **postgres** de la base de datos **o** cambiar las credenciales por un usuario que tenga el privilegios para realizar dichas operaciones.
* Dicho script se encarga de crear la tabla hacker_news

![Tabla hacker_news](src/main/resources/static/img/table_img.png)

## Prueba de servicios

Se desarrollaron 2 servicios GET
1. [CONTEXT]/api/v1/news: Lista las noticias ordenadas por la fecha de creacion
	
	Ejemplo: http://localhost:8080/test/api/v1/news
2. [CONTEXT]/api/v1/news/{VARIABLE}: Elimina la noticia que tenga como identificador el valor de {VARIABLE}
	
	Ejemplo: http://localhost:8080/test/api/v1/news/22134612
	
