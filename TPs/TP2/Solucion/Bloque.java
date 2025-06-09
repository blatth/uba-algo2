package aed;

public class Bloque {
    private ListaEnlazada<Transaccion> transacciones;
    private HeapMax<Transaccion> heap;
    private int sumaMontos;

    public Bloque(int capacidad) {
        this.transacciones = new ListaEnlazada<Transaccion>();
        this.heap = new HeapMax<Transaccion>(capacidad);
        this.sumaMontos = 0;
    }

    public void agregarTransaccion(Transaccion t) {
        transacciones.agregarAtras(t); // O(1)
        heap.agregarElemento(t); // O(log n_b)
        sumaMontos += t.monto(); // O(1)
    }

    public Transaccion obtenerMaximaTransaccion() {
        return heap.obtenerMaximo();
    }

    public int getSumaMontos() {
        return sumaMontos;
    }

    public int numeroTransacciones() {
        return transacciones.longitud();
    }

    public ListaEnlazada<Transaccion> getTransacciones() {
        return transacciones;
    }
}
