/**
 * Lista enlazada simple para la gestión de productos.
 * Implementa inserción al inicio, al final, búsqueda, modificación,
 * eliminación y generación de reporte de costos.
 */
public class ListaProductos {

    private NodoProducto primero;

    /**
     * Crea una lista vacía.
     */
    public ListaProductos() {
        primero = null;
    }

    // --------------------------------
    // Métodos de control
    // --------------------------------

    /**
     * Verifica si la lista está vacía.
     * @return true si no hay elementos, false en caso contrario
     */
    public boolean estaVacia() {
        return primero == null;
    }

    public NodoProducto getPrimero() {
        return primero;
    }

    public void setPrimero(NodoProducto primero) {
        this.primero = primero;
    }

    // --------------------------------
    // Inserción
    // --------------------------------

    /**
     * Inserta un nuevo producto al inicio de la lista.
     * @param producto Producto a insertar
     */
    public void insertarAlInicio(Producto producto) {
        NodoProducto nuevoNodo = new NodoProducto(producto);
        nuevoNodo.setSiguiente(primero);
        primero = nuevoNodo;
    }

    /**
     * Inserta un nuevo producto al final de la lista.
     * @param producto Producto a insertar
     */
    public void insertarAlFinal(Producto producto) {
        NodoProducto nuevoNodo = new NodoProducto(producto);

        if (estaVacia()) {
            primero = nuevoNodo;
            return;
        }

        NodoProducto tmp = primero;
        while (tmp.getSiguiente() != null) {
            tmp = tmp.getSiguiente();
        }
        tmp.setSiguiente(nuevoNodo);
    }

    // --------------------------------
    // Búsqueda
    // --------------------------------

    /**
     * Busca un producto por su nombre (ignorando mayúsculas/minúsculas).
     * @param nombre Nombre del producto a buscar
     * @return El nodo que contiene el producto, o null si no se encuentra
     */
    public NodoProducto buscar(String nombre) {
        if (estaVacia()) {
            return null;
        }

        NodoProducto tmp = primero;
        while (tmp != null) {
            if (tmp.getProducto().getNombre().equalsIgnoreCase(nombre)) {
                return tmp;
            }
            tmp = tmp.getSiguiente();
        }
        return null;
    }

    // --------------------------------
    // Modificación
    // --------------------------------

    /**
     * Modifica los datos de un producto existente.
     * Busca por nombre original y reemplaza con los nuevos datos.
     *
     * @param nombreActual Nombre actual del producto a modificar
     * @param nuevosDatos  Objeto Producto con los datos actualizados
     * @return true si se modificó correctamente, false si no se encontró
     */
    public boolean modificarProducto(String nombreActual, Producto nuevosDatos) {
        NodoProducto nodo = buscar(nombreActual);
        if (nodo == null) {
            return false;
        }

        Producto p = nodo.getProducto();
        p.setNombre(nuevosDatos.getNombre());
        p.setPrecio(nuevosDatos.getPrecio());
        p.setCategoria(nuevosDatos.getCategoria());
        p.setFechaVencimiento(nuevosDatos.getFechaVencimiento());
        p.setCantidad(nuevosDatos.getCantidad());
        // NOTA: no se reemplaza la lista de imágenes — se maneja por separado
        return true;
    }

    /**
     * Agrega una ruta de imagen a la lista de imágenes de un producto.
     *
     * @param nombreProducto Nombre del producto
     * @param rutaImagen     Ruta de la imagen a agregar
     * @return true si se agregó correctamente, false si no se encontró el producto
     */
    public boolean agregarImagen(String nombreProducto, String rutaImagen) {
        NodoProducto nodo = buscar(nombreProducto);
        if (nodo == null) {
            return false;
        }

        nodo.getProducto().agregarImagen(rutaImagen);
        return true;
    }

    // --------------------------------
    // Eliminación
    // --------------------------------

    /**
     * Elimina un producto de la lista por su nombre.
     * @param nombre Nombre del producto a eliminar
     * @return true si se eliminó correctamente, false si no se encontró
     */
    public boolean eliminar(String nombre) {
        if (estaVacia()) {
            return false;
        }

        // Caso 1: el producto está al inicio
        if (primero.getProducto().getNombre().equalsIgnoreCase(nombre)) {
            primero = primero.getSiguiente();
            return true;
        }

        // Caso 2: el producto está en medio o al final
        NodoProducto tmp = primero;
        while (tmp.getSiguiente() != null) {
            if (tmp.getSiguiente().getProducto().getNombre().equalsIgnoreCase(nombre)) {
                tmp.setSiguiente(tmp.getSiguiente().getSiguiente());
                return true;
            }
            tmp = tmp.getSiguiente();
        }

        return false;
    }

    // --------------------------------
    // Listado
    // --------------------------------

    /**
     * Imprime todos los productos de la lista en consola.
     */
    public void listarProductos() {
        if (estaVacia()) {
            System.out.println("No hay productos registrados.\n");
            return;
        }

        System.out.println("=== LISTA DE PRODUCTOS ===");
        NodoProducto tmp = primero;
        int contador = 1;
        while (tmp != null) {
            System.out.println(contador + ". " + tmp.getProducto());
            tmp = tmp.getSiguiente();
            contador++;
        }
        System.out.println("Total de productos: " + (contador - 1) + "\n");
    }

    // --------------------------------
    // Reporte de costos
    // --------------------------------

    /**
     * Recorre la lista e imprime un reporte con el costo total de cada producto
     * (precio × cantidad) y el costo total acumulado de toda la lista.
     */
    public void generarReporteCostos() {
        if (estaVacia()) {
            System.out.println("No hay productos para generar el reporte.\n");
            return;
        }

        System.out.println("=== REPORTE DE COSTOS ===");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-20s %-10s %-10s %-12s%n", "Producto", "Precio", "Cantidad", "Costo Total");
        System.out.println("------------------------------------------------------------");

        double costoAcumulado = 0.0;
        NodoProducto tmp = primero;

        while (tmp != null) {
            Producto p = tmp.getProducto();
            double costoTotal = p.calcularCostoTotal();
            costoAcumulado += costoTotal;

            System.out.printf("%-20s ₡%-9.2f %-10d ₡%-10.2f%n",
                    p.getNombre(), p.getPrecio(), p.getCantidad(), costoTotal);
            tmp = tmp.getSiguiente();
        }

        System.out.println("------------------------------------------------------------");
        System.out.printf("%-42s ₡%-10.2f%n", "COSTO TOTAL ACUMULADO:", costoAcumulado);
        System.out.println();
    }
}
