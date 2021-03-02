package com.evertec.cargararchivoplano.entity;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Oswaldo Tuberquia
 */
public class Cartera implements java.io.Serializable {

    private int idcartera;
    private String idcliente;
    private String nombrecliente;
    private String correo;
    private BigDecimal montodeuda;
    private String iddeuda;
    private java.sql.Date vencimiento;

    public Cartera() {
    }

    public Cartera(int idcartera, String idcliente, String nombrecliente,
            String correo, BigDecimal montodeuda, String iddeuda, Date vencimiento) {
        this.idcartera = idcartera;
        this.idcliente = idcliente;
        this.nombrecliente = nombrecliente;
        this.correo = correo;
        this.montodeuda = montodeuda;
        this.iddeuda = iddeuda;
        this.vencimiento = vencimiento;
    }

    public int getIdcartera() {
        return idcartera;
    }

    public void setIdcartera(int idcartera) {
        this.idcartera = idcartera;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public BigDecimal getMontodeuda() {
        return montodeuda;
    }

    public void setMontodeuda(BigDecimal montodeuda) {
        this.montodeuda = montodeuda;
    }

    public String getIddeuda() {
        return iddeuda;
    }

    public void setIddeuda(String iddeuda) {
        this.iddeuda = iddeuda;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    } 

}
