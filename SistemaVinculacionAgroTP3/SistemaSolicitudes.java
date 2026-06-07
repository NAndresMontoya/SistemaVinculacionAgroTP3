import java.util.ArrayList;

public class SistemaSolicitudes {
    private ArrayList<Solicitud> solicitudes;
    private ArrayList<Mecanico> mecanicos;
    private int contadorSolicitudes;

    public SistemaSolicitudes() {
        solicitudes = new ArrayList<>();
        mecanicos = new ArrayList<>();
        contadorSolicitudes = 1;

        mecanicos.add(new Mecanico(1, "Carlos Gómez", "3517654321", "carlos@email.com", "Cosechadoras"));
        mecanicos.add(new Mecanico(2, "Miguel Torres", "3512223333", "miguel@email.com", "Tractores"));
    }

    public Solicitud registrarSolicitud(Productor productor, Maquinaria maquinaria, String problema, String ubicacion, String urgencia) {
        Solicitud solicitud = new Solicitud(contadorSolicitudes, productor, maquinaria, problema, ubicacion, urgencia);
        solicitudes.add(solicitud);
        contadorSolicitudes++;
        return solicitud;
    }

    public ArrayList<Solicitud> listarSolicitudes() {
        return solicitudes;
    }

    public Solicitud buscarSolicitudPorId(int id) {
        for (Solicitud solicitud : solicitudes) {
            if (solicitud.getId() == id) {
                return solicitud;
            }
        }
        return null;
    }

    public boolean aceptarSolicitud(int idSolicitud) {
        Solicitud solicitud = buscarSolicitudPorId(idSolicitud);

        if (solicitud != null && solicitud.getEstado().equals("Pendiente")) {
            solicitud.aceptarSolicitud(mecanicos.get(0));
            return true;
        }

        return false;
    }
}