public class Productor extends Usuario {

    public Productor(int id, String nombre, String telefono, String email) {
        super(id, nombre, telefono, email);
    }

    @Override
    public String obtenerTipoUsuario() {
        return "Productor";
    }
}