package aed;
import java.util.ArrayList;

// P: cantidad de usuarios
// n_b: cantidad de transacciones por bloques 

public class Berretacoin {

    private ListaEnlazada<Bloque> bloques;
    private ArrayList<Handle<Usuario>> handles; 
    private HeapMax<Usuario> usuarios;

    public Berretacoin(int n_usuarios) {  

        this.handles = new ArrayList<Handle<Usuario>>(n_usuarios); // Se inicializa un ArrayList con capacidad P -> O(1)
        Handle<Usuario>[] arrayHandles = (Handle<Usuario>[]) new Handle[n_usuarios]; // Se crea un arreglo de Handle<Usuario> de tamaño P -> O(P)
        
        for (int i = 0; i < n_usuarios; i++) { // Se crean P usuarios y handles, y se insertan en el arreglo y el ArrayList -> O(P)
            Usuario usuario = new Usuario(i + 1, 0); // Ids comienzan en 1
            Handle<Usuario> handle = new Handle<>(usuario, i); // Se crea un handle para cada usuario con su pos "actual" en el heap
            arrayHandles[i] = handle; 
            this.handles.add(handle); 
        }

        this.usuarios = new HeapMax<>(arrayHandles, n_usuarios); // Se construye el heap con HeapMax(heap[], n) -> heapify en O(P)
        this.bloques = new ListaEnlazada<Bloque>(); // Se inicializa una lista enlazada vacía para bloques -> O(1)
    } // Total: O(P)

    public void agregarBloque(Transaccion[] transacciones) {  

        for (Transaccion t : transacciones) { // Para cada transacción -> O(n_b) 

            if (t.id_comprador() != 0) { // Diferenciamos transacciones de creacion
 
                Handle<Usuario> handleComprador = handles.get(t.id_comprador() - 1); // Se accede al comprador mediante handles -> O(1)
                Usuario comprador = handleComprador.getElemento();
                comprador.saldo -= t.monto(); // Se actualiza el saldo -> O(1)
                usuarios.actualizar(handleComprador); // Se actualiza el heap de usuarios -> O(log P)
            }

            Handle<Usuario> handleVendedor = handles.get(t.id_vendedor() - 1); 
            Usuario vendedor = handleVendedor.getElemento();
            vendedor.saldo += t.monto();
            usuarios.actualizar(handleVendedor); // O(log p)
        } // O(n_b * log(P)) : para cada transaccion hacemos dos actualizaciones, cada una en O(log P)

        int idMax = 0;
        for (Transaccion t : transacciones) { // Se obtiene el ID máximo de transacción -> O(nb)
            if (t.id() > idMax) idMax = t.id();
        }

        Bloque nuevoBloque = new Bloque(idMax + 1); 
        for (Transaccion t : transacciones) { // Hacemos una copia de un conjunto a un heap -> O(n_b) 
            nuevoBloque.agregarTransaccion(t);
        }

        bloques.agregarAtras(nuevoBloque); // Se agrega el bloque a la lista -> O(1)
    } // Total: O(nb × log P)

    public Transaccion txMayorValorUltimoBloque() { 
        Bloque ult = bloques.obtener(bloques.longitud() - 1); // Se accede al último bloque -> O(1) (por implementación de la lista optimizada)
        return ult.obtenerMaximaTransaccion(); // Se devuelve la transacción de mayor valor del heap del bloque -> O(1)
    } // Total: O(1)

    public Transaccion[] txUltimoBloque() { 
        
        Bloque ult = bloques.obtener(bloques.longitud() - 1); // Se accede al último bloque -> O(1)
        int n = ult.numeroTransacciones();
        Transaccion[] arr = new Transaccion[n];
        for (int i = 0; i < n; i++) { // Se recorre y copia cada transacción del bloque -> O(nb)
            arr[i] = ult.getTransacciones().obtener(i);
        }
        return arr;
    } // Total: O(nb)

    public int maximoTenedor() { 
        Usuario top = usuarios.obtenerMaximo();// Se accede al máximo del heap de usuarios -> O(1)
        return top.id; 
    } // Total: O(1)

    public int montoMedioUltimoBloque() { 
        Bloque ultimo = bloques.obtener(bloques.longitud() - 1); // Se accede al último bloque -> O(1)
        return ultimo.promedioMontos(); // Se devuelve sumaMontos/cantidadTransaccionesValidas -> Ambas variables se mantienen actualizadas al agregar/quitar transacciones -> no hay recorrido, O(1)
    } // Total: O(1)

    public void hackearTx() {         
        if (bloques.longitud() == 0) return;

        Bloque ult = bloques.obtener(bloques.longitud() - 1); // Se accede al último bloque -> O(1)
        if (ult.numeroTransacciones() == 0) return;

        Transaccion t = ult.extraerMaximaTransaccion(); // Se extrae la transacción de mayor monto del heap del bloque -> O(log nb)

        ult.restarMonto(t); // Sacamos el monto de la transaccion hackeada del atributo sumaMontos de la clase Bloque

        if (t.id_comprador() != 0) { // Revierto los saldos
            Handle<Usuario> hV = handles.get(t.id_vendedor() - 1); 
            Usuario v = hV.getElemento();
            v.saldo -= t.monto(); 
            usuarios.actualizar(hV); // Se actualizan usuarios en el heap -> O(log P)

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
        ult.eliminarTransaccionPorId(t.id()); // Se elimina la transacción de la lista enlazada por handle -> O(1)
    } // Total: O(log nb + log P)
}







