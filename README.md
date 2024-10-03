# Sistema Vacunas “OnVacunas” - Prueba Técnica

## Estimación de esfuerzo en horas
Se estimó un total alrededor de 18 horas.

## Descripción
Este proyecto corresponde a una prueba técnica para la gestión de un sistema de vacunación, dividido en dos partes principales: el backend y el frontend.

### Backend

#### Tecnologías Requeridas
- **Java**: Para el desarrollo del backend.
- **MySQL**: Base de datos utilizada.
- **Modelo Base de datos**: Adjunto en el repositorio: `ModeloEntidadRelacion.png`.

#### Dependencias de Maven
El proyecto está desarrollado con Spring Boot y utiliza las siguientes dependencias:
- **Lombok**: Para reducir el boilerplate code.
- **Spring Data JPA**: Para la persistencia de datos.
- **Spring Web**: Para la creación de servicios web.
- **Spring Boot DevTools**: Para mejorar la experiencia de desarrollo.
- **MySQL Driver**: Para la conexión con la base de datos MySQL.
- **Spring Data JDBC**: Para un acceso más sencillo a la base de datos.
- **Swagger**: Para la documentación interactiva de la API.
- **Validation**: Para validaciones en el backend.

#### Arquitectura
- **Arquitectura Hexagonal**: Utilizada para estructurar el backend de manera modular y flexible.

#### Base de Datos
- **MySQL**: Base de datos local utilizada para almacenar la información de los niños, municipio, departamentos y vacunas.

#### Documentación de la API
- **Swagger**: Para acceder a la documentación interactiva, inicie la aplicación y visite Swagger UI.

#### Instrucciones para Usar:
1. Iniciar la aplicación.
2. **Postman**: Inicie la aplicación e importe el archivo JSON adjunto en Postman (`OnVacunas.postman_collection.json`).

#### Alternativa para probar la aplicación:
- **Swagger (Recomendado)**: Inicie la aplicación y visite `http://localhost:8080/api/v1/swagger-ui/index.html#/`.
- Clonar el repositorio desde GitHub.
- Abrir la carpeta `OnVacunasBackend` en su IDE preferido (se recomienda utilizar el **Java Developer Pack** o el **Spring Boot Pack**).
- Configurar el archivo `application.properties` con el username y password de su base de datos MySQL, si es necesario.

### Frontend

#### Tecnologías Requeridas
- **Angular 14**: Para el desarrollo del frontend.
- **TypeScript (TS)**: Lenguaje utilizado en Angular.
- **Node.js y npm**: Para la gestión de dependencias y ejecución de scripts.
- **Tailwind CSS**: Framework de diseño para crear interfaces responsivas y estilizadas.

#### Instrucciones para Usar:
- Acceder a la aplicación en Vercel: `https://prueba-tec-on-credit-sistema-vacunas.vercel.app/children`.

#### Alternativa de despliegue:
1. Clonar el repositorio desde GitHub.
2. Abrir la carpeta `OnVacunasFrontend` en su IDE preferido (se recomienda **Visual Studio Code**).
3. Instalar Angular CLI, los módulos de Node.js y Tailwind CSS:
   ```bash
   npm install -g @angular/cli@14
   npm install
####Si llega a ocurrir un error al iniciar el proyecto puede deberse a una versión incompatible de TypeScript con Angular 14.
Solución:
 ` npm install typescript@4.8 --save-dev`
 
####Ejecutar la aplicación:
`ng serve`
