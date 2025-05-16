import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class DiscoDuro {
    Nodo[][] matriz;
    private int nodosOcupados;
    private List<int[]> posiciones;

    public DiscoDuro() {
        matriz = new Nodo[10][10];
        nodosOcupados = 0;
        inicializarMatriz();
    }

    private void inicializarMatriz() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                matriz[i][j] = new Nodo(i, j, null);
                conectarNodos(i, j);
            }
        }
    }

    private void conectarNodos(int i, int j) {
        Nodo nodoActual = matriz[i][j];
        if (i > 0) nodoActual.setArriba(matriz[i-1][j]);
        if (i < 9) nodoActual.setAbajo(matriz[i+1][j]);
        if (j > 0) nodoActual.setIzquierda(matriz[i][j-1]);
        if (j < 9) nodoActual.setDerecha(matriz[i][j+1]);
    }

    public boolean placaExiste(String placa) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Nodo nodo = matriz[i][j];
                Vehiculo v = nodo.getVehiculo();
                if (v != null && v.getPlaca().equalsIgnoreCase(placa)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean insertar(Vehiculo vehiculo) {
        if (nodosOcupados >= 100) return false;
        if (placaExiste(vehiculo.getPlaca())) return false;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!matriz[i][j].isOcupado()) {
                    matriz[i][j].setVehiculo(vehiculo);
                    nodosOcupados++;
                    return true;
                }
            }
        }
        return false;
    }

    public String buscar(String criterio) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Nodo nodo = matriz[i][j];
                Vehiculo v = nodo.getVehiculo();
                if (v != null && (v.getPlaca().equalsIgnoreCase(criterio) ||
                                  v.getColor().equalsIgnoreCase(criterio) ||
                                  v.getLinea().equalsIgnoreCase(criterio) ||
                                  v.getModelo().equalsIgnoreCase(criterio) ||
                                  v.getPropietario().equalsIgnoreCase(criterio))) {
                    resultado.append(nodo.getId() + ": " + v.toString()).append("\n");
                }
            }
        }
        return resultado.isEmpty() ? "No se encontró el vehículo" : resultado.toString();
    }

    public boolean eliminar(String criterio) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Nodo nodo = matriz[i][j];
                Vehiculo v = nodo.getVehiculo();
                if (v != null && (v.getPlaca().equalsIgnoreCase(criterio) ||
                                  v.getColor().equalsIgnoreCase(criterio) ||
                                  v.getLinea().equalsIgnoreCase(criterio) ||
                                  v.getModelo().equalsIgnoreCase(criterio) ||
                                  v.getPropietario().equalsIgnoreCase(criterio))) {
                    nodo.setVehiculo(null);
                    nodosOcupados--;
                    return true;
                }
            }
        }
        return false;
    }

    public String mostrarMatriz() {
        StringBuilder matrizTexto = new StringBuilder();
        matrizTexto.append("Estado del Almacenamiento (10x10):\n");
        matrizTexto.append("ID del Nodo: Placa (si ocupada) o Vacío\n");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Nodo nodo = matriz[i][j];
                String valor = nodo.isOcupado() ? nodo.getId() + ": " + nodo.getVehiculo().getPlaca() : nodo.getId() + ": Vacío";
                matrizTexto.append(String.format("%-20s", valor)).append(" ");
            }
            matrizTexto.append("\n");
        }
        matrizTexto.append("\nNodos ocupados: ").append(nodosOcupados);
        matrizTexto.append("\nNodos disponibles: ").append(100 - nodosOcupados);
        return matrizTexto.toString();
    }

    public void guardarDatos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("almacenamiento.txt"))) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    Nodo nodo = matriz[i][j];
                    Vehiculo v = nodo.getVehiculo();
                    if (v != null) {
                        String linea = String.format("%d|%d|%s|%s|%s|%s|%s",
                            i, j, v.getPlaca(), v.getColor(), v.getLinea(), v.getModelo(), v.getPropietario());
                        writer.write(linea);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

    public void cargarDatos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("almacenamiento.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 7) {
                    int i = Integer.parseInt(partes[0]);
                    int j = Integer.parseInt(partes[1]);
                    Vehiculo vehiculo = new Vehiculo(partes[2], partes[3], partes[4], partes[5], partes[6]);
                    matriz[i][j].setVehiculo(vehiculo);
                    nodosOcupados++;
                }
            }
        } catch (IOException e) {
            System.out.println("No se encontraron datos previos o error al cargarlos: " + e.getMessage());
        }
    }
}