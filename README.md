

# ADMINISTRADOR DE MEMORIA

Este programa simula la asignación de memoria en un sistema operativo usando el algoritmo Primer Ajuste sobre un esquema de partición dinámica. Permite al usuario ingresar procesos con un tamaño específico, los cuales son asignados al primer bloque de memoria libre lo suficientemente grande para albergarlos.

La interfaz gráfica muestra el estado actual de la memoria, incluyendo:

-   Identificador y tamaño de cada bloque.
    
-   Estado (libre u ocupado).
    
-   Proceso asignado.
    
-   Fragmentación interna por bloque.
    
-   Memoria restante total.

### Datos que debe tener cada nodo (vehículo):

1.  **Placa** → Ej. "ABC123" (clave única)
    
2.  **Color** → Ej. "Rojo"
    
3.  **Línea** → Ej. "Hilux", "Yaris", "Aveo" (submodelo)
    
4.  **Modelo** → Ej. "2020"
    
5.  **Propietario** → Ej. "Juan Pérez"

# DiscoDuro.java

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


Esta clase simula un sistema de almacenamiento de datos en una matriz de 10x10, donde cada celda (o nodo) puede almacenar un objeto Vehículo. Representa un espacio de almacenamiento como un "disco duro lógico" que permite insertar, buscar, eliminar y visualizar vehículos en nodos interconectados.

# InterfazAlmacenamiento.java

    import javax.swing.*;
    import javax.swing.table.DefaultTableCellRenderer;
    import javax.swing.table.DefaultTableModel;
    import java.awt.*;
    import java.awt.event.*;
    
    public class InterfazAlmacenamiento extends JFrame {
        private JTextField placaField, colorField, lineaField, modeloField, propietarioField, busquedaField;
        private JTable almacenamientoTable;
        private JTextArea mensajeArea;
        private final DiscoDuro disco;
    
        public InterfazAlmacenamiento() {
            disco = new DiscoDuro();
            disco.cargarDatos();
            inicializarComponentes();
        }
    
        private void inicializarComponentes() {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                SwingUtilities.updateComponentTreeUI(this);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                System.out.println("No se pudo aplicar Nimbus Look and Feel: " + e.getMessage());
            }
    
            setTitle("Sistema de Almacenamiento");
            setSize(900, 700);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            getContentPane().setBackground(new Color(240, 248, 255));
    
            Font fuenteGrande = new Font("Arial", Font.PLAIN, 16);
    
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15, 15, 15, 15);
            gbc.anchor = GridBagConstraints.WEST;
    
            // Etiquetas y campos de texto
            gbc.gridx = 0;
            gbc.gridy = 0;
            JLabel placaLabel = new JLabel("Placa:");
            placaLabel.setFont(fuenteGrande);
            panel.add(placaLabel, gbc);
    
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            placaField = new JTextField(20);
            placaField.setFont(fuenteGrande);
            panel.add(placaField, gbc);
    
            gbc.gridx = 0;
            gbc.gridy = 1;
            JLabel colorLabel = new JLabel("Color:");
            colorLabel.setFont(fuenteGrande);
            panel.add(colorLabel, gbc);
    
            gbc.gridx = 1;
            gbc.gridy = 1;
            colorField = new JTextField(20);
            colorField.setFont(fuenteGrande);
            panel.add(colorField, gbc);
    
            gbc.gridx = 0;
            gbc.gridy = 2;
            JLabel lineaLabel = new JLabel("Línea:");
            lineaLabel.setFont(fuenteGrande);
            panel.add(lineaLabel, gbc);
    
            gbc.gridx = 1;
            gbc.gridy = 2;
            lineaField = new JTextField(20);
            lineaField.setFont(fuenteGrande);
            panel.add(lineaField, gbc);
    
            gbc.gridx = 0;
            gbc.gridy = 3;
            JLabel modeloLabel = new JLabel("Modelo:");
            modeloLabel.setFont(fuenteGrande);
            panel.add(modeloLabel, gbc);
    
            gbc.gridx = 1;
            gbc.gridy = 3;
            modeloField = new JTextField(20);
            modeloField.setFont(fuenteGrande);
            panel.add(modeloField, gbc);
    
            gbc.gridx = 0;
            gbc.gridy = 4;
            JLabel propietarioLabel = new JLabel("Propietario:");
            propietarioLabel.setFont(fuenteGrande);
            panel.add(propietarioLabel, gbc);
    
            gbc.gridx = 1;
            gbc.gridy = 4;
            propietarioField = new JTextField(20);
            propietarioField.setFont(fuenteGrande);
            panel.add(propietarioField, gbc);
    
            // Botones
            JButton insertarButton = new JButton("Insertar");
            JButton buscarButton = new JButton("Buscar");
            JButton eliminarButton = new JButton("Eliminar");
            JButton verAlmacenamientoButton = new JButton("Ver Almacenamiento");
            insertarButton.setBackground(new Color(46, 204, 113));
            buscarButton.setBackground(new Color(52, 152, 219));
            eliminarButton.setBackground(new Color(231, 76, 60));
            verAlmacenamientoButton.setBackground(new Color(255, 193, 7));
            insertarButton.setForeground(Color.WHITE);
            buscarButton.setForeground(Color.WHITE);
            eliminarButton.setForeground(Color.WHITE);
            verAlmacenamientoButton.setForeground(Color.BLACK);
            insertarButton.setFont(fuenteGrande);
            buscarButton.setFont(fuenteGrande);
            eliminarButton.setFont(fuenteGrande);
            verAlmacenamientoButton.setFont(fuenteGrande);
            insertarButton.setFocusPainted(false);
            buscarButton.setFocusPainted(false);
            eliminarButton.setFocusPainted(false);
            verAlmacenamientoButton.setFocusPainted(false);
    
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            panel.add(insertarButton, gbc);
    
            gbc.gridx = 1;
            panel.add(buscarButton, gbc);
    
            gbc.gridx = 2;
            panel.add(eliminarButton, gbc);
    
            gbc.gridx = 3;
            panel.add(verAlmacenamientoButton, gbc);
    
            // Tabla para el almacenamiento
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.gridwidth = 4;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;
            gbc.weighty = 0.7;
            String[] columnNames = new String[10];
            for (int i = 0; i < 10; i++) {
                columnNames[i] = String.valueOf(i);
            }
            String[][] data = new String[10][10];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    data[i][j] = "";
                }
            }
            almacenamientoTable = new JTable(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            almacenamientoTable.setFont(fuenteGrande);
            almacenamientoTable.setRowHeight(30);
            almacenamientoTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
            JScrollPane tableScrollPane = new JScrollPane(almacenamientoTable);
            panel.add(tableScrollPane, gbc);
    
            // Área de mensajes
            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.gridwidth = 4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weighty = 0.3;
            mensajeArea = new JTextArea(3, 40);
            mensajeArea.setFont(fuenteGrande);
            mensajeArea.setEditable(false);
            JScrollPane mensajeScrollPane = new JScrollPane(mensajeArea);
            panel.add(mensajeScrollPane, gbc);
    
            // Campo de búsqueda
            gbc.gridx = 0;
            gbc.gridy = 8;
            gbc.gridwidth = 1;
            gbc.weighty = 0;
            JLabel busquedaLabel = new JLabel("Buscar (criterio):");
            busquedaLabel.setFont(fuenteGrande);
            panel.add(busquedaLabel, gbc);
    
            gbc.gridx = 1;
            gbc.gridwidth = 3;
            busquedaField = new JTextField(25);
            busquedaField.setFont(fuenteGrande);
            panel.add(busquedaField, gbc);
    
            insertarButton.addActionListener(e -> {
                Vehiculo vehiculo = new Vehiculo(placaField.getText(), colorField.getText(), lineaField.getText(),
                                               modeloField.getText(), propietarioField.getText());
                boolean insertado = disco.insertar(vehiculo);
                if (insertado) {
                    actualizarTabla();
                    mensajeArea.setText("Datos insertados en: " + buscarPrimeraPosicionLibre() + "\n" + vehiculo.toString());
                    limpiarCampos();
                } else {
                    mensajeArea.setText("Error: Placa repetida o no hay espacio, inserción rechazada");
                }
            });
    
            buscarButton.addActionListener(e -> {
                String criterio = busquedaField.getText();
                String resultado = disco.buscar(criterio);
                mensajeArea.setText(resultado);
            });
    
            eliminarButton.addActionListener(e -> {
                String criterio = busquedaField.getText();
                boolean eliminado = disco.eliminar(criterio);
                actualizarTabla();
                mensajeArea.setText(eliminado ? "Datos eliminados" : "No se encontraron datos");
            });
    
            verAlmacenamientoButton.addActionListener(e -> {
                actualizarTabla();
            });
    
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    disco.guardarDatos();
                    System.exit(0);
                }
            });
    
            add(panel);
        }
    
        private void limpiarCampos() {
            placaField.setText("");
            colorField.setText("");
            lineaField.setText("");
            modeloField.setText("");
            propietarioField.setText("");
        }
    
        private void actualizarTabla() {
            String[][] matrizVisual = new String[10][10];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    Nodo nodo = disco.matriz[i][j];
                    matrizVisual[i][j] = nodo.isOcupado() ? nodo.getId() + ": " + nodo.getVehiculo().getPlaca() : nodo.getId() + ": Vacío";
                }
            }
            almacenamientoTable.setModel(new DefaultTableModel(matrizVisual, new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
        }
    
        private String buscarPrimeraPosicionLibre() {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (!disco.matriz[i][j].isOcupado()) {
                        return "Nodo_" + i + "_" + j;
                    }
                }
            }
            return "No hay espacio";
        }
    
        private class CustomTableCellRenderer extends DefaultTableCellRenderer {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                          boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Nodo nodo = disco.matriz[row][column];
                if (nodo.isOcupado()) {
                    c.setBackground(new Color(255, 99, 71));
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(new Color(144, 238, 144));
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        }
    
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                InterfazAlmacenamiento interfaz = new InterfazAlmacenamiento();
                interfaz.setVisible(true);
            });
        }
    }

Esta clase implementa una interfaz gráfica en Java Swing para gestionar el almacenamiento de vehículos en una estructura tipo matriz de 10x10, usando una clase auxiliar DiscoDuro (con nodos que contienen datos de un objeto Vehículo).

# Nodo.java

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

Esta clase cumple adecuadamente con su función como elemento de una matriz 10x10 que almacena objetos de tipo Vehículo, permitiendo además referencias a nodos adyacentes (arriba, abajo, izquierda, derecha), lo cual es útil para futuras funcionalidades como recorridos o conexiones lógicas.

# Vehiculo.java

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

Representa un vehículo con atributos básicos de identificación y características. Esta clase encapsula los datos de un vehículo individual, como su placa, color, línea, modelo y # Almacenamiento.java

    

Contiene el método `main()` del proyecto. Actualmente actúa como punto de entrada del programa, aunque en su estado actual solo imprime "Hello World!". Esta clase puede ser extendida para inicializar estructuras de almacenamiento, gestionar vehículos o conectar con una interfaz gráfica en el futuro.
