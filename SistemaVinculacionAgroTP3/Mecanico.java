public class Mecanico extends Usuario {
    private String especialidad;

    public Mecanico(int id, String nombre, String telefono, String email, String especialidad) {
        super(id, nombre, telefono, email);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    @Override
    public String obtenerTipoUsuario() {
        return "Mecánico especializado en " + especialidad;
    }
}