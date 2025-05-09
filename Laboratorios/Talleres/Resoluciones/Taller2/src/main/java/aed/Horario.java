package aed;

public class Horario {
    private int hora;
    private int minutos;

    public Horario(int hora, int minutos) {
        this.hora = hora;
        this.minutos = minutos;
    }

    public int hora() {
        return hora;
    }

    public int minutos() {
        return minutos;
    }

    @Override
    public String toString() {
        if (String.valueOf(minutos).length() == 1){
            return String.valueOf(hora) + ":0" + String.valueOf(minutos);
        }
        else{
            return String.valueOf(hora) + ":" + String.valueOf(minutos);
        }
    }

    @Override
    public boolean equals(Object otro) {
        if (otro == null || otro.getClass() != this.getClass()) {
            return false;
        }
        
        Horario otroHorario = (Horario) otro;

        return hora == otroHorario.hora && minutos == otroHorario.minutos; 
    }

}
