package entidades;

import data.Camino;
import data.EnemigoData;
import entidades.Enemigo;

public class MonstruoElite extends Enemigo {

	private int daño;

	public MonstruoElite(Camino camino, EnemigoData data) {
		super(camino, data);
	}
}