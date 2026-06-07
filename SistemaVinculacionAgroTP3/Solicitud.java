public class Solicitud {
    private int id;
    private Productor productor;
    private Maquinaria maquinaria;
    private String descripcionProblema;
    private String ubicacion;
    private String urgencia;
    private String estado;
    private Mecanico mecanicoAsignado;

    public Solicitud(int id, Productor productor, Maquinaria maquinaria, String descripcionProblema, String ubicacion, String urgencia) {
        this.id = id;
        this.productor = productor;
        this.maquinaria = maquinaria;
        this.descripcionProblema = descripcionProblema;
        this.ubicacion = ubicacion;
        this.urgencia = urgencia;
        this.estado = "Pendiente";
    }

    public int getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    public Productor getProductor() {
        return productor;
    }

    public Maquinaria getMaquinaria() {
        return maquinaria;
    }

    public void aceptarSolicitud(Mecanico mecanico) {
        this.mecanicoAsignado = mecanico;
        this.estado = "Aceptada";
    }

    @Override
    public String toString() {
    return "ID: " + id + "\n" +
            "Productor: " + productor.getNombre() + "\n" +
            "Maquinaria: " + maquinaria + "\n" +
            "Problema: " + descripcionProblema + "\n" +
            "Ubicación: " + ubicacion + "\n" +
            "Urgencia: " + urgencia + "\n" +
            "Estado: " + estado + "\n" +
            "----------------------------------------";
}
}