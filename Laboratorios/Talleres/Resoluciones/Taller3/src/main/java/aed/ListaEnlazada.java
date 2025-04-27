package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> { // esto sería como declarar los obs de un TAD
    private int size;
    private Nodo primero;
    private Nodo ultimo; 

    private class Nodo { // el Nodo se compotaría como un struct, donde es una tupla con renombe de tipos. guardo el valr actual y el nodo siguiente y el anterior
        T valor;
        Nodo sig;
        Nodo ant;

        Nodo(T v) {valor = v;}
    }

    public ListaEnlazada() { // creación
        this.size = 0;
        this.primero = new Nodo(null);
        this.ultimo = new Nodo(null);
    }

    public int longitud() {
        return this.size;
    }

    public void agregarAdelante(T elem) { // agreo a la izquierda
        Nodo nuevoNodo = new Nodo(elem);
        if (this.size == 0){ // les doy el mismo valor a ambos porque aún no existían
            this.primero = nuevoNodo;
            this.ultimo = nuevoNodo;
        }
        else{
            this.primero.ant = nuevoNodo; // el valor anterior tendrá valor nuevoNodo
            nuevoNodo.sig = this.primero; // 
            this.primero = nuevoNodo; 
        }

        this.size = size + 1;
    }

    public void agregarAtras(T elem) { // agreo a la derecha
        Nodo nuevoNodo = new Nodo(elem);
        if (this.size == 0){ // les doy el mismo valor a ambos porque aún no existían
            this.primero = nuevoNodo;
            this.ultimo = nuevoNodo;
        }
        else{
            this.ultimo.sig = nuevoNodo; // agrego un nuevo nodo al final
            nuevoNodo.ant = this.ultimo; // el anterior toma el valor que tenía en el último
            nuevoNodo.sig = new Nodo(null); // agrego el último nodo
            this.ultimo = nuevoNodo;
        }

        this.size = size + 1;
    }

    public T obtener(int i) {
        Nodo nodoActual = primero;
        for (int n = 0; n < i; n++){
            nodoActual = nodoActual.sig;
        }

        return nodoActual.valor; 
    }

    public void eliminar(int i) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public void modificarPosicion(int indice, T elem) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        throw new UnsupportedOperationException("No implementada aun");
    }
    
    @Override
    public String toString() {
        throw new UnsupportedOperationException("No implementada aun");
    }

    private class ListaIterador implements Iterador<T> {
    	int dedo;
        ListaEnlazada<T> nuevaL;

        public boolean haySiguiente() {
	        return dedo != nuevaL.size;
        }
        
        public boolean hayAnterior() {
	        return dedo != 0; // porque el size comienza en 0. si el dedo = 0 entonces no hay anterior
        }

        public T siguiente() {
	        dedo++;
            return nuevaL.obtener(dedo-1);
        }
        

        public T anterior() {
	        dedo--;
            return nuevaL.obtener(dedo);
        }
    }

    public Iterador<T> iterador() {
	    throw new UnsupportedOperationException("No implementada aun");
    }

}
