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
            if (compare == 0){
                return;
            }
            padre = actual;
            if (compare > 0){
                actual = actual.der;
            }
            else{
                actual = actual.izq;
            }
        }
        Nodo nuevo = new Nodo(elem);
        nuevo.padre = padre;
        if (elem.compareTo(padre.v) > 0){
            padre.der = nuevo;
        }
        else{
            padre.izq = nuevo;
        }
        _cardinal++;
    }

    public boolean pertenece(T elem){
        Nodo actual = _raiz;
        while (actual != null){
            int compare = elem.compareTo(actual.v);
            if (compare == 0){
                return true;
            }
            if (compare > 0){
                actual = actual.der;
            }
            else{
                actual = actual.izq;
            }
        }
        return false;
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
            if (compare == 0) {
                return actual;
            }
            if (compare < 0) {
                actual = actual.izq;
            }
            else actual = actual.der;
        }
        return null;
    }

    private void reemplazar(Nodo viejo, Nodo nuevo) {

        if (viejo.padre == null) {
            _raiz = nuevo;
        } else if (viejo == viejo.padre.izq) {
            viejo.padre.izq = nuevo;
        } else {
            viejo.padre.der = nuevo;
        }
        if (nuevo != null) {
            nuevo.padre = viejo.padre;
        }
    }

     /*         10       | Con este ejemplo, si hago reemplazar(15, 12) entra en el else porque 15 está a la derecha del
               /  \      | padre. Lo cambio por el valor de nuevo y luego le asigno su padre
              5    15    |
                   /     |
                  12     |
     */

    public void eliminar(T elem){
        ;
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
