package entidades;

import data.Camino;
import data.EnemigoData;
import entidades.Enemigo;

public class MonstruoNormal extends Enemigo {

	private int dano;


	public MonstruoNormal(Camino camino, EnemigoData data) {
		super(camino, data);
	}
}