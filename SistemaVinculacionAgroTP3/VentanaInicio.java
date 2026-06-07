import javax.swing.*;
import java.awt.*;

public class VentanaInicio extends JFrame {

    // Sistema compartido por productor y mecánico
    private SistemaSolicitudes sistema;

    // Campos del registro inicial
    private JTextField campoNombre;
    private JTextField campoTelefono;
    private JTextField campoEmail;
    private JTextField campoEspecialidad;

    // Etiqueta que solo se muestra cuando el rol elegido es Mecánico
    private JLabel labelEspecialidad;

    // Selector de rol
    private JComboBox<String> comboRol;

    public VentanaInicio() {
    this(new SistemaSolicitudes());
    }

    public VentanaInicio(SistemaSolicitudes sistema) {
    this.sistema = sistema;

        setTitle("Inicio - Sistema de Vinculación Agro");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearPanelRegistro(), BorderLayout.CENTER);
    }

    // Encabezado visual de la ventana
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(20, 83, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("<html><center>SV Agro<br><span style='font-size:14px;'>Sistema de Vinculación entre Productores y Mecánicos</span></center></html>");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(titulo);

        return panel;
    }

    // Formulario de registro inicial
    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        campoNombre = new JTextField();
        campoTelefono = new JTextField();
        campoEmail = new JTextField();
        campoEspecialidad = new JTextField();

        labelEspecialidad = new JLabel("Especialidad mecánico:");

        comboRol = new JComboBox<>(new String[]{"Productor", "Mecánico"});

        // Al iniciar, como el rol por defecto es Productor, ocultamos especialidad
        campoEspecialidad.setVisible(false);
        labelEspecialidad.setVisible(false);

        // Cuando cambia el rol, se muestra u oculta el campo especialidad
        comboRol.addActionListener(e -> {
            boolean esMecanico = comboRol.getSelectedItem().toString().equals("Mecánico");
            campoEspecialidad.setVisible(esMecanico);
            labelEspecialidad.setVisible(esMecanico);
        });

        JButton botonIngresar = new JButton("Ingresar al sistema");
        botonIngresar.setBackground(new Color(45, 140, 65));
        botonIngresar.setForeground(Color.WHITE);
        botonIngresar.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(new JLabel("Seleccione rol:"));
        panel.add(comboRol);

        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);

        panel.add(new JLabel("Teléfono:"));
        panel.add(campoTelefono);

        panel.add(new JLabel("Email:"));
        panel.add(campoEmail);

        panel.add(labelEspecialidad);
        panel.add(campoEspecialidad);

        panel.add(new JLabel(""));
        panel.add(botonIngresar);

        botonIngresar.addActionListener(e -> ingresarSistema());

        return panel;
    }

    // Valida los datos y abre la ventana correspondiente
    private void ingresarSistema() {
        try {
            String rol = comboRol.getSelectedItem().toString();
            String nombre = campoNombre.getText();
            String telefono = campoTelefono.getText();
            String email = campoEmail.getText();
            String especialidad = campoEspecialidad.getText();

            if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
                throw new IllegalArgumentException("Debe completar nombre, teléfono y email.");
            }

            if (rol.equals("Productor")) {
                VentanaProductor ventanaProductor = new VentanaProductor(sistema);
                ventanaProductor.setVisible(true);
                dispose();
            } else {
                if (especialidad.isEmpty()) {
                    throw new IllegalArgumentException("Debe completar la especialidad del mecánico.");
                }

                VentanaMecanico ventanaMecanico = new VentanaMecanico(sistema);
                ventanaMecanico.setVisible(true);
                dispose();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}