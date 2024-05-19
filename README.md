# AprendeArabe backend

Proyecto backend de la aplicación web AprendeArabe para la realización del Trabajo Fin de Grado de Ingeniería Informática - Tecnologías Informáticas en la Universidad de Sevilla.

El siguiente proyecto ha sido desarrollado utilizando las siguientes versiones:
- Java en su versión 17.0.2
- Spring Boot en su versión 3.0.13

Para la ejecución del proyecto correctamente es necesario realizar las siguientes acciones:
- Clonar proyecto del repositorio y modificar las credenciales del servidor SMTP en el fichero application.properties
- Levantar proyecto con campo spring.jpa.hibernate.ddl-auto=create-drop en el fichero application.properties
- Parar ejecución del proyecto y cambiar campo spring.jpa.hibernate.ddl-auto=update en el fichero application.properties
- Levantar proyecto
- Ejecutar prueba de Postman en la carpeta Scraping Controller para cargar temáticas, lecciones y contenido en la aplicación
- Ejecutar prueba de Postman en la carpeta Test Generator Controller para generar tests en la aplicación

Autor: Antonio Parra Diaz
