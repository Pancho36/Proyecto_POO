package entidades;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import data.Camino;
import data.EnemigoData;
import fallout.proyecto_poo.FalloutTDApplication;
import javafx.geometry.Point2D;

import java.util.List;
public abstract class Enemigo extends Component {

	private String nombre;
	private int velocidad;
	private int vida;


	private List<Point2D> waypoints;
	private EnemigoData data;
	private Point2D nextWaypoint;

	public Enemigo(Camino camino, EnemigoData data) {
		waypoints = camino.getWaypoints();
		this.data = data;
	}


	public EnemigoData getData() {
		return data;
	}

	@Override
	public void onAdded() {
		nextWaypoint = waypoints.remove(0);

		entity.setPosition(nextWaypoint);
	}

	@Override
	public void onUpdate(double tpf) {
		double speed = tpf * 60 * data.velocidadMovimiento();

		Point2D velocity = nextWaypoint.subtract(entity.getPosition())
				.normalize()
				.multiply(speed);

		entity.translate(velocity);

		if (nextWaypoint.distance(entity.getPosition()) < speed) {
			entity.setPosition(nextWaypoint);

			if (!waypoints.isEmpty()) {
				nextWaypoint = waypoints.remove(0);
			} else {
				FXGL.<FalloutTDApplication>getAppCast().onEnemyReachedEnd(entity);

				entity.removeFromWorld();
			}
		}
	}
}