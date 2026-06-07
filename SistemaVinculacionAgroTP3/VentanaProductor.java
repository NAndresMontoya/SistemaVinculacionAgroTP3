import javax.swing.*;
import java.awt.*;

public class VentanaProductor extends JFrame {

    // Sistema compartido donde se guardan las solicitudes
    private SistemaSolicitudes sistema;

    // Datos del productor
    private JTextField campoNombreProductor;
    private JTextField campoTelefonoProductor;
    private JTextField campoEmailProductor;

    // Datos de la solicitud
    private JTextField campoMaquinaria;
    private JTextField campoMarca;
    private JTextField campoModelo;
    private JTextField campoProblema;
    private JTextField campoUbicacion;
    private JComboBox<String> comboUrgencia;

    // Área donde el productor ve sus solicitudes cargadas
    private JTextArea areaMisSolicitudes;

    public VentanaProductor(SistemaSolicitudes sistema) {
        this.sistema = sistema;

        setTitle("Panel del Productor - Sistema de Vinculación Agro");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearMenuLateral(), BorderLayout.WEST);
        add(crearPanelPrincipal(), BorderLayout.CENTER);
    }

    // Menú lateral del productor
    private JPanel crearMenuLateral() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(220, 650));
        panel.setBackground(new Color(20, 83, 45));
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel titulo = new JLabel("<html><center>SV Agro<br>Panel Productor</center></html>", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnRegistrar = crearBotonMenu("Crear Solicitud");
        JButton btnMisSolicitudes = crearBotonMenu("Mis Solicitudes");
        JButton btnCerrarSesion = crearBotonMenu("Cerrar Sesión");

        btnRegistrar.addActionListener(e -> campoMaquinaria.requestFocus());
        btnMisSolicitudes.addActionListener(e -> mostrarSolicitudes());
        btnCerrarSesion.addActionListener(e -> dispose());
        new VentanaInicio(sistema).setVisible(true);

        panel.add(titulo);
        panel.add(btnRegistrar);
        panel.add(btnMisSolicitudes);
        panel.add(btnCerrarSesion);

        return panel;
    }

    // Estilo común para los botones del menú
    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(45, 140, 65));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 15));
        boton.setFocusPainted(false);
        return boton;
    }

    // Panel principal
    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel titulo = new JLabel("<html>Panel del Productor<br><span style='font-size:14px;'>Registro de datos y creación de solicitudes</span></html>");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(20, 83, 45));

        JPanel centro = new JPanel(new GridLayout(1, 2, 20, 20));
        centro.add(crearPanelFormulario());
        centro.add(crearPanelMisSolicitudes());

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(centro, BorderLayout.CENTER);

        return panel;
    }

    // Formulario del productor y solicitud
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(11, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Crear solicitud de asistencia técnica"));

        campoNombreProductor = new JTextField();
        campoTelefonoProductor = new JTextField();
        campoEmailProductor = new JTextField();

        campoMaquinaria = new JTextField();
        campoMarca = new JTextField();
        campoModelo = new JTextField();
        campoProblema = new JTextField();
        campoUbicacion = new JTextField();
        comboUrgencia = new JComboBox<>(new String[]{"Baja", "Media", "Alta"});

        JButton botonRegistrar = new JButton("Registrar solicitud");
        botonRegistrar.setBackground(new Color(45, 140, 65));
        botonRegistrar.setForeground(Color.WHITE);

        panel.add(new JLabel("Nombre del productor:"));
        panel.add(campoNombreProductor);

        panel.add(new JLabel("Teléfono:"));
        panel.add(campoTelefonoProductor);

        panel.add(new JLabel("Email:"));
        panel.add(campoEmailProductor);

        panel.add(new JLabel("Tipo de maquinaria:"));
        panel.add(campoMaquinaria);

        panel.add(new JLabel("Marca:"));
        panel.add(campoMarca);

        panel.add(new JLabel("Modelo:"));
        panel.add(campoModelo);

        panel.add(new JLabel("Problema detectado:"));
        panel.add(campoProblema);

        panel.add(new JLabel("Ubicación:"));
        panel.add(campoUbicacion);

        panel.add(new JLabel("Urgencia:"));
        panel.add(comboUrgencia);

        panel.add(new JLabel(""));
        panel.add(botonRegistrar);

        botonRegistrar.addActionListener(e -> registrarSolicitud());

        return panel;
    }

    // Panel donde se muestran las solicitudes creadas
    private JPanel crearPanelMisSolicitudes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Mis solicitudes"));

        areaMisSolicitudes = new JTextArea();
        areaMisSolicitudes.setEditable(false);
        areaMisSolicitudes.setFont(new Font("Monospaced", Font.PLAIN, 13));

        panel.add(new JScrollPane(areaMisSolicitudes), BorderLayout.CENTER);

        return panel;
    }

    // Registra una nueva solicitud en el sistema
    private void registrarSolicitud() {
        try {
            String nombre = campoNombreProductor.getText();
            String telefono = campoTelefonoProductor.getText();
            String email = campoEmailProductor.getText();

            String tipoMaquinaria = campoMaquinaria.getText();
            String marca = campoMarca.getText();
            String modelo = campoModelo.getText();
            String problema = campoProblema.getText();
            String ubicacion = campoUbicacion.getText();
            String urgencia = comboUrgencia.getSelectedItem().toString();

            if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty() ||
                    tipoMaquinaria.isEmpty() || problema.isEmpty() || ubicacion.isEmpty()) {
                throw new IllegalArgumentException("Debe completar los datos principales del productor y la solicitud.");
            }

            Productor productor = new Productor(1, nombre, telefono, email);
            Maquinaria maquinaria = new Maquinaria(1, tipoMaquinaria, marca, modelo);

            sistema.registrarSolicitud(productor, maquinaria, problema, ubicacion, urgencia);

            JOptionPane.showMessageDialog(this, "Solicitud registrada correctamente.");

            limpiarCamposSolicitud();
            mostrarSolicitudes();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Muestra todas las solicitudes registradas en el sistema
    private void mostrarSolicitudes() {
        areaMisSolicitudes.setText("");

        if (sistema.listarSolicitudes().isEmpty()) {
            areaMisSolicitudes.setText("No hay solicitudes registradas.");
            return;
        }

        for (Solicitud solicitud : sistema.listarSolicitudes()) {
            areaMisSolicitudes.append(solicitud.toString() + "\n\n");
        }
    }

    // Limpia solo los campos de la solicitud, dejando los datos del productor cargados
    private void limpiarCamposSolicitud() {
        campoMaquinaria.setText("");
        campoMarca.setText("");
        campoModelo.setText("");
        campoProblema.setText("");
        campoUbicacion.setText("");
        comboUrgencia.setSelectedIndex(0);
    }
}