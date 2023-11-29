package fallout.proyecto_poo;

import kotlin.contracts.Effect;

public class GolpeEfecto {

    private Effect effect;
    private double chance;

    public GolpeEfecto(Effect effect, double chance){
        this.effect = effect;
        this.chance = chance;
    }

    public Effect getEffect(){return effect;}
    public double getChance(){return chance;}
}
