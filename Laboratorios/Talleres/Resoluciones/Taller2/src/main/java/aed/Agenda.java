package aed;

public class Agenda {
    private Fecha fecha;
    private ArregloRedimensionableDeRecordatorios recordatorios;

    public Agenda(Fecha fechaActual) { // creo copias para ambos
        this.fecha = new Fecha(fechaActual); 
        this.recordatorios = new ArregloRedimensionableDeRecordatorios();
    }

    public void agregarRecordatorio(Recordatorio recordatorio) {
        recordatorios.agregarAtras(recordatorio);
    }

    @Override
    public String toString() {
        String agendaR = fecha.dia() + "/" + fecha.mes();
        agendaR += "\n=====\n";
        for (int i = 0; i < this.recordatorios.longitud(); i++){
            Recordatorio rec = recordatorios.obtener(i);
            if (rec.fecha().dia() == fecha.dia() && rec.fecha().mes() == fecha.mes()) { // los filtro para que sean los recordatorios del mismo día
                agendaR += rec.mensaje() + " @ " + rec.fecha().dia() + "/" + rec.fecha().mes() + " " + rec.horario().hora() + ":" + rec.horario().minutos() + "\n";
            }
        }
        return agendaR;
    }

    public void incrementarDia() {
        this.fecha.incrementarDia();
    }

    public Fecha fechaActual() {
        return new Fecha(this.fecha);
    }

}
