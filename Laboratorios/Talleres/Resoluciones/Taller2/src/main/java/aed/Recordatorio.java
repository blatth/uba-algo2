package aed;

public class Recordatorio {
    private String mensaje;
    private Fecha fecha;
    private Horario horario;

    public Recordatorio(String mensaje, Fecha fecha, Horario horario) {
        this.mensaje = mensaje;
        this.fecha = new Fecha(fecha.dia(), fecha.mes());
        this.horario = new Horario(horario.hora(), horario.minutos());
    }

    public Horario horario() {
        return horario;
    }

    public Fecha fecha() {
        return new Fecha(this.fecha.dia(), this.fecha.mes());
    }

    public String mensaje() {
        return mensaje;
    }

    @Override
    public String toString() {
        return mensaje + " @ " + String.valueOf(fecha) + " " + String.valueOf(horario);
    }

    @Override
    public boolean equals(Object otro) {
        if (otro == null || otro.getClass() != this.getClass()) {
            return false;
        }
        Recordatorio otroRecordatorio = (Recordatorio) otro;

        return this.mensaje.equals(otroRecordatorio.mensaje) && this.fecha.equals(otroRecordatorio.fecha) &&  this.horario.equals(otroRecordatorio.horario);
    }
}
