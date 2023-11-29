package entidades;

import com.almasb.fxgl.entity.component.Component;

public abstract class Edificio extends Component {

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