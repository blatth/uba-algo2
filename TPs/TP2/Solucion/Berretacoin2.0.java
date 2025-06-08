package aed;

// p: cantidad de usuarios
// n_b: cantidad de transacciones por bloques 

public class Berretacoin {

    private ListaEnlazada<Bloque> bloque;
    private Handle<Usuario> handles; // handles
    private heapMax<Usuario> usuarios;

    public Berretacoin(int n_usuarios){ // O(p)
        // O(p) Porque estamos creando un arreglo de handles de tamaño P
        // O(p) y tambien inicializamos el heapify a partir del arreglo saldos
        // O(1) ademas la lista dob enlazada de bloques 
        throw new UnsupportedOperationException("Implementar!");
    }

    public void agregarBloque(Transaccion[] transacciones){ // O(n_b * log(p))

        // O(n * log p) Actualizar saldos --> obtenerElemento del heapMax (utiliza handles) reordenamiento del heap 
        // O(1) agregar al final porque lista doblemente enlazada implementa .add
        // O(n_b) crear la lista doblemente enlazada de transacciones
        // O(log n_b) agregar transaccion al bloque
    
        throw new UnsupportedOperationException("Implementar!");
    }

    public Transaccion txMayorValorUltimoBloque(){ // O(1)
        // O(1) utilizamos obtenerMaximo del heapMax de transacciones
        
        throw new UnsupportedOperationException("Implementar!");
    }

    public Transaccion[] txUltimoBloque(){ // O(n_b)
        // O(n_b) Iteramos por el bloque con n_b transacciones y las copiamos
        // O(1) Lista doblemente enlazada nos da el .ulitmo

        throw new UnsupportedOperationException("Implementar!");
    }

    public int maximoTenedor(){ //O(1)
        // O(1) obtenerMaximo del heapmax
        throw new UnsupportedOperationException("Implementar!");
    }

    public int montoMedioUltimoBloque(){ // O(1)
        // O(1) .ultimo de lista enlazada de transacciones
        // O(1) .size de lista enlazada de transaccionse (bloque)
        // O(1) Obtenemos sumaMontos de bloque
        // O(1) Dividimos total por cantidad de transaccioens

        throw new UnsupportedOperationException("Implementar!");
    }

    public void hackearTx(){ // O(log n_b + log(p))
        // O(log n_b) obtenerMaximo de heapMax
        // O(log p) buscar id_vendedor y id_comprador y actualizar saldo y reordenar heapMax
        throw new UnsupportedOperationException("Implementar!");
    }
}


public class bloque {

    private ListaEnlazada<Transaccion> transacciones;
    private heapMax<Transaccion> heap;
    private int sumaMontos; // no sumar la transacción de creación
    // Como nos da la longitud .size de lista doblelmente enlazada le restamos 1 por la de creación

}

public class heapMax { // ver handle

    // vos tenes que hacer el heapify a partir del arreglo de saldos

    public heapMax agregarElemento()[ // encolar O(log x)

    ]
    public void obtenerMaximo(){ // consultarMax O(1)

    }
    public heapMax sacarMaximo(){ // desencolarMax O(log x)

    }
    public id obtenerElemento(){ // O(1)

    }

}

public class usuario {

    private int id_usuario;
    private int saldo;

}