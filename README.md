# Primer Avance — Gestión de Inventarios

Sistema interactivo de gestión de inventarios por consola, implementado en Java como proyecto académico para la materia **Estructuras de Datos (SOFT-10)** — SCV5, C2-2026.

**Docente:** Romario Salas Cerdas

---

## Funcionalidades

- **Agregar productos** al inicio o al final del inventario
- **Listar** todos los productos registrados
- **Buscar** producto por nombre (sin distinguir mayúsculas/minúsculas)
- **Modificar** producto: permite editar campos individualmente o dejar vacío para conservar el valor actual
- **Eliminar** producto con confirmación previa
- **Asociar imágenes** a un producto (rutas dentro de `images/`)
- **Generar reporte de costos** con desglose por producto y costo total acumulado (precio × cantidad)

## Validaciones incluidas

- Nombre obligatorio y sin duplicados
- Precio debe ser mayor a cero
- Categoría obligatoria
- Cantidad no puede ser negativa
- Rutas de imagen deben iniciar con `images/`
- Entrada robusta ante formatos inválidos (letras donde se esperan números, etc.)

## Estructura del proyecto

```
.
├── README.md
├── images/                  # Imágenes de productos
├── out/                     # Archivos .class compilados (gitignored)
└── src/
    ├── Main.java            # CLI, menú interactivo y validaciones
    ├── Producto.java        # Modelo de datos del producto
    ├── NodoProducto.java    # Nodo para la lista enlazada simple
    └── ListaProductos.java  # Lista enlazada simple con operaciones CRUD
```

## Arquitectura de clases

- **`Producto`** — Modelo con nombre, precio, categoría, fecha de vencimiento (nullable), cantidad en inventario y lista de rutas de imágenes.
- **`NodoProducto`** — Nodo envolvente que almacena un `Producto` y una referencia al siguiente nodo (`siguiente`).
- **`ListaProductos`** — Lista enlazada simple (singly linked list). Implementa inserción al inicio, al final, búsqueda lineal por nombre, modificación in-place, eliminación, listado completo y generación de reporte de costos.
- **`Main`** — Controlador con menú interactivo cíclico, manejo de entrada con `Scanner`, validaciones en capa de presentación y flujo de pausa/presentación.

## Requisitos

- Java 17 o superior

## Compilar y ejecutar

```bash
javac src/*.java -d out && java -cp out Main
```

El comando compila todos los archivos `.java` en `src/`, coloca los `.class` en `out/`, y ejecuta la clase `Main`.

## Menú de opciones

```
  1. Agregar producto al inicio
  2. Agregar producto al final
  3. Ver todos los productos
  4. Buscar producto por nombre
  5. Modificar producto
  6. Agregar imagen a un producto
  7. Eliminar producto
  8. Generar reporte de costos
  9. Salir
```
