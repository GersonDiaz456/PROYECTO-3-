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