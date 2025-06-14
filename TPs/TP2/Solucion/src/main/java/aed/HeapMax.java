package aed;


public class HeapMax<T extends Comparable<T>> {
    private Handle<T>[] heap;
    private int size;


    public HeapMax(int capacidad) { // Se crea un heap vacío con capacidad dada -> O(n)
        heap = (Handle<T>[]) new Handle[capacidad];
        size = 0;
    }

    public HeapMax(Handle<T>[] elementos, int cantidad) { // Se crea un heap a partir de un array, un heapify -> O(n)
        this.heap = elementos;
        this.size = cantidad;
        for (int i = (size - 1) / 2; i >= 0; i--) {
            siftDown(i);
        }
    }

    public Handle<T> agregarElemento(T elem) { // Encolar elemento en el heap -> O(log n)
        Handle<T> h = new Handle<>(elem, size);
        this.heap[this.size] = h;
        siftUp(size);
        this.size++;
        return h;
    }

    public T obtenerMaximo() { // Consultar máximo del heap, se busca directamente en la posición cero -> O(1)
        if (this.size == 0) {
            return null;
        }
        return this.heap[0].getElemento();
    }

    public T sacarMaximo() { // Desencolar máximo -> O(log n)
        if (this.size == 0) {
            return null;
        }
        T maximo = heap[0].getElemento(); // Se accede al máximo -> O(1)
        swap(0, size - 1); // Se intercambia el máximo con la última posición -> O(1)
        this.heap[size - 1] = null; // Se borra el máximo -> O(1)
        this.size--; // Se ajusta el tamaño -> O(1)
        siftDown(0); // Se ordena la raíz -> O(log n)
        return maximo;
    }

    @Override
    public String toString() { // Se recorren los elementos para devolverlos en una cadena de texto -> O(n)
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) { // Se intera sobre la el tamaño del heap -> O(n)
            sb.append(heap[i].getElemento().toString()); // Se utiliza toString() de los elementos del heap
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public void actualizar(Handle<T> h) { // Se actualiza el heap -> O(log n)
        siftUp(h.getPosicion());
        siftDown(h.getPosicion());
    }

    private void siftUp(int i) { // Se comparan los elementos hacia arriba y se reordena el heap -> O(log n)
        while (i > 0) {
            int padre = (i - 1) / 2; // Según p(v) = 2p(u)+1 con v hijo de u
            if (heap[i].getElemento().compareTo(heap[padre].getElemento()) > 0) {
                swap(i, padre);
                i = padre;
            } else {
                break;
            }
        }
    }

    private void siftDown(int i){ // Se comparan los elementos hacia abajo y se reordena el heap -> O(log n)

        while (2*i+1<this.size) { // Se pide que exista al menos el hijo izquierdo
            int izq = 2*i+1;
            int der = 2*i+2;
            int mayor = i;

            if (heap[izq].getElemento().compareTo(heap[mayor].getElemento()) > 0) {
                mayor = izq; // Si el elemento izquierdo es mayor, se guarda
            }

            if (der < this.size && // Se comprueba si existe el hijo derecho
                    heap[der].getElemento().compareTo(heap[mayor].getElemento()) > 0) {
                mayor = der; // Si el elemento derecho es mayor, se guarda
            }

            if (mayor != i) { // Se intercambia elemento con el mayor
                swap(i, mayor);
                i = mayor;
            }
            else {
                break;
            }
        }
    }

    private void swap(int i, int j) { // Se intercambian las posiciones de dos elementos -> O(1)
        Handle<T> temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        heap[i].setPosicion(i);
        heap[j].setPosicion(j);
    }
}


