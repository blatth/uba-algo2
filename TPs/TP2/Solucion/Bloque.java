package aed;

public class Bloque {
    private ListaEnlazada<Transaccion> transacciones;
    private HeapMax<Transaccion> heap;
    private int sumaMontos;

    public Bloque(Transaccion[] transc) {
        transacciones = new ListaEnlazada<>(); // creo una lista doblemente enlazada de transacciones para acumular
            Handle<Transaccion>[] handles = new Handle[transc.length]; // creo un handle con la misma len que transc (el input)
            for (int i = 0; i < transc.length; i++) { // itero sobre transc (el input)
                transacciones.agregarAtras(transc[i]); // agrego en la listaenlazada los elem de mi transc (el input)
                if (transc[i].id_vendedor() != 0) { // no tengo en cuenta la creación
                    sumaMontos += transc[i].monto(); // sumo los montos de todos los demás valores
                }
                handles[i] = new Handle<>(transc[i], i); // guardo los valores y la posición de cada uno en el array de handles
            }
            heap = new HeapMax<>(handles); // creo un heap que organiza todas las transacciones según heapmax
        }

    public Bloque(int capacidad) {
        this.transacciones = new ListaEnlazada<Transaccion>();
        this.heap = new HeapMax<Transaccion>(capacidad);
        this.sumaMontos = 0;
    }

    public void agregarTransaccion(Transaccion t) {
        transacciones.agregarAtras(t); // O(1)
        heap.agregarElemento(t); // O(log n_b)
        if (t.id_vendedor() != 0) { // no hay que tener en cuenta las de creación
            sumaMontos += t.monto(); // O(1), solo si no es creación
        }
    }

    public Transaccion obtenerMaximaTransaccion() { // no sé si se usa
        return heap.obtenerMaximo();
    }

    public Transaccion extraerMaximaTransaccion() { // para sacar la transacción en hackearTx
        return heap.sacarMaximo(); // O(log n_b)
    }

    public void restarMonto(Transaccion t) { // para restar cuando se saca la transacción con hackearTx
    if (t.id_vendedor() != 0) { // no hay que tener en cuenta las de creación
        sumaMontos -= t.monto();
    }
    }

    public int getSumaMontos() {
        return sumaMontos;
    }

    public int promedioMontos() { // para usar en montoMedioUltimoBloque
        int n = transacciones.size() - 1;
        if (n > 0) {
            return sumaMontos / n;
        } else {
            return 0;
    }
}

    public int numeroTransacciones() {
        return transacciones.size();
    }

    public ListaEnlazada<Transaccion> getTransacciones() {
        return transacciones;
    }
}
