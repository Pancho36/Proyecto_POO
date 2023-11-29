package entidades;

import data.Camino;
import data.EnemigoData;
import entidades.Mago;

public class Sanador extends Mago {

	private int restaracionVida;

	public Sanador(Camino camino, EnemigoData data) {
		super(camino, data);
	}

	public void curar() {
		// TODO - implement Sanador.curar
		throw new UnsupportedOperationException();
	}

}