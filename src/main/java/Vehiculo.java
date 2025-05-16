public class Vehiculo {
    private String placa;
    private String color;
    private String linea;
    private String modelo;
    private String propietario;

    public Vehiculo(String placa, String color, String linea, String modelo, String propietario) {
        this.placa = placa;
        this.color = color;
        this.linea = linea;
        this.modelo = modelo;
        this.propietario = propietario;
    }

    public String getPlaca() { return placa; }
    public String getColor() { return color; }
    public String getLinea() { return linea; }
    public String getModelo() { return modelo; }
    public String getPropietario() { return propietario; }

    @Override
    public String toString() {
        return "Placa: " + placa + ", Color: " + color + ", Línea: " + linea +
               ", Modelo: " + modelo + ", Propietario: " + propietario;
    }
}