import java.util.Scanner;

/**
 * Primer avance — Aplicación de Gestión de Inventarios.
 * Estructuras de Datos (SOFT-10), SCV5, C2-2026.
 * Docente: Romario Salas Cerdas.
 *
 * Implementa una lista enlazada simple para la gestión de productos
 * con un menú CLI intuitivo, validaciones robustas y buenas prácticas.

 */
public class Main {

    /**
     * Muestra el menú principal y gestiona el flujo de interacción
     * con el usuario mediante un ciclo continuo.
     *
     * @param lista   ListaProductos sobre la cual se realizarán las operaciones
     * @param scanner Scanner para leer la entrada del usuario
     */
    public static void menu(ListaProductos lista, Scanner scanner) {
        int opcion;

        do {
            System.out.println("   GESTIÓN DE INVENTARIOS");
            System.out.println("  1. Agregar producto al inicio");
            System.out.println("  2. Agregar producto al final");
            System.out.println("  3. Ver todos los productos");
            System.out.println("  4. Buscar producto por nombre");
            System.out.println("  5. Modificar producto");
            System.out.println("  6. Agregar imagen a un producto");
            System.out.println("  7. Eliminar producto");
            System.out.println("  8. Generar reporte de costos");
            System.out.println("  9. Salir");
            System.out.print("  Seleccione una opción: ");

            opcion = leerEntero(scanner);

            System.out.println();
            switch (opcion) {
                case 1 -> agregarProducto(scanner, lista, true);  // al inicio
                case 2 -> agregarProducto(scanner, lista, false); // al final
                case 3 -> { lista.listarProductos(); pausar(scanner); }
                case 4 -> buscarProducto(scanner, lista);
                case 5 -> modificarProducto(scanner, lista);
                case 6 -> agregarImagen(scanner, lista);
                case 7 -> eliminarProducto(scanner, lista);
                case 8 -> { lista.generarReporteCostos(); pausar(scanner); }
                case 9 -> System.out.println("  ¡Hasta luego!");
                default -> {
                    System.out.println("  Opción inválida. Intente de nuevo.\n");
                    pausar(scanner);
                }
            }
        } while (opcion != 9);
    }

    // UTILS

    /**
     * Lee un número entero desde el scanner, manejando entradas inválidas.
     * @return Entero válido ingresado por el usuario
     */
    private static int leerEntero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("  Entrada inválida. Ingrese un número: ");
            scanner.next(); // descarta la entrada incorrecta
        }
        return scanner.nextInt();
    }

    /**
     * Lee un número double desde el scanner, manejando entradas inválidas.
     * @return Double válido ingresado por el usuario
     */
    private static double leerDouble(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("  Entrada inválida. Ingrese un número: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    /**
     * Lee una línea completa de texto, manejando el salto de línea
     * pendiente que dejan nextInt(), nextDouble(), etc.
     * Útil para leer nombres compuestos (ej: "Leche Entera").
     * @return Línea de texto ingresada por el usuario, sin espacios al inicio/final
     */
    private static String leerTexto(Scanner scanner) {
        scanner.nextLine(); // descarta el \n pendiente
        return scanner.nextLine().trim();
    }

    /**
     * Pausa la ejecución hasta que el usuario presione Enter.
     * Debe llamarse cuando el buffer tiene un \n pendiente.
     */
    private static void pausar(Scanner scanner) {
        System.out.print("  Presione Enter para continuar...");
        scanner.nextLine(); // consume el \n pendiente
        scanner.nextLine(); // espera a que el usuario presione Enter
    }

    /**
     * Valida que un precio sea mayor a cero.
     * @param precio Precio a validar
     * @return true si es válido
     */
    private static boolean validarPrecio(double precio) {
        return precio > 0;
    }

    /**
     * Valida que una cantidad sea mayor o igual a cero.
     * @param cantidad Cantidad a validar
     * @return true si es válida
     */
    private static boolean validarCantidad(int cantidad) {
        return cantidad >= 0;
    }

    /**
     * Valida que un nombre no esté vacío.
     * @param nombre Nombre a validar
     * @return true si es válido
     */
    private static boolean validarNombre(String nombre) {
        return nombre != null && !nombre.isBlank();
    }

    /**
     * Valida que una ruta de imagen pertenezca al directorio images del proyecto.
     * @param ruta Ruta a validar
     * @return true si la ruta es válida
     */
    private static boolean validarRutaImagen(String ruta) {
        return ruta != null && !ruta.isBlank() && ruta.startsWith("images/") && !ruta.equals("images/");
    }

    /**
     * Verifica si un nombre ya pertenece a otro producto de la lista.
     */
    private static boolean existeOtroProductoConNombre(ListaProductos lista, String nombre, String nombreActual) {
        NodoProducto encontrado = lista.buscar(nombre);
        return encontrado != null && !encontrado.getProducto().getNombre().equalsIgnoreCase(nombreActual);
    }

    /**
     * Lee un precio opcional desde una línea de texto. Si el usuario deja vacío,
     * conserva el valor actual. Si escribe un dato inválido, vuelve a solicitarlo.
     */
    private static double leerPrecioOpcional(Scanner scanner, double valorActual) {
        while (true) {
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                return valorActual;
            }

            try {
                double precio = Double.parseDouble(entrada);
                if (validarPrecio(precio)) {
                    return precio;
                }
                System.out.print("  ERROR: El precio debe ser mayor a cero. Ingrese nuevamente: ");
            } catch (NumberFormatException e) {
                System.out.print("  ERROR: Debe ingresar un número válido. Ingrese nuevamente: ");
            }
        }
    }

    /**
     * Lee una cantidad opcional desde una línea de texto. Si el usuario deja vacío,
     * conserva el valor actual. Si escribe un dato inválido, vuelve a solicitarlo.
     */
    private static int leerCantidadOpcional(Scanner scanner, int valorActual) {
        while (true) {
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                return valorActual;
            }

            try {
                int cantidad = Integer.parseInt(entrada);
                if (validarCantidad(cantidad)) {
                    return cantidad;
                }
                System.out.print("  ERROR: La cantidad no puede ser negativa. Ingrese nuevamente: ");
            } catch (NumberFormatException e) {
                System.out.print("  ERROR: Debe ingresar un número entero válido. Ingrese nuevamente: ");
            }
        }
    }

    // Menu

    /**
     * Solicita los datos de un producto con validaciones y lo agrega a la lista.
     * Valida: nombre no vacío, sin duplicados, precio > 0, cantidad >= 0.
     *
     * @param scanner  Scanner para leer datos
     * @param lista    Lista donde se insertará
     * @param alInicio true → inserta al inicio, false → al final
     */
    private static void agregarProducto(Scanner scanner, ListaProductos lista, boolean alInicio) {
        System.out.println("  AGREGAR PRODUCTO ");

        // Validar nombre (no vacío, sin duplicados)
        // NOTA: la primera lectura usa leerTexto() para consumir el \n pendiente
        //       del menú (nextInt). Los reintentos usan nextLine() directo.
        String nombre;
        System.out.print("  Nombre: ");
        nombre = leerTexto(scanner);
        while (!validarNombre(nombre) || lista.buscar(nombre) != null) {
            if (!validarNombre(nombre)) {
                System.out.println("  ERROR: El nombre no puede estar vacío.");
            } else {
                System.out.println("  ERROR: Ya existe un producto con ese nombre.");
            }
            System.out.print("  Nombre: ");
            nombre = scanner.nextLine().trim();
        }

        // Validar precio (> 0)
        double precio;
        System.out.print("  Precio: ");
        precio = leerDouble(scanner);
        while (!validarPrecio(precio)) {
            System.out.println("  ERROR: El precio debe ser mayor a cero.");
            System.out.print("  Precio: ");
            precio = leerDouble(scanner);
        }

        // Validar categoría (no vacía)
        String categoria;
        System.out.print("  Categoría: ");
        categoria = leerTexto(scanner);
        while (!validarNombre(categoria)) {
            System.out.println("  ERROR: La categoría no puede estar vacía.");
            System.out.print("  Categoría: ");
            categoria = scanner.nextLine().trim();
        }

        System.out.print("  Fecha de vencimiento (deje vacío si no aplica): ");
        String fechaVencimiento = scanner.nextLine().trim();
        if (fechaVencimiento.isEmpty()) {
            fechaVencimiento = null;
        }

        // Validar cantidad (>= 0)
        int cantidad;
        while (true) {
            System.out.print("  Cantidad en inventario: ");
            cantidad = leerEntero(scanner);
            if (validarCantidad(cantidad)) break;
            System.out.println("  ERROR: La cantidad no puede ser negativa.");
        }

        Producto producto = new Producto(nombre, precio, categoria, fechaVencimiento, cantidad);

        if (alInicio) {
            lista.insertarAlInicio(producto);
            System.out.println("  Producto agregado al inicio.");
        } else {
            lista.insertarAlFinal(producto);
            System.out.println("  Producto agregado al final.");
        }

        pausar(scanner);
    }

    /**
     * Busca un producto por nombre y muestra sus datos.
     */
    private static void buscarProducto(Scanner scanner, ListaProductos lista) {
        System.out.println("  BUSCAR PRODUCTO ");
        System.out.print("  Nombre del producto: ");
        String nombre = leerTexto(scanner);

        NodoProducto nodo = lista.buscar(nombre);
        if (nodo == null) {
            System.out.println("  Producto no encontrado.");
        } else {
            System.out.println("  Producto encontrado:");
            System.out.println("  " + nodo.getProducto());
        }

        // Buffer limpio después de leerTexto, usamos pausaLimpia
        System.out.print("  Presione Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Solicita los datos actualizados de un producto y lo modifica.
     * Permite dejar campos vacíos para conservar el valor actual.
     */
    private static void modificarProducto(Scanner scanner, ListaProductos lista) {
        System.out.println("  MODIFICAR PRODUCTO ");
        System.out.print("  Nombre del producto a modificar: ");
        String nombreActual = leerTexto(scanner);

        NodoProducto nodo = lista.buscar(nombreActual);
        if (nodo == null) {
            System.out.println("  Producto no encontrado.");
            System.out.print("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Producto original = nodo.getProducto();
        System.out.println("  Datos actuales: " + original);
        System.out.println("  Ingrese los nuevos datos (deje vacío para conservar el valor actual):");

        System.out.print("  Nuevo nombre [" + original.getNombre() + "]: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            nombre = original.getNombre();
        }
        while (!validarNombre(nombre) || existeOtroProductoConNombre(lista, nombre, nombreActual)) {
            if (!validarNombre(nombre)) {
                System.out.println("  ERROR: El nombre no puede estar vacío.");
            } else {
                System.out.println("  ERROR: Ya existe otro producto con ese nombre.");
            }
            System.out.print("  Nuevo nombre [" + original.getNombre() + "]: ");
            nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                nombre = original.getNombre();
            }
        }

        System.out.print("  Nuevo precio [" + String.format("%.2f", original.getPrecio()) + "]: ");
        double precio = leerPrecioOpcional(scanner, original.getPrecio());

        System.out.print("  Nueva categoría [" + original.getCategoria() + "]: ");
        String categoria = scanner.nextLine().trim();
        if (categoria.isEmpty()) categoria = original.getCategoria();

        String fechaActual = (original.getFechaVencimiento() == null) ? "N/A" : original.getFechaVencimiento();
        System.out.print("  Nueva fecha de vencimiento [" + fechaActual + "]: ");
        String fechaVencimiento = scanner.nextLine().trim();
        if (fechaVencimiento.isEmpty()) {
            fechaVencimiento = original.getFechaVencimiento();
        }

        System.out.print("  Nueva cantidad [" + original.getCantidad() + "]: ");
        int cantidad = leerCantidadOpcional(scanner, original.getCantidad());

        Producto nuevosDatos = new Producto(nombre, precio, categoria, fechaVencimiento, cantidad);
        boolean exito = lista.modificarProducto(nombreActual, nuevosDatos);

        if (exito) {
            System.out.println("  Producto modificado correctamente.");
        } else {
            System.out.println("  Error al modificar el producto.");
        }

        System.out.print("  Presione Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Agrega una ruta de imagen a la lista de imágenes de un producto.
     */
    private static void agregarImagen(Scanner scanner, ListaProductos lista) {
        System.out.println("  AGREGAR IMAGEN ");
        System.out.print("  Nombre del producto: ");
        String nombre = leerTexto(scanner);

        NodoProducto nodo = lista.buscar(nombre);
        if (nodo == null) {
            System.out.println("  Producto no encontrado.");
            System.out.print("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.print("  Ruta de la imagen (ej: images/producto.jpg): ");
        String ruta = scanner.nextLine().trim();
        while (!validarRutaImagen(ruta)) {
            System.out.println("  ERROR: La ruta debe iniciar con images/ y no puede estar vacía.");
            System.out.print("  Ruta de la imagen (ej: images/producto.jpg): ");
            ruta = scanner.nextLine().trim();
        }

        boolean exito = lista.agregarImagen(nombre, ruta);
        if (exito) {
            System.out.println("  Imagen agregada correctamente.");
        } else {
            System.out.println("  Error al agregar la imagen.");
        }

        System.out.print("  Presione Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Elimina un producto de la lista previa confirmación del usuario.
     */
    private static void eliminarProducto(Scanner scanner, ListaProductos lista) {
        System.out.println("  ELIMINAR PRODUCTO ");
        System.out.print("  Nombre del producto a eliminar: ");
        String nombre = leerTexto(scanner);

        NodoProducto nodo = lista.buscar(nombre);
        if (nodo == null) {
            System.out.println("  Producto no encontrado.");
            System.out.print("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        // Confirmación antes de eliminar
        System.out.println("  Producto encontrado: " + nodo.getProducto().getNombre());
        System.out.print("  ¿Está seguro que desea eliminarlo? (s/n): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();

        if (confirmacion.equals("s") || confirmacion.equals("si")) {
            boolean exito = lista.eliminar(nombre);
            if (exito) {
                System.out.println("  Producto eliminado correctamente.");
            } else {
                System.out.println("  Error al eliminar el producto.");
            }
        } else {
            System.out.println("  Eliminación cancelada.");
        }

        System.out.print("  Presione Enter para continuar...");
        scanner.nextLine();
    }


    /**
     * Método principal. Muestra el encabezado del programa,
     * inicializa la lista y el scanner, e invoca el menú interactivo.
     */
    public static void main(String[] args) {
        System.out.println();
        System.out.println("     SISTEMA DE GESTIÓN DE INVENTARIOS           ");
        System.out.println();

        ListaProductos inventario = new ListaProductos();
        Scanner scanner = new Scanner(System.in);

        menu(inventario, scanner);

        scanner.close();
    }
}
