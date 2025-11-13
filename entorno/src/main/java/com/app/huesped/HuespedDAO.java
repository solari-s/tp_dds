package com.app.huesped;

import java.util.List;

public interface HuespedDAO {

    // Guarda un nuevo huésped a partir de su DTO.
    boolean darDeAltaHuesped(HuespedDTO huespedDto);

    // Modifica los datos de un huésped existente a partir de su DTO.
    void modificarHuesped(HuespedDTO huespedDto);

    // Elimina un huésped de la fuente de datos.
    void darDeBajaHuesped(HuespedDTO huespedDto);

    // Busca un huésped por tipoDoc y su doc. Devuelve el objeto de dominio
    // completo.
    HuespedDTO buscarHuesped(TipoDoc tipoDoc, String nroDoc);

    // Busca un huésped por su nombre o su apellido. Devuelve el objeto de dominio
    // completo.
    List<HuespedDTO> buscarHuesped(String criterio);
}