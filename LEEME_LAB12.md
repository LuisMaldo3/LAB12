# Laboratorio 12 — Pruebas de integración

Proyecto completado sobre el ZIP proporcionado. Repositorio de destino: https://github.com/LuisMaldo3/LAB12

## Qué se agregó
- Vet, Owner, Specialty y Visit: DTO, repositorio JPA, servicio transaccional y controlador REST.
- Nueve pruebas por entidad: listar, buscar existente/inexistente, crear, actualizar, actualizar inexistente, eliminar, eliminar inexistente y rechazar datos incompletos.
- MockMvc con contexto completo (@SpringBootTest), servicios y repositorios reales, perfil H2 y rollback por prueba. No se sustituye la base de datos con mocks.
- DTO de Visit con petId y resolución de la mascota en el servicio. DTOs evitan serializar relaciones bidireccionales.
- GitHub Actions ejecuta clean verify y conserva informes de Surefire y JaCoCo.

## Estado de verificación
Se intentó ejecutar Maven con Java 21. No se descargó el POM de Spring Boot 3.5.6 porque repo.maven.apache.org no pudo resolverse en el entorno de preparación. No se afirma que las pruebas hayan pasado. Ejecuta los comandos siguientes para obtener el resultado real. Los informes o capturas de éxito deben proceder de esa ejecución.

## Abrir y probar en Windows
Extrae el ZIP. Abre la carpeta petclinic_integration_test en IntelliJ como proyecto Maven y selecciona JDK 21. Abre la terminal en esa carpeta (donde está pom.xml).

```powershell
java -version
.\mvnw.cmd test "-Dtest=VetControllerTest,OwnerControllerTest,SpecialtyControllerTest,VisitControllerTest"
.\mvnw.cmd clean verify
```

El primer comando de pruebas selecciona las 36 nuevas. El segundo ejecuta además las pruebas originales de Pet y genera cobertura. No necesita XAMPP: application.yml selecciona H2. La base se reinicia al arrancar la aplicación. Necesitas internet para la primera descarga Maven.

## Capturas de la parte inicial
1. Estructura: panel del proyecto desplegado con dtos, entities, repositories, services, webs y src/test/java.
2. PetControllerTest.java: anotaciones, MockMvc y un método de prueba legible.
3. Configuración: application.yml (perfil h2) y application-h2.yml (URL jdbc:h2 y SQL init).
4. Ejecución: terminal con .\mvnw.cmd test "-Dtest=PetControllerTest" y el resultado completo.

## Evidencias por ejercicio
Para cada entidad Vet, Owner, Specialty y Visit:
- Código: abre src/test/java/com/tecsup/petclinic/webs/ENTIDADControllerTest.java. Haz dos o tres capturas si hace falta: anotaciones/inyectados, creación/actualización y eliminación/errores. No reduzcas el texto hasta hacerlo ilegible.
- Ejecución: .\mvnw.cmd test "-Dtest=ENTIDADControllerTest". Captura el nombre de clase, Tests run, Failures, Errors, Skipped y BUILD SUCCESS únicamente si aparece. Se esperan 9 pruebas por clase; si falla, corrige antes de entregar.
- URL: https://github.com/LuisMaldo3/LAB12
- Interacciones de GitHub: captura el commit real que agregó esa entidad, mostrando repositorio, mensaje, autor, fecha, hash y archivos cambiados. Si trabajaron con ramas y PR, captura también el PR y su merge real. No inventes revisiones, comentarios o colaboradores.
- Complemento opcional: pestaña Actions con el flujo terminado y el paso de pruebas expandido. No sustituye la captura del código.

## Subir a tu repositorio sin reemplazar su historial
Ejecuta lo siguiente desde la carpeta que contiene este README. Se crea un clon separado. Si LAB12-entrega ya existe, usa otro nombre en los tres lugares. Si Git detecta conflictos o cambios remotos, no uses --force.

```powershell
git clone https://github.com/LuisMaldo3/LAB12.git ..\LAB12-entrega
robocopy . ..\LAB12-entrega /E /XD .git .idea target /XF *.iml
cd ..\LAB12-entrega
.\mvnw.cmd test "-Dtest=VetControllerTest,OwnerControllerTest,SpecialtyControllerTest,VisitControllerTest"
git status --short
git add .
git commit -m "feat: completar pruebas de integracion Vet Owner Specialty y Visit"
git push origin HEAD
```

Robocopy usa códigos 0 a 7 para resultados sin fallo fatal; revisa su resumen. Si ya hay trabajo distinto en LAB12, respáldalo y revisa git diff antes de confirmar la copia. Este paquete no fue subido a GitHub por el asistente.

## Iniciar la API si lo necesitas
```powershell
.\mvnw.cmd spring-boot:run
```
Endpoints: /pets, /vets, /owners, /specialties y /visits en http://localhost:8080. Los cuatro nuevos ofrecen GET/POST y GET/PUT/DELETE con /{id}. Creación 201, eliminación 204, ID inexistente 404 y campos incompletos 400. Visit exige petId existente. El alcance es académico; no se agregó autenticación.
