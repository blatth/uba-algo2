package aed;
import java.util.ArrayList;

// p: cantidad de usuarios
// n_b: cantidad de transacciones por bloques 

public class Berretacoin {

    private ListaEnlazada<Bloque> bloques;
    private ArrayList<Handle<Usuario>> handles; // handles
    private HeapMax<Usuario> usuarios;

    public Berretacoin(int n_usuarios) { // O(p)

        this.handles = new ArrayList<Handle<Usuario>>(n_usuarios); // se crea un ArrayList para guardar los handles de cada usuario para acceder a cada usuario por su id
        Handle<Usuario>[] arrayHandles = (Handle<Usuario>[]) new Handle[n_usuarios]; // se crea un array de handles que se va a usar para construir el heap

        for (int i = 0; i < n_usuarios; i++) {
            Usuario usuario = new Usuario(i + 1, 0); // ids comienzan en 1 - O(P)
            Handle<Usuario> handle = new Handle<>(usuario, i); // se crea un handle para cada usuario con su pos "actual" en el heap
            arrayHandles[i] = handle; // voy guardando los handles en el array
            this.handles.add(handle); // voy guardando los handles en el ArrayList para poder acceder por ids
        }

        this.usuarios = new HeapMax<>(arrayHandles, n_usuarios); // heapify para que se cumpla O(P)
        this.bloques = new ListaEnlazada<Bloque>();
    }

    public void agregarBloque(Transaccion[] transacciones) { // O(n_b * log(P))

        for (Transaccion t : transacciones) { // O(n_b)

            if (t.id_comprador() != 0) {

                Handle<Usuario> handleComprador = handles.get(t.id_comprador() - 1);
                Usuario comprador = handleComprador.getElemento();
                comprador.saldo -= t.monto();
                usuarios.actualizar(handleComprador); // O(log p)
            }

            Handle<Usuario> handleVendedor = handles.get(t.id_vendedor() - 1);
            Usuario vendedor = handleVendedor.getElemento();
            vendedor.saldo += t.monto();
            usuarios.actualizar(handleVendedor); // O(log p)
        } // O(n_b * log(P)) : para cada transaccion hacemos dos actualizaciones, cada una en O(log P)

        int idMax = 0;
        for (Transaccion t : transacciones) { // O(n_b)
            if (t.id() > idMax) idMax = t.id();
        }

        Bloque nuevoBloque = new Bloque(idMax + 1);
        for (Transaccion t : transacciones) { // O(nb * log nb)
            nuevoBloque.agregarTransaccion(t);
        }

        bloques.agregarAtras(nuevoBloque);
    }

    public Transaccion txMayorValorUltimoBloque() {
        Bloque ult = bloques.obtener(bloques.longitud() - 1);
        return ult.obtenerMaximaTransaccion(); // O(1)
    }

    public Transaccion[] txUltimoBloque() {
        Bloque ult = bloques.obtener(bloques.longitud() - 1);
        int n = ult.numeroTransacciones();
        Transaccion[] arr = new Transaccion[n];
        for (int i = 0; i < n; i++) { // O(n_b)
            arr[i] = ult.getTransacciones().obtener(i);
        }
        return arr;
    }

    public int maximoTenedor() {
        Usuario top = usuarios.obtenerMaximo();
        return top.id; // O(1)
    }

    public int montoMedioUltimoBloque() {
        Bloque ultimo = bloques.obtener(bloques.longitud() - 1);
        return ultimo.promedioMontos(); // O(1)
    }

    public void hackearTx() {
        if (bloques.longitud() == 0) return;

        Bloque ult = bloques.obtener(bloques.longitud() - 1);
        if (ult.numeroTransacciones() == 0) return;

        Transaccion t = ult.extraerMaximaTransaccion(); // saco del heap - O(log nb)

        ult.restarMonto(t); // resto del total de montos si no es creación

        if (t.id_comprador() != 0) { // revierto los saldos
            Handle<Usuario> hV = handles.get(t.id_vendedor() - 1);
            Usuario v = hV.getElemento();
            v.saldo -= t.monto();
            usuarios.actualizar(hV); // O(log P)

            Handle<Usuario> hC = handles.get(t.id_comprador() - 1);
            Usuario c = hC.getElemento();
            c.saldo += t.monto();
            usuarios.actualizar(hC); // O(log P)
        } else {
            Handle<Usuario> hV = handles.get(t.id_vendedor() - 1);
            Usuario v = hV.getElemento();
            v.saldo -= t.monto();
            usuarios.actualizar(hV); // O(log P)
        }
        ult.eliminarTransaccionPorId(t.id());
    }
}







