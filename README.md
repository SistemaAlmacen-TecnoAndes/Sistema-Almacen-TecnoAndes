# Sistema Web de Gestión de Almacén - TecnoAndes Perú S.A.C.

Sistema web para la gestión de almacén de una MYPE dedicada a la comercialización de artículos de cómputo en Lima Metropolitana.

## 🚀 Tecnologías

- **Backend:** Java 17 + Spring Boot 3.x
- **Frontend:** Thymeleaf + Bootstrap 5
- **Base de datos:** MySQL 8.x
- **Control de versiones:** Git + GitHub

## 📋 Módulos del Sistema

- 🔐 Autenticación (Login/Logout)
- 👥 Usuarios (solo Administrador)
- 📦 Productos
- 🗂️ Categorías
- 🚚 Proveedores
- 👤 Clientes
- 📥 Entradas
- 📤 Salidas
- 📋 Inventario/Kardex
- 📈 Dashboard
- 📊 Reportes

## 🗄️ Base de Datos

12 tablas relacionales:

1. `usuarios`
2. `roles`
3. `usuario_rol`
4. `categorias`
5. `productos`
6. `proveedores`
7. `clientes`
8. `entradas`
9. `detalle_entrada`
10. `salidas`
11. `detalle_salida`
12. `movimientos`

## 👥 Roles del Sistema

| Rol | Permisos |
|-----|----------|
| **ADMIN** | Acceso total + gestión de usuarios |
| **USUARIO** | Acceso a módulos operativos |

## 🛠️ Instalación

### Requisitos previos

- Java JDK 17 o superior
- Maven 3.8+
- MySQL 8.x
- Git

### Pasos

1. Clonar el repositorio:
```bash
git clone https://github.com/VFrank2384/Sistema-Almacen-TecnoAndes.git