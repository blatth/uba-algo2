package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2

public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private Nodo _raiz;
    private int _cardinal;

    private class Nodo {
        T v;
        Nodo izq;
        Nodo der;
        Nodo padre;

        Nodo (T valor){
            v = valor;
            izq = null;
            der = null;
            padre = null;
        }
    }

    public ABB() {
        _raiz = null;
        _cardinal = 0;
    }

    public int cardinal() {
        return _cardinal;
    }

    public T minimo(){
        if (_raiz == null) throw new NoSuchElementException();
        Nodo actual = _raiz;
        while (actual.izq != null){
            actual = actual.izq;
        }
        return actual.v;
    }

    public T maximo(){
        if (_raiz == null) throw new NoSuchElementException();
        Nodo actual = _raiz;
        while (actual.der != null){
            actual = actual.der;
        }
        return actual.v;
    }

    public void insertar(T elem){
        if (_raiz == null){
            _raiz = new Nodo(elem);
            _cardinal++;
            return;
        }
        Nodo actual = _raiz;
        Nodo padre = null;
        while (actual != null){
            int compare = elem.compareTo(actual.v);
            if (compare < 0){
                padre = actual;
                actual = actual.izq;
            }
            if (compare == 0){
                return;
            }
        }
    }

    public boolean pertenece(T elem){
        throw new UnsupportedOperationException("No implementada aun");
    }

        /* rec compareTo: sean elem1 y elem2 dos instancias de un mismo tipo de datos comparable,
        luego elem1.compareTo(elem2) devuelve un entero:
        1) > 0 si elem1 > elem2,
        2) < 0 si elem1 < elem2
        3) = 0 si elem1 = elem2 */

    private Nodo buscarNodo(T elem){
        Nodo actual = _raiz;
        while (actual != null){
            int compare = elem.compareTo(actual.v);
            if (compare == 0) return actual;
            if (compare < 0) actual = actual.izq;
            else actual = actual.der;
        }
        return null;
    }

    public void eliminar(T elem){
        throw new UnsupportedOperationException("No implementada aun");
    }

    public String toString(){
        throw new UnsupportedOperationException("No implementada aun");
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        public boolean haySiguiente() {            
            throw new UnsupportedOperationException("No implementada aun");
        }
    
        public T siguiente() {
            throw new UnsupportedOperationException("No implementada aun");
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
