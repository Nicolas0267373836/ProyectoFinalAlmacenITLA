# Sistema de Gestión de Productos de Almacén

Aplicación de escritorio desarrollada con Java 17, Swing, AWT, JDBC y MySQL para administrar usuarios y productos de almacén.

## Funcionalidades

- Inicio de sesión con contraseña oculta y validación de campos obligatorios.
- Registro de usuarios con confirmación de contraseña.
- Panel principal con acceso a Gestión de Usuarios y Gestión de Productos.
- CRUD de usuarios: registrar, listar, actualizar y eliminar.
- CRUD de productos: registrar, listar, actualizar y eliminar nombre, marca, categoría, precio y cantidad disponible.
- Botones Volver y Cerrar Sesión para navegar entre las pantallas.
- Actualización automática de las tablas después de guardar o eliminar datos.

## Programación orientada a objetos y patrones

- **Abstracción:** `Persona` es una clase abstracta con los datos comunes.
- **Encapsulamiento:** los atributos de `Persona`, `Usuario` y `Producto` son privados y usan getters/setters.
- **Herencia:** `Usuario` extiende `Persona`.
- **Polimorfismo:** `Usuario` sobrescribe `tipoPersona()` y las pantallas trabajan con las interfaces `UsuarioDAO` y `ProductoDAO`.
- **Singleton:** `DatabaseConnection` centraliza la configuración de conexión.
- **Factory:** `DAOFactory` crea los DAO que utiliza la aplicación.

## Configuración de base de datos

1. Copia `database.properties.example` y nómbralo `database.properties` en la raíz del proyecto.
2. Completa los valores de conexión en `database.properties`.
3. Al iniciar, la aplicación crea las tablas `usuarios_20252437` y `productos_20252437` si no existen. El archivo `database/schema.sql` contiene la estructura de respaldo.

El archivo `database.properties` está excluido del repositorio para no publicar credenciales.

## Ejecutar

Con Java JDK 17 y Maven instalados:

```powershell
mvn clean package
java -jar target/sistema-almacen-itla.jar
```

También puedes usar `compilar_y_ejecutar.bat`.
