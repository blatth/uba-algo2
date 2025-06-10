package aed;

public class Bloque {
    private ListaEnlazada<Transaccion> transacciones;
    private HeapMax<Transaccion> heap;
    private int sumaMontos;
    private ListaEnlazada<Transaccion>.HandleLista[] handleId;


    public Bloque(int capacidad) {
        this.transacciones = new ListaEnlazada<Transaccion>();
        this.heap = new HeapMax<Transaccion>(capacidad);
        this.sumaMontos = 0;
        this.handleId = new ListaEnlazada.HandleLista[capacidad];
    }

    public void agregarTransaccion(Transaccion t) {
        // guardamos la transacción y su nodo en la lista
        ListaEnlazada<Transaccion>.HandleLista h = transacciones.agregarAtrasHandle(t);
        handleId[t.id()] = h;

        heap.agregarElemento(t);

        if (t.id_comprador() != 0) {
            sumaMontos += t.monto();
        }
    }

    public Transaccion obtenerMaximaTransaccion() { // no sé si se usa
        return heap.obtenerMaximo(); // O(1)
    }

    public Transaccion extraerMaximaTransaccion() { // para sacar la transacción en hackearTx
        return heap.sacarMaximo(); // O(log n_b)
    }

    public void eliminarTransaccionPorId(int id) {
        transacciones.eliminarHandle(handleId[id]);
    }

    public void restarMonto(Transaccion t) { // para restar cuando se saca la transacción con hackearTx
    if (t.id_vendedor() != 0) { // no hay que tener en cuenta las de creación
        sumaMontos -= t.monto();
    }
    }

    public int getSumaMontos() {
        return sumaMontos;
    }

    public int promedioMontos() {
        int n = 0;
        for (int i = 0; i < transacciones.longitud(); i++) {
            if (transacciones.obtener(i).id_comprador() != 0) {
                n++;
            }
        }
        if (n > 0) {
            return sumaMontos / n;
        } else {
            return 0;
        }
    }

    public int numeroTransacciones() {
        return transacciones.longitud();
    }

    public ListaEnlazada<Transaccion> getTransacciones() {
        return transacciones;
    }
}