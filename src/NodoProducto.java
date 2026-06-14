/**
 * Nodo para la lista enlazada simple ListaProductos.
 * Cada nodo almacena un objeto Producto y una referencia al siguiente nodo.
 */
public class NodoProducto {

    // Atributos
    private Producto producto;
    private NodoProducto siguiente;

    // Constructor

    /**
     * Crea un nodo con el producto dado.
     * El puntero siguiente se inicializa en null.
     *
     * @param producto Producto que almacena este nodo
     */
    public NodoProducto(Producto producto) {
        this.producto = producto;
        this.siguiente = null;
    }

    // Getters y Setters

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public NodoProducto getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoProducto siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public String toString() {
        return producto.toString();
    }
}
