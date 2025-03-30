package aed;

public class Fecha {
    private int dia;
    private int mes;

    public Fecha(int dia, int mes) {  
        this.dia = dia;
        this.mes = mes;
    }

    public Fecha(Fecha fecha) {
        this.dia = fecha.dia;
        this.mes = fecha.mes;
    }

    public Integer dia() {
        return dia;
    }

    public Integer mes() {
        return mes;
    }

    public String toString() { 
        return String.valueOf(dia) + "/" + String.valueOf(mes); // también se puede hacer directamente return dia + "/" + mes porque pasa los tests
    }

    @Override
    public boolean equals(Object otra) {
        if (otra == null || otra.getClass() != this.getClass()) {
            return false;
        }
        
        Fecha otraFecha = (Fecha) otra; // a otra le asigno el tipo de la clase (Fecha) que venía usando

        return dia == otraFecha.dia && mes == otraFecha.mes; // chequea que la fecha (tanto día como mes) sean iguales si se le pasa un parámetro igual al anterior
    }

    public void incrementarDia() {
        if (this.dia == diasEnMes(this.mes)){ // chequea que el día que da el user sea != diasEnMes(mes-1)
            this.dia = 1; // si lo es, lo convierte a 1, el primer día del mes
            if (this.mes == 12){ // lo mismo que antes
                this.mes = 1;
            }
            else{ // si no son los casos límite, simplemente suma 1 al día o mes
                this.mes = this.mes + 1;
            }
        }
        else{
            this.dia = this.dia + 1;
        }
    }

    private int diasEnMes(int mes) {
        int dias[] = {
                // ene, feb, mar, abr, may, jun
                31, 28, 31, 30, 31, 30,
                // jul, ago, sep, oct, nov, dic
                31, 31, 30, 31, 30, 31
        };
        return dias[mes - 1];
    }

}
