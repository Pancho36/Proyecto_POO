package edificios;

public abstract class Edificio {

	private int tipoEdificio;
	private int nivel = 1;
	private int tiempoConstruccion;




	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public void subirNivel(Edificio edificio) {
		// TODO - implement Edificio.sibirNivel

		setNivel(nivel+1);
		throw new UnsupportedOperationException();
	}

}