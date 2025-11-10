package huesped;

public class Direccion {
    private String calle;
    private String departamento;
    private int altura;
    private int piso;
    private int codigoPostal;

    public Direccion(String calle, String departamento, int altura, int piso, int codigoPostal) {
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
        this.departamento = departamento;
        this.codigoPostal = codigoPostal;
    }
    //Getters
    public String getCalle() { return calle; }
    public String getDepartamento() { return departamento; }
    public int getAltura() { return altura; }
    public int getPiso() { return piso; }
    public int getCodigoPostal() { return codigoPostal; }
}
