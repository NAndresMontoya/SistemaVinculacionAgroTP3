import javax.swing.*;
import java.awt.*;

public class VentanaMecanico extends JFrame {

    // Sistema que contiene la lógica y la lista de solicitudes
    private SistemaSolicitudes sistema;

    // Componentes visuales principales
    private JTextArea areaSolicitudes;
    private JTextField campoIdAceptar;
    private JLabel labelCantidadSolicitudes;

    public VentanaMecanico(SistemaSolicitudes sistema) {
        this.sistema = sistema;

        setTitle("Panel del Mecánico - Sistema de Vinculación Agro");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearMenuLateral(), BorderLayout.WEST);
        add(crearPanelPrincipal(), BorderLayout.CENTER);

        mostrarSolicitudes();
    }

    // Crea el menú lateral con las opciones del mecánico
    private JPanel crearMenuLateral() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(220, 600));
        panel.setBackground(new Color(20, 83, 45));
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel titulo = new JLabel("<html><center>SV Agro<br>Panel Mecánico</center></html>", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnVer = crearBotonMenu("Ver Solicitudes");
        JButton btnAceptar = crearBotonMenu("Aceptar Solicitud");
        JButton btnAceptadas = crearBotonMenu("Solicitudes Aceptadas");
        JButton btnCerrarSesion = crearBotonMenu("Cerrar Sesión");

        // Refresca el listado completo de solicitudes
        btnVer.addActionListener(e -> mostrarSolicitudes());

        // Lleva el cursor al campo donde se escribe el ID
        btnAceptar.addActionListener(e -> campoIdAceptar.requestFocus());

        // Muestra solamente las solicitudes aceptadas y los datos de contacto
        btnAceptadas.addActionListener(e -> mostrarSolicitudesAceptadas());

        // Por ahora cierra la ventana. Luego puede volver a VentanaInicio.
        btnCerrarSesion.addActionListener(e -> dispose());
        new VentanaInicio(sistema).setVisible(true);

        panel.add(titulo);
        panel.add(btnVer);
        panel.add(btnAceptar);
        panel.add(btnAceptadas);
        panel.add(btnCerrarSesion);

        return panel;
    }

    // Crea botones con el mismo estilo visual
    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(45, 140, 65));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 15));
        boton.setFocusPainted(false);
        return boton;
    }

    // Crea el panel principal de la ventana del mecánico
    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel titulo = new JLabel("<html>Panel del Mecánico<br><span style='font-size:14px;'>Visualización y aceptación de solicitudes</span></html>");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(20, 83, 45));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(crearPanelSolicitudes(), BorderLayout.CENTER);
        panel.add(crearPanelAceptar(), BorderLayout.SOUTH);

        return panel;
    }

    // Crea el cuadro donde se muestran las solicitudes
    private JPanel crearPanelSolicitudes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Solicitudes disponibles"));

        labelCantidadSolicitudes = new JLabel("Solicitudes registradas: 0");

        areaSolicitudes = new JTextArea();
        areaSolicitudes.setEditable(false);
        areaSolicitudes.setFont(new Font("Monospaced", Font.PLAIN, 13));

        panel.add(labelCantidadSolicitudes, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaSolicitudes), BorderLayout.CENTER);

        return panel;
    }

    // Crea el sector inferior para aceptar una solicitud por ID
    private JPanel crearPanelAceptar() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Aceptar solicitud"));

        campoIdAceptar = new JTextField();
        JButton botonAceptar = new JButton("Aceptar solicitud");

        botonAceptar.setBackground(new Color(45, 140, 65));
        botonAceptar.setForeground(Color.WHITE);

        panel.add(new JLabel("ID de solicitud:"));
        panel.add(campoIdAceptar);
        panel.add(new JLabel(""));
        panel.add(botonAceptar);

        botonAceptar.addActionListener(e -> aceptarSolicitud());

        return panel;
    }

    // Muestra todas las solicitudes cargadas en el sistema
    private void mostrarSolicitudes() {
        labelCantidadSolicitudes.setText("Solicitudes registradas: " + sistema.listarSolicitudes().size());
        areaSolicitudes.setText("");

        if (sistema.listarSolicitudes().isEmpty()) {
            areaSolicitudes.setText("No hay solicitudes disponibles.");
            return;
        }

        for (Solicitud solicitud : sistema.listarSolicitudes()) {
            areaSolicitudes.append(solicitud.toString() + "\n\n");
        }
    }

    // Muestra solo las solicitudes aceptadas junto con los datos de contacto del productor
    private void mostrarSolicitudesAceptadas() {
        areaSolicitudes.setText("");

        boolean hayAceptadas = false;

        for (Solicitud solicitud : sistema.listarSolicitudes()) {
            if (solicitud.getEstado().equals("Aceptada")) {
                hayAceptadas = true;

                areaSolicitudes.append(
                        solicitud.toString() + "\n" +
                        "Contacto del productor:\n" +
                        "Nombre: " + solicitud.getProductor().getNombre() + "\n" +
                        "Teléfono: " + solicitud.getProductor().getTelefono() + "\n" +
                        "Email: " + solicitud.getProductor().getEmail() + "\n" +
                        "========================================\n\n"
                );
            }
        }

        if (!hayAceptadas) {
            areaSolicitudes.setText("No hay solicitudes aceptadas.");
        }
    }

    // Acepta una solicitud pendiente a partir del ID ingresado
    private void aceptarSolicitud() {
        try {
            int id = Integer.parseInt(campoIdAceptar.getText());

            Solicitud solicitud = sistema.buscarSolicitudPorId(id);

            if (solicitud == null) {
                JOptionPane.showMessageDialog(this, "No se encontró una solicitud con ese ID.");
                return;
            }

            boolean resultado = sistema.aceptarSolicitud(id);

            if (resultado) {
                JOptionPane.showMessageDialog(this,
                        "Solicitud aceptada correctamente.\n\n" +
                        "Datos de contacto del productor:\n" +
                        "Nombre: " + solicitud.getProductor().getNombre() + "\n" +
                        "Teléfono: " + solicitud.getProductor().getTelefono() + "\n" +
                        "Email: " + solicitud.getProductor().getEmail()
                );
            } else {
                JOptionPane.showMessageDialog(this, "La solicitud ya fue aceptada o no está disponible.");
            }

            campoIdAceptar.setText("");
            mostrarSolicitudes();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID numérico.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}