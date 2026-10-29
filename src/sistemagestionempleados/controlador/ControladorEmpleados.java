/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemagestionempleados.controlador;

/**
 *
 * @author USER
 */

import java.util.ArrayList;
import sistemagestionempleados.modelo.Empleado;
import sistemagestionempleados.modelo.EmpleadoAdministrativo;
import sistemagestionempleados.modelo.EmpleadoPorHoras;

public class ControladorEmpleados {

    private ArrayList<Empleado> empleados;

    public ControladorEmpleados() {
        empleados = new ArrayList<>();
    }

    public ArrayList<Empleado> getEmpleados() {
        return new ArrayList<>(empleados);
    }

    public void registrarEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no puede ser nulo.");
        }

        if (buscarEmpleado(empleado.getCodigo()) != null) {
            throw new IllegalArgumentException( "Ya existe un empleado con ese código.");
        }

        empleados.add(empleado);
    }

    public Empleado buscarEmpleado(String codigo) {
        for (Empleado empleado : empleados) {
            if (empleado.getCodigo().equalsIgnoreCase(codigo)) {
                return empleado;
            }
        }

        return null;
    }

    public Empleado clonarEmpleado(String codigoOriginal,
            String nuevoCodigo) {

        Empleado original = buscarEmpleado(codigoOriginal);

        if (original == null) {
            throw new IllegalArgumentException( "No se encontró el empleado seleccionado." );
        }

        if (buscarEmpleado(nuevoCodigo) != null) {
            throw new IllegalArgumentException("El código del clon ya existe." );
        }

        Empleado clon = original.clone();
        clon.setCodigo(nuevoCodigo);

        return clon;
    }

    public double obtenerSalarioMaximo() {
        if (empleados.isEmpty()) {
            throw new IllegalStateException( "No hay empleados registrados." );
        }

        double maximo = empleados.get(0).calcularSalario();

        for (Empleado empleado : empleados) {
            if (empleado.calcularSalario() > maximo) {
                maximo = empleado.calcularSalario();
            }
        }

        return maximo;
    }

    public Empleado obtenerEmpleadoMayorSalario() {
        if (empleados.isEmpty()) {
            return null;
        }

        Empleado mayor = empleados.get(0);

        for (Empleado empleado : empleados) {
            if (empleado.calcularSalario() > mayor.calcularSalario()) {
                mayor = empleado;
            }
        }

        return mayor;
    }

    public double calcularPromedioSalarios() {
        if (empleados.isEmpty()) {
            throw new IllegalStateException("No hay empleados registrados.");
        }

        double suma = 0;

        for (Empleado empleado : empleados) {
            suma += empleado.calcularSalario();
        }

        return suma / empleados.size();
    }

    public boolean mismaReferencia(Empleado empleado1,
            Empleado empleado2) {
        return empleado1 == empleado2;
    }

    public boolean mismosDatos(Empleado empleado1,
            Empleado empleado2) {

        if (empleado1 == null || empleado2 == null) {
            return false;
        }

        if (!empleado1.getCodigo().equals(empleado2.getCodigo())|| !empleado1.getNombre().equals(empleado2.getNombre())
                || Double.compare(
                    empleado1.getSalarioBase(),
                    empleado2.getSalarioBase()) != 0) {
            return false;
        }

        if (empleado1 instanceof EmpleadoAdministrativo
                && empleado2 instanceof EmpleadoAdministrativo) {

            EmpleadoAdministrativo a =(EmpleadoAdministrativo) empleado1;
            EmpleadoAdministrativo b =(EmpleadoAdministrativo) empleado2;

            return Double.compare( a.getBonificacion(), b.getBonificacion()) == 0&& Double.compare(a.getDescuento(), b.getDescuento()) == 0;
        }

        if (empleado1 instanceof EmpleadoPorHoras
                && empleado2 instanceof EmpleadoPorHoras) {

            EmpleadoPorHoras a = (EmpleadoPorHoras) empleado1;
            EmpleadoPorHoras b = (EmpleadoPorHoras) empleado2;

            return a.getHorasTrabajadas() == b.getHorasTrabajadas()&& Double.compare(a.getTarifaPorHora(), b.getTarifaPorHora()) == 0;
        }

        return false;
    }

    public void registrarClon(Empleado clon) {
        registrarEmpleado(clon);
    }

    public void limpiarRegistros() {
        empleados.clear();
    }
}
