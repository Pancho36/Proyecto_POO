package entidades;


import com.almasb.fxgl.dsl.effects.SlowTimeEffect;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.time.LocalTimer;
import data.TorreData;
import fallout.proyecto_poo.GolpeEfecto;
import fallout.proyecto_poo.TipoEntidad;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class Torre extends Edificio {

	private int cadenciaAtaque;
	private int costo;
	private int rango;
	private int ataque;

	public void atacar() {
		// TODO - implement Torre.atacar
		throw new UnsupportedOperationException();
	}


	private LocalTimer tiempoDisparo;
	private TorreData data;

	private Torre(TorreData data){this.data = data;}

	public int getAtaque(){return data.ataque();}

	public List<GolpeEfecto> efectosGolpes(){
		return List.of(
				new GolpeEfecto(new SlowTimeEffect(0.2, Duration.seconds(3)), 0.75)
		);
	}


	@Override
	public void onAdded(){
		tiempoDisparo = newLocalTimer();
		tiempoDisparo.capture();
	}

	@Override
	public void onUpdate(double tpf){
		if (tiempoDisparo.elapsed(Duration.seconds(1))){
			getGameWorld()
					.getClosestEntity(entity,e->e.isType(TipoEntidad.ENEMIGO))
					.ifPresent(nearrestEnemigo->{

						entity.rotateToVector(nearrestEnemigo.getPosition().subtract(entity.getPosition()));
						entity.rotateBy(90);

						disparo(nearrestEnemigo);
						tiempoDisparo.capture();
					});
		}
	}

	private void disparo(Entity enemigo){
		Point2D posicion = getEntity().getPosition();
		Point2D direccion = enemigo.getPosition().subtract(posicion);

		String nombreImagen = Objects.requireNonNullElse(data.proyectilImagen(),"proyectil.png");
		var bala = spawn("bala",
				new SpawnData(posicion)
						.put("nombreImagen",nombreImagen)
						.put("torre",entity)
						.put("objetivo",enemigo)
				);
	}
}