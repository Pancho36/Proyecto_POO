package entidades;

import com.almasb.fxgl.core.collection.Array;

public class Cuartel extends Edificio {

	private int velocidadGeneracion;
	private int rangoGeneracion;
	private Array<Soldado> soldados = new Array<Soldado>();
	private int nroSoldados = 0;
	private int maxSoldados = 6;

	public void generarSoldado() {
		// TODO - implement Cuartel.generarSoldado
		if (nroSoldados < maxSoldados) {

			Soldado nuevoSoldado = new Soldado(10,2);
			soldados.add(nuevoSoldado);
			nroSoldados++;

			System.out.println("Soldado generado: " + nuevoSoldado);
		} else {
			System.out.println("No se pueden generar más soldados. Límite alcanzado.");
		}

		throw new UnsupportedOperationException();
	}


}