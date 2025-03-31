package aed;

class ArregloRedimensionableDeRecordatorios {
    private Recordatorio[] recordatorios;
    private int cantidad;

    public ArregloRedimensionableDeRecordatorios() {
        this.recordatorios = new Recordatorio[1];
        this.cantidad = 0;
    }

    public int longitud() {
        return cantidad;
    }

    public void agregarAtras(Recordatorio i) {
        if (cantidad == recordatorios.length){ // si cantidad = recoradatorios.length es porque el arreglo está lleno y lo expando
            int cantidadUpd = recordatorios.length * 2; // según leí, es mejor duplicar el tamaño que aumentarlo de a 1 porque es más eficiente
            Recordatorio[] arrayNuevo = new Recordatorio[cantidadUpd]; // arreglo nuevo con capacidad updateada
            for (int h = 0; h < cantidad; h++){ // copio los elementos del viejo al nuevo uno a uno
                arrayNuevo[h] = recordatorios[h];
            }
            recordatorios = arrayNuevo;
        }
        recordatorios[cantidad] = i;
        cantidad++;
    }

    public Recordatorio obtener(int i) {
        return this.recordatorios[i];
    }

    public void quitarAtras() {
        if (cantidad > 0){
            cantidad--; 
        }
    }

    public void modificarPosicion(int indice, Recordatorio valor) {
        if (indice > 0 && indice < cantidad){
            recordatorios[indice] = valor; // reemplazo el elem en pos i con el valor
        }
    }

    public ArregloRedimensionableDeRecordatorios(ArregloRedimensionableDeRecordatorios vector) {
        this.cantidad = vector.cantidad; // copio la cantidad de elementos
        this.recordatorios = new Recordatorio[vector.recordatorios.length]; // creo un nuevo array con el mismo tamaño
        for (int h = 0; h < cantidad; h++){ // copio los elementos del viejo al nuevo uno a uno (c/p del otro ej)
            this.recordatorios[h] = vector.recordatorios[h];
        }
    }

    public ArregloRedimensionableDeRecordatorios copiar() {
        return new ArregloRedimensionableDeRecordatorios(this); // solamente llamo a lo que definí antes, lo hago con this porque esto representa la intancia actual
    }
}
