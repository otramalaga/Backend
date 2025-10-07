# Otra Málaga - Backend API

Backend API para la plataforma **Otra Málaga**, una aplicación web que permite a los usuarios compartir y descubrir lugares culturales únicos en Málaga. La plataforma fomenta una comunidad vibrante de exploración cultural y conexión.

---

## 🎯 Funcionalidades del Backend

Este backend proporciona las siguientes funcionalidades para soportar la plataforma Otra Málaga:

### 👤 Gestión de Usuarios
- **Registro de usuarios** 📝 con validación de datos
- **Autenticación** ✅ mediante JWT (JSON Web Tokens)
- **Gestión de perfiles** de usuario

### 📍 Gestión de Bookmarks/Lugares
- **Creación de nuevos lugares** 
- **Consulta de detalles** de lugares específicos 🔍
- **Actualización de información** de lugares existentes ✏️
- **Eliminación de lugares** 🗑️
- **Búsqueda de lugares** por título
- **Gestión de imágenes** asociadas a los lugares

### 🏷️ Sistema de Categorización
- **Gestión de categorías** para clasificar lugares
- **Sistema de tags** para etiquetado flexible
- **Filtros por ubicación y categoría**

### 🔐 Seguridad
- **Autenticación JWT** para acceso seguro
- **Autorización** basada en roles de usuario
- **Validación de datos** de entrada
- **Manejo de excepciones** global

### 🌐 API REST
- **Endpoints RESTful** para comunicación con el frontend
- **Soporte para métodos HTTP** estándar (GET ➡️, POST 📤, PUT 💾, DELETE 🗑️)
- **Documentación automática** con Swagger/OpenAPI
- **CORS configurado** para múltiples dominios

---

## 🛠️ Tecnologías y Herramientas

- **Lenguaje de Programación**: Java 17
- **Framework Backend**: Spring Boot 3.2.5
- **Persistencia de Datos**: Spring Data JPA
- **Seguridad**: Spring Security con JWT
- **Base de Datos**: PostgreSQL (producción) / H2 (testing)
- **Autenticación**: JWT (JSON Web Tokens)
- **Validación**: Bean Validation
- **Documentación API**: SpringDoc OpenAPI 3
- **Herramientas de Desarrollo**: Lombok, Spring Boot DevTools
- **Contenedorización**: Docker
- **Gestión de Dependencias**: Maven

---

## 📋 Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- PostgreSQL 12+ (para producción)
- Docker (opcional, para contenedores)

---

## ⚙️ Configuración y Ejecución

### 1. Clonar el Repositorio
```bash
git clone https://github.com/otramalaga/Backend.git
cd Backend
```

### 2. Configurar Variables de Entorno
Crear un archivo `.env` basado en el ejemplo:
```bash
cp env.example .env
```

Editar el archivo `.env` con tus valores:
```env
# Base de datos
DATABASE_URL=jdbc:postgresql://localhost:5432/otramalaga_db
DATABASE_USERNAME=tu_usuario
DATABASE_PASSWORD=tu_password

# Directorio de archivos
FILE_UPLOAD_DIRECTORY=./uploads

# Clave secreta para JWT (genera una nueva para producción)
SECRET_JWT_KEY=tu-clave-secreta-muy-larga-y-segura
```

### 3. Configurar Base de Datos
Crear la base de datos PostgreSQL:
```sql
CREATE DATABASE otramalaga_db;
```

### 4. Ejecutar la Aplicación

#### Opción A: Con Maven
```bash
# Instalar dependencias
mvn clean install -DskipTests

# Ejecutar la aplicación
mvn spring-boot:run
```

#### Opción B: Con Docker
```bash
# Construir la imagen
docker build -t otramalaga-backend .

# Ejecutar el contenedor
docker run -p 8080:8080 --env-file .env otramalaga-backend
```

### 5. Verificar la Aplicación
- **API Base**: `http://localhost:8080`
- **Documentación Swagger**: `http://localhost:8080/swagger-ui/`
- **Health Check**: `http://localhost:8080/api/health`

---

## 📚 Estructura del Proyecto

```
src/main/java/com/finalproject/Backend/
├── config/                 # Configuraciones (CORS, Security, JWT)
├── controller/             # Controladores REST
│   ├── AuthController.java
│   ├── BookmarkController.java
│   ├── CategoryController.java
│   └── ...
├── dto/                    # Data Transfer Objects
│   ├── request/           # DTOs de petición
│   └── response/          # DTOs de respuesta
├── exception/             # Manejo de excepciones
├── mapper/                # Mappers entre entidades y DTOs
├── model/                 # Entidades JPA
│   ├── User.java
│   ├── Bookmark.java
│   ├── Category.java
│   └── ...
├── repository/            # Repositorios JPA
├── security/             # Configuración de seguridad
├── service/              # Lógica de negocio
└── RizomaApplication.java # Clase principal
```

---

## 🔗 Endpoints Principales

### Autenticación
- `POST /api/auth/register` - Registro de usuario
- `POST /api/auth/login` - Inicio de sesión

### Bookmarks/Lugares
- `GET /api/bookmarks` - Obtener todos los lugares
- `GET /api/bookmarks/{id}` - Obtener lugar por ID
- `GET /api/bookmarks/search?title={title}` - Buscar lugares
- `POST /api/bookmarks` - Crear nuevo lugar
- `PUT /api/bookmarks/{id}` - Actualizar lugar
- `DELETE /api/bookmarks/{id}` - Eliminar lugar

### Categorías
- `GET /api/categories` - Obtener todas las categorías

### Tags
- `GET /api/tags` - Obtener todos los tags

### Health Check
- `GET /api/health` - Estado de la aplicación

---

## 🧪 Testing

Ejecutar tests:
```bash
# Todos los tests
mvn test

# Tests específicos
mvn test -Dtest=NombreTest

# Saltar tests (solo compilación)
mvn clean install -DskipTests
```

---

## 🐳 Docker

### Construir imagen
```bash
docker build -t otramalaga-backend .
```

### Ejecutar contenedor
```bash
docker run -p 8080:8080 \
  -e DATABASE_URL=jdbc:postgresql://host.docker.internal:5432/otramalaga_db \
  -e DATABASE_USERNAME=tu_usuario \
  -e DATABASE_PASSWORD=tu_password \
  -e SECRET_JWT_KEY=tu-clave-secreta \
  otramalaga-backend
```

---

## 📖 Documentación de la API

La documentación completa de la API está disponible en:
- **Swagger UI**: `http://localhost:8080/swagger-ui/`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

---

## 🔧 Configuración de Desarrollo

### Variables de Entorno Requeridas
```env
DATABASE_URL=jdbc:postgresql://localhost:5432/otramalaga_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=password
FILE_UPLOAD_DIRECTORY=./uploads
SECRET_JWT_KEY=clave-secreta-para-desarrollo
```

### Configuración de CORS
La aplicación está configurada para aceptar peticiones desde:
- `http://localhost:5173` (desarrollo local)
- `https://otramalaga.com` (producción)

---

## 🚀 Despliegue

### Variables de Entorno para Producción
```env
DATABASE_URL=jdbc:postgresql://tu-servidor:5432/otramalaga_prod
DATABASE_USERNAME=usuario_prod
DATABASE_PASSWORD=password_seguro
FILE_UPLOAD_DIRECTORY=/app/uploads
SECRET_JWT_KEY=clave-super-secreta-para-produccion
```

### Consideraciones de Seguridad
- Cambiar `SECRET_JWT_KEY` por una clave segura
- Configurar HTTPS en producción
- Revisar configuración de CORS
- Implementar rate limiting
- Configurar logs de seguridad

---


## 📄 Licencia

Este proyecto es parte de la plataforma **Otra Málaga** y está destinado para uso educativo y de desarrollo comunitario.

---

## 🆘 Soporte

Para soporte técnico o preguntas sobre el proyecto, contacta al equipo de desarrollo o crea un issue en el repositorio.

---

*Desarrollado con ❤️ para la comunidad de Málaga*