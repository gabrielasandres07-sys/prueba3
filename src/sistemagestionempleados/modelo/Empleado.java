/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemagestionempleados.modelo;

/**
 *
 * @author USER
 */


public abstract class Empleado implements Cloneable {

    private String codigo;
    private String nombre;
    private double salarioBase;

    public Empleado(String codigo, String nombre, double salarioBase) {
        setCodigo(codigo);
        setNombre(nombre);
        setSalarioBase(salarioBase);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }

        this.codigo = codigo.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        this.nombre = nombre.trim();
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        if (salarioBase < 0) {
            throw new IllegalArgumentException("El salario base no puede ser negativo.");
        }

        this.salarioBase = salarioBase;
    }

    public abstract double calcularSalario();

    public String mostrarInformacion() {
        return "Código: " + codigo
                + "\nNombre: " + nombre
                + "\nSalario base: " + salarioBase
                + "\nSalario calculado: " + calcularSalario()
                + "\nCategoría: " + obtenerCategoria();
    }

    public String obtenerCategoria() {
        double salario = calcularSalario();

        if (salario < 10000) {
            return "Estándar";
        } else if (salario < 20000) {
            return "Superior";
        } else {
            return "Premium";
        }
    }

    @Override
    public Empleado clone() {
        try {
            return (Empleado) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
