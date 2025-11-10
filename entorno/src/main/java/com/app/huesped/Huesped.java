package huesped;

import java.util.Date;

public class Huesped {

    private String nombre;
    private String apellido;
    private TipoDoc tipo_documento;
    private String nroDocumento;
    private Date fechaDeNacimiento;
    private String nacionalidad;
    private String email;
    private String telefono;
    private String ocupacion;
    private boolean alojado;

    public Huesped(String nombre, String apellido, TipoDoc tipo_documento, String nroDocumento,
            Date fechaDeNacimiento, String nacionalidad, String email,
            String telefono, String ocupacion, boolean alojado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipo_documento = tipo_documento;
        this.nroDocumento = nroDocumento;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.nacionalidad = nacionalidad;
        this.email = email;
        this.telefono = telefono;
        this.ocupacion = ocupacion;
        this.alojado = alojado;
    }

    public Huesped(HuespedDTO huespedDto) {
        this.nombre = huespedDto.getNombre();
        this.apellido = huespedDto.getApellido();
        this.tipo_documento = huespedDto.getTipo_documento();
        this.nroDocumento = huespedDto.getNroDocumento();
        this.fechaDeNacimiento = huespedDto.getFechaDeNacimiento();
        this.nacionalidad = huespedDto.getNacionalidad();
        this.email = huespedDto.getEmail();
        this.telefono = huespedDto.getTelefono();
        this.ocupacion = huespedDto.getOcupacion();
        this.alojado = huespedDto.isAlojado();
    }


    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public TipoDoc getTipo_documento() {
        return tipo_documento;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public boolean isAlojado() {
        return alojado;
    }

}