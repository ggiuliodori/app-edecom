package edecom.edecom.estavezsale;

import java.util.Date;

/**
 * Created by Ailu on 12/3/2018.
 */

public class Licencia {



    private int id;

    //Datos personales
    private String nombre;
    private String direccion;
    private String dni;
    private Date fNac;
    private String nacionalidad;
    private String sexo;

    //Licencia
    private Date fOtorg;
    private Date fVto;
    private String claseLic;
    private String codSeg;
    private String dominio;

    public Licencia(String nombre, String direccion, String dni, Date fNac,
                    String nacionalidad, String sexo, Date fOtorg, Date fVto,
                    String claseLic, String codSeg, String dominio) {

        this.id = 0;
        this.nombre = nombre;
        this.direccion = direccion;
        this.dni = dni;
        this.fNac = fNac;
        this.nacionalidad = nacionalidad;
        this.sexo = sexo;
        this.fOtorg = fOtorg;
        this.fVto = fVto;
        this.claseLic = claseLic;
        this.codSeg = codSeg;
        this.dominio = dominio;
    }

    public Licencia(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getfNac() {
        return fNac;
    }

    public void setfNac(Date fNac) {
        this.fNac = fNac;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getfOtorg() {
        return fOtorg;
    }

    public void setfOtorg(Date fOtorg) {
        this.fOtorg = fOtorg;
    }

    public Date getfVto() {
        return fVto;
    }

    public void setfVto(Date fVto) {
        this.fVto = fVto;
    }

    public String getClaseLic() {
        return claseLic;
    }

    public void setClaseLic(String claseLic) {
        this.claseLic = claseLic;
    }

    public String getCodSeg() {
        return codSeg;
    }

    public void setCodSeg(String codSeg) {
        this.codSeg = codSeg;
    }

    public String getDominio() { return dominio; }

    public void setDominio(String dominio) { this.dominio = dominio; }

}
