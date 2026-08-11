# Sistema de Gestión de Productos de Almacén

Proyecto final desarrollado con Java 17, Swing, AWT, JDBC y MySQL remoto. Permite administrar usuarios y productos de almacén.

## Funcionalidades

- Login con contraseña oculta y validación de campos obligatorios.
- Registro de usuarios con todos los campos solicitados y confirmación de contraseña.
- Panel principal con botones con iconos para Gestión de Usuarios y Gestión de Productos.
- CRUD de usuarios: registrar, listar, actualizar y eliminar.
- CRUD de productos: registrar, listar, actualizar y eliminar nombre, marca, categoría, precio y cantidad disponible.
- Botones Volver para regresar al panel principal y Cerrar Sesión para regresar al login.
- Las tablas se actualizan automáticamente después de guardar o eliminar.
- Conexión a la base remota indicada para la asignación.

## Programación orientada a objetos y patrones

- **Abstracción:** `Persona` es una clase abstracta con los datos comunes.
- **Encapsulamiento:** los atributos de `Persona`, `Usuario` y `Producto` son privados y usan getters/setters.
- **Herencia:** `Usuario` extiende `Persona`.
- **Polimorfismo:** `Usuario` sobrescribe `tipoPersona()` y las pantallas usan las interfaces `UsuarioDAO` y `ProductoDAO`.
- **Singleton:** `DatabaseConnection` centraliza una sola configuración de conexión.
- **Factory:** `DAOFactory` crea los DAO que utiliza la aplicación.

Los comentarios en el código identifican los pilares y patrones usados.

## Configuración obligatoria

1. Copia `database.properties.example` y nómbralo `database.properties` en la raíz del proyecto.
2. En `database.properties`, completa únicamente esta línea con la contraseña entregada por el docente:

```properties
db.password=TU_CONTRASENA
```

3. Ejecuta `database/schema.sql` en la base remota `almacenitlafinal`. El archivo crea las tablas `usuarios_20252437` y `productos_20252437`, por lo que no mezcla tus datos con los de otros estudiantes.

No subas `database.properties` a GitHub; ya está incluido en `.gitignore`.

## Ejecutar

Con Java JDK 17 y Maven instalados:

```powershell
mvn clean package
java -jar target/sistema-almacen-itla.jar
```

También puedes usar `compilar_y_ejecutar.bat`.

## Video de entrega

El video debe durar aproximadamente 4 minutos, mostrar tu cara y enseñar: registro/login, panel principal, gestión de usuarios, gestión de productos, nuevo producto, edición, eliminación, volver y cerrar sesión.

## Autor

Nicolás Abud - 2025-2437
