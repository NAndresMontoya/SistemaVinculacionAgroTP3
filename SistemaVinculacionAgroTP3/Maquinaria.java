public class Maquinaria {
    private int id;
    private String tipo;
    private String marca;
    private String modelo;

    public Maquinaria(int id, String tipo, String marca, String modelo) {
        this.id = id;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    @Override
    public String toString() {
        return tipo + " - " + marca + " " + modelo;
    }
}