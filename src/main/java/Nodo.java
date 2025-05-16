public class Nodo {
    private String id;
    private Vehiculo vehiculo;
    private Nodo arriba;
    private Nodo abajo;
    private Nodo izquierda;
    private Nodo derecha;

    public Nodo(int i, int j, Vehiculo vehiculo) {
        this.id = "Nodo_" + i + "_" + j;
        this.vehiculo = vehiculo;
        this.arriba = null;
        this.abajo = null;
        this.izquierda = null;
        this.derecha = null;
    }

    public String getId() { return id; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
    public Nodo getArriba() { return arriba; }
    public void setArriba(Nodo arriba) { this.arriba = arriba; }
    public Nodo getAbajo() { return abajo; }
    public void setAbajo(Nodo abajo) { this.abajo = abajo; }
    public Nodo getIzquierda() { return izquierda; }
    public void setIzquierda(Nodo izquierda) { this.izquierda = izquierda; }
    public Nodo getDerecha() { return derecha; }
    public void setDerecha(Nodo derecha) { this.derecha = derecha; }

    public boolean isOcupado() { return vehiculo != null; }
}