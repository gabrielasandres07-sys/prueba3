/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemagestionempleados.vista;

/**
 *
 * @author USER
 */


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import sistemagestionempleados.controlador.ControladorEmpleados;
import sistemagestionempleados.modelo.Empleado;
import sistemagestionempleados.modelo.EmpleadoAdministrativo;
import sistemagestionempleados.modelo.EmpleadoPorHoras;

public class VentanaEmpleados extends JFrame {

    private ControladorEmpleados controlador;

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtSalarioBase;
    private JTextField txtBonificacion;
    private JTextField txtDescuento;
    private JTextField txtHoras;
    private JTextField txtTarifa;
    private JTextField txtBuscar;

    private JComboBox<String> cmbTipo;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public VentanaEmpleados() {
        controlador = new ControladorEmpleados();

        setTitle("Sistema de Gestión de Empleados");
        setSize(1050, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        crearFormulario();
        crearTabla();
        crearBotones();
        actualizarTabla();
    }

    private void crearFormulario() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del empleado"));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtSalarioBase = new JTextField();
        txtBonificacion = new JTextField();
        txtDescuento = new JTextField();
        txtHoras = new JTextField();
        txtTarifa = new JTextField();
        txtBuscar = new JTextField();

        cmbTipo = new JComboBox<>(new String[]{
            "Administrativo", "Por horas"
        });

        agregarCampo(panel, "Código:", txtCodigo);
        agregarCampo(panel, "Nombre:", txtNombre);
        agregarCampo(panel, "Salario base:", txtSalarioBase);
        agregarCampo(panel, "Tipo:", cmbTipo);
        agregarCampo(panel, "Bonificación:", txtBonificacion);
        agregarCampo(panel, "Descuento (%):", txtDescuento);
        agregarCampo(panel, "Horas trabajadas:", txtHoras);
        agregarCampo(panel, "Tarifa por hora:", txtTarifa);

        add(panel, BorderLayout.NORTH);
    }

    private void agregarCampo(JPanel panel, String texto,
            JComponent campo) {
        panel.add(new JLabel(texto));
        panel.add(campo);
    }

    private void crearTabla() {
        modeloTabla = new DefaultTableModel(new String[]{
                    "Código", "Nombre", "Tipo",
                    "Salario calculado", "Categoría"
                }, 0) {

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Empleados registrados"));

        add(scroll, BorderLayout.CENTER);
    }

    private void crearBotones() {
        JPanel panel = new JPanel(new GridLayout(0, 3, 8, 8));

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCalcular = new JButton("Calcular salario");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnClonar = new JButton("Clonar empleado");
        JButton btnRegistrarClon = new JButton("Registrar clon");
        JButton btnComparar = new JButton("Comparar objetos");
        JButton btnMaximo = new JButton("Salario máximo");
        JButton btnPromedio = new JButton("Promedio salarial");
        JButton btnLimpiar = new JButton("Limpiar formulario");

        panel.add(btnRegistrar);
        panel.add(btnCalcular);
        panel.add(btnBuscar);
        panel.add(btnClonar);
        panel.add(btnRegistrarClon);
        panel.add(btnComparar);
        panel.add(btnMaximo);
        panel.add(btnPromedio);
        panel.add(btnLimpiar);

        JPanel inferior = new JPanel(new BorderLayout(5, 5));
        JPanel busqueda = new JPanel(new BorderLayout(5, 5));
        busqueda.add(new JLabel("Código para buscar:"), BorderLayout.WEST);
        busqueda.add(txtBuscar, BorderLayout.CENTER);

        inferior.add(busqueda, BorderLayout.NORTH);
        inferior.add(panel, BorderLayout.CENTER);
        add(inferior, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> registrarEmpleado());
        btnCalcular.addActionListener(e -> calcularSalario());
        btnBuscar.addActionListener(e -> buscarEmpleado());
        btnClonar.addActionListener(e -> clonarEmpleado());
        btnRegistrarClon.addActionListener(e -> registrarClon());
        btnComparar.addActionListener(e -> compararObjetos());
        btnMaximo.addActionListener(e -> mostrarMaximo());
        btnPromedio.addActionListener(e -> mostrarPromedio());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    private void registrarEmpleado() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            double salarioBase = Double.parseDouble(
                    txtSalarioBase.getText().trim());

            Empleado empleado;

            if (cmbTipo.getSelectedIndex() == 0) {
                double bonificacion = Double.parseDouble(txtBonificacion.getText().trim());
                double descuento = Double.parseDouble( txtDescuento.getText().trim());

                empleado = new EmpleadoAdministrativo(codigo, nombre, salarioBase,bonificacion, descuento);

            } else {
                int horas = Integer.parseInt(txtHoras.getText().trim());
                double tarifa = Double.parseDouble(txtTarifa.getText().trim());

                empleado = new EmpleadoPorHoras(codigo, nombre, salarioBase, horas, tarifa);
            }

            controlador.registrarEmpleado(empleado);
            actualizarTabla();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this,"Empleado registrado correctamente.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"Verifica que los campos numéricos tengan valores válidos.",
                    "Error de datos", JOptionPane.ERROR_MESSAGE);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularSalario() {
        Empleado empleado = obtenerSeleccionado();

        if (empleado == null) {
            JOptionPane.showMessageDialog(this,"Selecciona un empleado de la tabla.");
            return;
        }

        JOptionPane.showMessageDialog(this,empleado.mostrarInformacion());
    }

    private void buscarEmpleado() {
        String codigo = txtBuscar.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Escribe el código que deseas buscar.");
            return;
        }

        Empleado empleado = controlador.buscarEmpleado(codigo);

        if (empleado == null) {
            JOptionPane.showMessageDialog(this,"No se encontró el empleado.");
            return;
        }

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 0).toString().equalsIgnoreCase(codigo)) {
                tabla.setRowSelectionInterval(i, i);
                tabla.scrollRectToVisible(tabla.getCellRect(i, 0, true));
                break;
            }
        }

        JOptionPane.showMessageDialog(this,empleado.mostrarInformacion());
    }

    private void clonarEmpleado() {
        Empleado original = obtenerSeleccionado();

        if (original == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un empleado para clonar.");
            return;
        }

        String nuevoCodigo = JOptionPane.showInputDialog(this, "Escribe el código nuevo para el clon:");

        if (nuevoCodigo == null || nuevoCodigo.trim().isEmpty()) {
            return;
        }

        try {
            Empleado clon = controlador.clonarEmpleado(
                    original.getCodigo(), nuevoCodigo.trim());

            mostrarDatosEnFormulario(clon);

            JOptionPane.showMessageDialog(this, "Copia preparada. Revisa los datos y pulsa "+ "'Registrar clon' para guardarla.");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private Empleado clonPendiente;

    private void mostrarDatosEnFormulario(Empleado empleado) {
        clonPendiente = empleado;

        txtCodigo.setText(empleado.getCodigo());
        txtNombre.setText(empleado.getNombre());
        txtSalarioBase.setText(String.valueOf(empleado.getSalarioBase()));

        if (empleado instanceof EmpleadoAdministrativo) {
            EmpleadoAdministrativo admin =(EmpleadoAdministrativo) empleado;

            cmbTipo.setSelectedIndex(0);
            txtBonificacion.setText(
                    String.valueOf(admin.getBonificacion()));
            txtDescuento.setText(
                    String.valueOf(admin.getDescuento()));

        } else if (empleado instanceof EmpleadoPorHoras) {
            EmpleadoPorHoras horas = (EmpleadoPorHoras) empleado;

            cmbTipo.setSelectedIndex(1);
            txtHoras.setText(
                    String.valueOf(horas.getHorasTrabajadas()));
            txtTarifa.setText(
                    String.valueOf(horas.getTarifaPorHora()));
        }
    }

    private void registrarClon() {
        if (clonPendiente == null) {
            JOptionPane.showMessageDialog(this,"Primero debes clonar un empleado.");
            return;
        }

        try {
            String nuevoCodigo = txtCodigo.getText().trim();

            if (nuevoCodigo.isEmpty()) {
                JOptionPane.showMessageDialog(this,"El código es obligatorio.");
                return;
            }

            clonPendiente.setCodigo(nuevoCodigo);
            controlador.registrarClon(clonPendiente);

            clonPendiente = null;
            actualizarTabla();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this, "Clon registrado correctamente.");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void compararObjetos() {
        int fila1 = tabla.getSelectedRow();

        if (fila1 < 0) {
            JOptionPane.showMessageDialog(this,"Selecciona el primer empleado.");
            return;
        }

        int fila2 = -1;

        String codigo2 = JOptionPane.showInputDialog(this,"Escribe el código del segundo empleado:");

        if (codigo2 == null || codigo2.trim().isEmpty()) {
            return;
        }

        Empleado primero = controlador.buscarEmpleado(modeloTabla.getValueAt(fila1, 0).toString());
        Empleado segundo = controlador.buscarEmpleado(codigo2.trim());

        if (segundo == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el segundo empleado.");
            return;
        }

        boolean mismaReferencia =controlador.mismaReferencia(primero, segundo);
        boolean mismosDatos = controlador.mismosDatos(primero, segundo);

        JOptionPane.showMessageDialog(this,"¿Misma referencia?: " + mismaReferencia+ "\n¿Mismos datos?: " + mismosDatos);
    }

    private void mostrarMaximo() {
        try {
            Empleado mayor = controlador.obtenerEmpleadoMayorSalario();

            if (mayor == null) {
                JOptionPane.showMessageDialog(this,"No hay empleados registrados.");
                return;
            }

            JOptionPane.showMessageDialog(this,"Empleado con mayor salario:\n" + mayor.mostrarInformacion());

        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void mostrarPromedio() {
        try {
            double promedio =controlador.calcularPromedioSalarios();

            JOptionPane.showMessageDialog(this,String.format("Promedio salarial: %.2f", promedio));

        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private Empleado obtenerSeleccionado() {
        int fila = tabla.getSelectedRow();

        if (fila < 0) {
            return null;
        }

        String codigo =modeloTabla.getValueAt(fila, 0).toString();

        return controlador.buscarEmpleado(codigo);
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);

        ArrayList<Empleado> empleados =controlador.getEmpleados();

        for (Empleado empleado : empleados) {
            String tipo;

            if (empleado instanceof EmpleadoAdministrativo) {
                tipo = "Administrativo";
            } else {
                tipo = "Por horas";
            }

            modeloTabla.addRow(new Object[]{
                empleado.getCodigo(),
                empleado.getNombre(),
                tipo,
                String.format("%.2f", empleado.calcularSalario()),
                empleado.obtenerCategoria()
            });
        }
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtSalarioBase.setText("");
        txtBonificacion.setText("");
        txtDescuento.setText("");
        txtHoras.setText("");
        txtTarifa.setText("");
        txtBuscar.setText("");

        clonPendiente = null;
        cmbTipo.setSelectedIndex(0);
        tabla.clearSelection();
    }
}
