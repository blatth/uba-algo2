public class Handle<T extends Comparable<T>> {
    public int posicion;
    public T elemento;

    public Handle(T elem, int pos) {
        this.elemento = elem;
        this.posicion = pos;
    }
}
