package entidades;

import data.Camino;
import data.EnemigoData;
import entidades.Enemigo;

public class Mago extends Enemigo {

	private int tipoMago;


	public Mago(Camino camino, EnemigoData data) {
		super(camino, data);
	}
}