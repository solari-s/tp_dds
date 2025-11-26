package com.app.factura;

import com.app.responsablePago.ResponsablePago;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
@Table(name = "facturas")
public class Factura {

    //puede ser que la factura podría quedar a medio pagar o algo así?
    
    // pk para la persistencia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoFactura tipoFactura;
    
    @Column(nullable = false)
    private float valorEstadia;
    
    // CU 16 10.A. El sistema comprueba que el monto acumulado es menor a la deuda. 
    // 10.A.1. El sistema actualiza el campo “Total a pagar”
    @Column(nullable = false)
    private float totalAPagar;
    
    @Column(nullable = false)
    private float vuelto;
    
    // totalAPagar == 0 (?)
    @Column(nullable = false)
    private boolean pagado;

    @OneToOne
    private ResponsablePago responsablePago; 

    @OneToMany(orphanRemoval=true)
    @JoinColumn(name="facturas_id")
    private List<FormaDePago> formasDePago;

    // @OneToOne


    public Factura(){}

    //creo q esto es lo mínimo idkkk
    public Factura(ResponsablePago responsablePago, int valorEstadia, float totalAPagar){
        this.responsablePago = responsablePago;
        this.totalAPagar = totalAPagar;
        this.valorEstadia = valorEstadia;
        formasDePago = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoFactura getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(TipoFactura tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public float getValorEstadia() {
        return valorEstadia;
    }

    public void setValorEstadia(float valorEstadia) {
        this.valorEstadia = valorEstadia;
    }

    public float getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(float totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public float getVuelto() {
        return vuelto;
    }

    public void setVuelto(float vuelto) {
        this.vuelto = vuelto;
    }

    public boolean getPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public ResponsablePago getResponsablePago(){
        return responsablePago;
    }

    public void setResponsablePago(ResponsablePago p){
        this.responsablePago = p;
    }

    public List<FormaDePago> getFormasDePago() {
        return formasDePago;
    }

    public void setFormasDePago(List<FormaDePago> formasDePago) {
        this.formasDePago = formasDePago;
    }

    public void agregarFormaDePago(FormaDePago f){
        formasDePago.add(f);
    }

}
