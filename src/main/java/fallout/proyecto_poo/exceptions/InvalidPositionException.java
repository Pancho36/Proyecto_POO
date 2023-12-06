package fallout.proyecto_poo.exceptions;

public class InvalidPositionException extends Exception{
    public InvalidPositionException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return "Turrets can't be placed on the route or in other turrets";
    }
}
