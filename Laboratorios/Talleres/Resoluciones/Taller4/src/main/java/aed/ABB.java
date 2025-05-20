package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el metodo compareTo()
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

    public T maximo() {
        if (_raiz == null) throw new NoSuchElementException();
        Nodo actual = _raiz;
        while (actual.der != null){
            actual = actual.der;
        }
        return actual.v;
    }

    public void insertar(T elem) {
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

    public boolean pertenece(T elem) {
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

    public void eliminar(T elem) {
        Nodo nodo = buscarNodo(elem);

        if (nodo == null){
            return;
        }
        if (nodo.izq == null && nodo.der == null){ // caso 1: sin hijos
            reemplazar(nodo, null);
        }
        else if (nodo.izq == null){ // caso 2a: solo hijo derecho
            reemplazar(nodo, nodo.der);
        }
        else if (nodo.der == null){ // caso 2b: solo hijo izquierdo
            reemplazar(nodo, nodo.izq);
        }
        else{ // caso 3: dos hijos
            Nodo sucesor = nodoMin(nodo.der);
            nodo.v = sucesor.v;

            if (sucesor.der != null){ // elimino el sucesor, que seguro no tiene hijo izquierdo
                reemplazar(sucesor, sucesor.der);
            }
            else{
                reemplazar(sucesor, null);
            }
        }
        _cardinal--;
    }


    // AUXS PARA ELIMINAR

    private Nodo buscarNodo(T elem) {
        Nodo actual = _raiz;
        while (actual != null){
            int compare = elem.compareTo(actual.v);
            if (compare == 0){
                return actual;
            }
            if (compare < 0){
                actual = actual.izq;
            }
            else actual = actual.der;
        }
        return null;
    }

    /* rec compareTo: sean elem1 y elem2 dos instancias de un mismo tipo de datos comparable,
    luego elem1.compareTo(elem2) devuelve un entero:
    1) > 0 si elem1 > elem2,
    2) < 0 si elem1 < elem2
    3) = 0 si elem1 = elem2 */

    private void reemplazar(Nodo viejo, Nodo nuevo) {

        if (viejo.padre == null){
            _raiz = nuevo;
        } else if (viejo == viejo.padre.izq){
            viejo.padre.izq = nuevo;
        } else {
            viejo.padre.der = nuevo;
        }
        if (nuevo != null){
            nuevo.padre = viejo.padre;
        }
    }

     /*         10       | Con este ejemplo, si hago reemplazar(15, 12) entra en el else porque 15 está a la derecha del
               /  \      | padre. Lo cambio por el valor de nuevo y luego le asigno su padre
              5    15    |
                   /     |
                  12     |
     */

    private Nodo nodoMin(Nodo elem) {
        if (elem == null){
            return null;
        }
        while (elem.izq != null){
            elem = elem.izq;
        }
        return elem;
    }

    /* Devuelve el nodo con el val más pequeño bajando por la rama izquierda hasta llegar al nodo que no tenga hijo
       a su izquierda. Me va a servir para buscar el mínimo de la rama derecha de hijos de un nodo. */

    public String toString() {
        Iterador<T> iterador = iterador();
        String res = "{";

        if (iterador.haySiguiente()){
            res += iterador.siguiente();  // solo llamo si hay algún elemento
            while (iterador.haySiguiente()){
                res += "," + iterador.siguiente();
            }
        }

        res += "}";
        return res;
    }



    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        public ABB_Iterador() {
            if (_raiz != null){
                _actual = nodoMin(_raiz);
            }
            else {
                _actual = null;
            }
        }

        public boolean haySiguiente() {
            return _actual != null;
        }

        public T siguiente() {
            if (_actual == null) throw new NoSuchElementException();
            T valor = _actual.v;

            if (_actual.der != null){ // caso 1: tiene subárbol derecho => va al mínimo de esa rama
                _actual = nodoMin(_actual.der);
            }
            else { // caso 2: no tiene hijo derecho => sube hasta venir de la izquierda
                Nodo padre = _actual.padre;
                while (padre != null && _actual == padre.der){
                    _actual = padre;
                    padre = padre.padre;
                }
                _actual = padre;
            }

            return valor;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }



}

