public class Berretacoin {

    private ListaEnlazada<Bloque> cadBloques;
    private Handle<Usuario>[] handles; // handles
    private HeapMax<Usuario> usuarios;

    public Berretacoin(int n_usuarios) { // O(p)
        this.usuarios = new HeapMax<Usuario>(n_usuarios);
        this.handles = new ArrayList<Handle<Usuario>>(n_usuarios);
        for (int i = 0; i < n_usuarios; i++) {
            Usuario usuario = new Usuario(i, 0);
            Handle<Usuario> handle = usuarios.agregarElemento(usuario);
            handles.add(handle);
        }
        this.cadBloques = new ListaEnlazada<Bloque>();
    }

    public void agregarBloque(Transaccion[] transacciones) { // O(n_b * log(p))
        for (Transaccion t : transacciones) {
            int comprador = t.id_comprador();
            int vendedor = t.id_vendedor();
            int monto = t.monto();

            if (vendedor != 0) {
                Handle<Usuario> hV = usuarios.getHandle(vendedor);
                hV.elemento.saldo += monto;
                usuarios.actualizar(hV);
            }
            if (comprador != 0) {
                Handle<Usuario> hC = usuarios.getHandle(comprador);
                hC.elemento.saldo -= monto;
                usuarios.actualizar(hC);
            }
        }
        Bloque nuevo = new Bloque(transacciones);
        cadBloques.agregarAtras(nuevo);
    }

    public Transaccion txMayorValorUltimoBloque() { // O(1)
        Bloque ultimo = cadBloques.obtener(cadBloques.size() - 1);
        return ultimo.txMayorValor();
    }

    public Transaccion[] txUltimoBloque(){
        // a implementar
    }

    public int maximoTenedor() { // O(1)
        return usuarios.obtenerMaximo().id;
    }

    public int montoMedioUltimoBloque() { // O(1)
        Bloque ultimo = cadBloques.obtener(cadBloques.size() - 1);
        return ultimo.promedioMontos();
    }

    public void hackearTx() {
        // a implementar
    }
}