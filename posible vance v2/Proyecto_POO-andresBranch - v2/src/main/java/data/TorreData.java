package data;

import java.util.Collections;
import java.util.List;

public record TorreData(

        String nombre,
        String nombreImagen,
        String proyectilImagen,
        int ataque,
        double velocidadAtaque,
        int costo,
        boolean dañoEnArea,
        List<String> efectos
) {

    @Override
    public List<String> efectos(){return efectos!=null ? efectos : Collections.emptyList();
    }
}
