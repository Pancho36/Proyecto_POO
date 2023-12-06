package fallout.proyecto_poo.Exceptions;

public class InvalidPositionException extends Exception{
    public InvalidPositionException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return "Turrets can't be placed on the route or in other turrets";
    }
}
