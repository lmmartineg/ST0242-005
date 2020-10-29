
/**
 * Esta clase maneja el juego
 * Se tiene una referencia al tablero y al pacman
 * En esta clase se hace la interacciÃ³n con el usuario
 * @author Helmuth Trefftz
 */
import java.util.Scanner;

public class Juego {

    /**
     * El numero de puntos iniciales de vida del pacman
     */
    public static final int PUNTOS_VIDA_INICIALES = 10;
    private boolean PierdeElJuego=false;
    private int contadorturno=0;
    private int vida=PUNTOS_VIDA_INICIALES;
    Tablero tablero;
    Pacman pacman;

    /**
     * Constructor
     * Se crea un tablero
     */
    public Juego() {
        tablero = new Tablero(this);
    }

    /**
     * Interacción con el usuario
     */
    public void jugar() {
        boolean ganaElJuego = false;
        
        tablero.dibujarTablero();
        Scanner in = new Scanner(System.in);
        String linea = in.nextLine();
        while (!linea.equals("q") && !ganaElJuego) {
            int fila = pacman.posicion.fila;
            int col = pacman.posicion.col;
            int nuevaFila = fila;
            int nuevaCol = col;
            switch (linea) {
                // En este punto se inserta el cÃ³digo para las teclas
                // "a" y "d"
                case "w":
                nuevaFila = fila - 1;
                break;
                case "s":
                nuevaFila = fila + 1;
                break;
                case "d":
                nuevaCol= col + 1;
                break;
                case "a":
                nuevaCol=col - 1;
                break;
            }
            if (validarCasilla(nuevaFila, nuevaCol)) {
                Celda anterior = tablero.tablero[fila][col];
                Celda nueva = tablero.tablero[nuevaFila][nuevaCol];
                nueva.caracter = pacman;
                anterior.caracter = null;
                pacman.posicion = new Posicion(nuevaFila, nuevaCol);
                // Aqui hay que verificar si el jugador gana el juego
                // Esto es, si llega a una parte del laberinto
                // que es una salida
                turnos(contadorturno);
                pierdejuego(pacman.puntosVida);
                if(PierdeElJuego){
                    ganaElJuego=true;
                    tablero.dibujarTablero();
                    System.out.println("HAS MUERTO");

                    break;
                }
                if(tablero.tablero[nuevaFila][nuevaCol]==tablero.tablero[13][15]){
                    nueva.caracter = pacman;
                    anterior.caracter = null;
                    pacman.posicion = new Posicion(nuevaFila, nuevaCol);
                    ganaElJuego=true;

                    if(ganaElJuego) {
                        tablero.dibujarTablero();
                        System.out.println("Has ganado el juego, Â¡felicitaciones!");

                        break;
                    }

                    
                }
            }
            tablero.dibujarTablero();
            linea = in.nextLine();
        }

        
    }

    /**
     * En este metodo se debe chequear las siguientes condiciones:
     * (i) Que el usuario no se salga de las filas del tablero
     * (ii) Que el usuario no se salga de las columnas del tablero
     * (iii) Que la posición no sea un muro
     * (iv) Que no haya un caracter en esa posición
     * @param nuevaFila - es el # de la fila a la que se va mover el pacman
     * @param nuevaCol - es el # de la columna a la que se ca a mover el pacman
     * @return - retorna true si el movimento es valido y false de lo contrario
     */
    private boolean validarCasilla(int nuevaFila, int nuevaCol) {
        Celda y=tablero.tablero[0][0];
        Celda x=tablero.tablero[nuevaFila][nuevaCol];

        if((nuevaFila)<=0||(nuevaCol)<=0){
            contadorturno=contadorturno+1;
            return false;
        }
        else if((nuevaFila)>=14||(nuevaCol)>=16){
            contadorturno=contadorturno+1;
            return false;

        }
        else if((y.esMuro)==(x.esMuro)){
            contadorturno=contadorturno+1;
            return false;
        }
        else if(x.tieneArepita){

            contadorturno=contadorturno+1;
            return true;
        }
        else {
            contadorturno=contadorturno+1;
            return true;}

    }

    /**
     * es el metodo encargado de restarle vidas al pacman cada 10 turnos
     * @param contadorTurno - es la cantidad de turnos que han transcurrido en el juego
     * @return retorna la cantidad de vidas del pacman
     */
    private int turnos(int contadorTurno){
        if((contadorTurno %10)==0){
            vida=vida-1;
            pacman.puntosVida=vida;
        }
        return pacman.puntosVida;
    }

    /**
     * es el metodo que verifica si el jugador se ha quedado sin vidas
     * @param vida -variable que lleva la cantidad de vidas del pacman
     * @return retorna true si el jugador se ha quedado sin vidas y false en el caso de que se pueda seguir jugando
     */
    private boolean pierdejuego(int vida){
        if(vida==0){
        PierdeElJuego=true;
        }
        return PierdeElJuego;
    }
}
