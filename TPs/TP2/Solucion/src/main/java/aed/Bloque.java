package aed;

public class Bloque {
    private ListaEnlazada<Transaccion> transacciones;
    private HeapMax<Transaccion> heap;
    private ListaEnlazada<Transaccion>.HandleLista[] handleId;
    private int cantTransaccionesValidas;
    private int sumaMontos;

    public Bloque(int capacidad) {
        this.transacciones = new ListaEnlazada<Transaccion>();
        this.heap = new HeapMax<Transaccion>(capacidad);
        this.handleId = new ListaEnlazada.HandleLista[capacidad];
        this.cantTransaccionesValidas = 0;
        this.sumaMontos = 0;
    }

    public void agregarTransaccion(Transaccion t) {
        ListaEnlazada<Transaccion>.HandleLista h = transacciones.agregarAtrasHandle(t); // Se agrega un nuevo nodo a transacciones y h apunta a ese nodo
        handleId[t.id()] = h; // En el array de handles, en la posicion id se encuentra el handle h

        heap.agregarElemento(t); 

        if (t.id_comprador() != 0) { //Actualizamos sumaMontos diferenciando si la transaccion es de creacion (Si es de creacion, no la sumamos) 
            sumaMontos += t.monto();
            cantTransaccionesValidas++;
        }
    }

    public Transaccion obtenerMaximaTransaccion() {
        return heap.obtenerMaximo(); 
    }

    public Transaccion extraerMaximaTransaccion() { 
        return heap.sacarMaximo(); 
    }

    public void eliminarTransaccionPorId(int id) { 
        transacciones.eliminarHandle(handleId[id]); // handleId[id] apunta al nodo que queremos eliminar -> O(1) 
    }

    public void restarMonto(Transaccion t) { 
    if (t.id_comprador() != 0) { // Actualizamos si no es de creacion
        sumaMontos -= t.monto();
        cantTransaccionesValidas--;
    }
    }

    public int getSumaMontos() {
        return sumaMontos;
    }

    public int promedioMontos() {
        if (cantTransaccionesValidas > 0) {
            return sumaMontos / cantTransaccionesValidas;
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