import java.util.ArrayList;

/**
 * Representa un producto dentro del sistema de gestión de inventarios.
 * Cada producto tiene nombre, precio, categoría, fecha de vencimiento opcional,
 * una cantidad en inventario y una lista de rutas de imágenes asociadas.
 */
public class Producto {

    // Atributos
    private String nombre;
    private double precio;
    private String categoria;
    private String fechaVencimiento; // nullable — "si aplica"
    private int cantidad;
    private ArrayList<String> listaImagenes;

    // Constructor

    /**
     * Crea un producto con todos los datos básicos.
     * La lista de imágenes se inicializa vacía.
     *
     * @param nombre           Nombre del producto
     * @param precio           Precio unitario
     * @param categoria        Categoría del producto
     * @param fechaVencimiento Fecha de vencimiento (puede ser null si no aplica)
     * @param cantidad         Cantidad en inventario
     */
    public Producto(String nombre, double precio, String categoria,
                    String fechaVencimiento, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
        this.listaImagenes = new ArrayList<>();
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ArrayList<String> getListaImagenes() {
        return listaImagenes;
    }

    public void setListaImagenes(ArrayList<String> listaImagenes) {
        this.listaImagenes = listaImagenes;
    }

    // Métodos adicionales

    /**
     * Agrega una ruta de imagen a la lista del producto.
     * @param ruta Ruta de la imagen dentro del proyecto
     * @return true si la ruta se agregó correctamente
     */
    public boolean agregarImagen(String ruta) {
        if (ruta == null || ruta.isBlank()) {
            return false;
        }

        listaImagenes.add(ruta.trim());
        return true;
    }

    /**
     * Calcula el costo total de este producto (precio × cantidad).
     * @return Costo total del producto
     */
    public double calcularCostoTotal() {
        return precio * cantidad;
    }

    @Override
    public String toString() {
        String vence = (fechaVencimiento == null || fechaVencimiento.isBlank())
                ? "N/A" : fechaVencimiento;
        return "Producto: " + nombre +
                " | Precio: ₡" + String.format("%.2f", precio) +
                " | Categoría: " + categoria +
                " | Vence: " + vence +
                " | Cantidad: " + cantidad +
                " | Imágenes: " + listaImagenes.size();
    }
}
