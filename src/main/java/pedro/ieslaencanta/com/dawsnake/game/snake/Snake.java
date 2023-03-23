/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawsnake.game.snake;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pedro.ieslaencanta.com.dawsnake.game.Coordenada;
import pedro.ieslaencanta.com.dawsnake.game.Direction;
import pedro.ieslaencanta.com.dawsnake.game.Game;
import pedro.ieslaencanta.com.dawsnake.game.IDrawable;
import pedro.ieslaencanta.com.dawsnake.game.Size;

/**
 *
 * @author Pedro
 */
public class Snake implements IDrawable {

    public enum State {
        OK,
        COLISION
    }
    private boolean conpowerup;
    private State state;
    private Direction direction;
    private Node head;
    //MODIFICAR ESTO A PRIVADO LUEGO ARREGLANDOLO
    public static Size part_size;

    public Snake() {
        this.state = State.OK;
        this.direction = Direction.LEFT;
        this.head = new Node(new SnakePart(new Coordenada(10, 10), Color.BLUE, part_size));
        this.conpowerup = false;
    }

    public void addpart() {
        int col, row;
        Node tempo = new Node();
        Node sitiodondeinsertar = this.getHead();
        while (sitiodondeinsertar.getNext() != null) {
            sitiodondeinsertar = sitiodondeinsertar.getNext();
        }
        row = sitiodondeinsertar.getSnakepart().getPosicion().getX();
        col = sitiodondeinsertar.getSnakepart().getPosicion().getY();
        if (this.getHead().getNext() == null) {
            switch (this.getDirection()) {
                case LEFT:
                    row++;
                    break;
                case RIGHT:
                    row--;
                    break;
                case UP:
                    col++;
                    break;
                case DOWN:
                    row--;
                    break;
            }
            System.out.println("Añado en la cabeza");
        } else {
            System.out.println("Añado en otro sitio que no es la cabeza");
        }

        //Preguntar a Pedro
        //Opción decente -> o con un par de switches comprobando cual es el penúltimo y según la diferencia de posiciones
        //con el último añadir así el nuevo último.
        tempo.setSnakepart(new SnakePart(new Coordenada(row, col),
                //Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255))
                this.head.getSnakepart().getColor(),
                part_size));
        sitiodondeinsertar.setNext(tempo);
    }

    public State DetectarColisionBordes(Size s) {
        boolean caso1, caso2, caso3, caso4;
        caso1 = this.getHead().getSnakepart().getPosicion().getX() < 0;
        caso2 = this.getHead().getSnakepart().getPosicion().getY() < 0;
        caso3 = this.getHead().getSnakepart().getPosicion().getX() >= s.getWidth() - 4;
        caso4 = this.getHead().getSnakepart().getPosicion().getY() >= s.getHeight() - 4;
        if (caso1
                || caso2
                || caso3
                || caso4) {
            if (this.isConpowerup() == true) {
                if (caso1) {
                    this.head.getSnakepart().setPosicion(new Coordenada(s.getWidth() - 4, this.head.getSnakepart().getPosicion().getY()));
                } else if (caso2) {
                    this.head.getSnakepart().setPosicion(new Coordenada(this.head.getSnakepart().getPosicion().getX(), s.getHeight() - 4));
                } else if (caso3) {
                    this.head.getSnakepart().setPosicion(new Coordenada(0, this.head.getSnakepart().getPosicion().getY()));
                } else if (caso4) {
                    this.head.getSnakepart().setPosicion(new Coordenada(this.head.getSnakepart().getPosicion().getX(), 0));
                }
            } else {
                System.out.println("Colisión Detectada");
                this.state = State.COLISION;
            }
        }
        return this.state;
    }

    public void Powerup() {
        Node tempo = this.head;
        Color color = Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        while (tempo != null) {
            tempo.getSnakepart().setColor(color);
            tempo = tempo.getNext();
        }
    }

    public State DetectarColisionSerpiente(Size s) {
        Node tempo = this.head;
        Coordenada comparación = this.head.getSnakepart().getPosicion();
        while (tempo.getNext() != null) {
            tempo = tempo.getNext();
            if (tempo.getSnakepart().getPosicion().compareTo(comparación) == 0) {
                this.state = State.COLISION;
            }
        }
        return this.state;
    }

    public void EliminarCola() {
        Node tempo = this.head;
        if (tempo.getNext() != null) {
            tempo.setNext(null);
        }
    }

    /**
     * vuelve la serpiente
     *
     * @param s
     * @return devuelve el estado de la serpiente correcto,toca borde, se toca a
     * ella misma
     */
    public State move(Size s) {
        Node tempo = this.getHead();
        Coordenada coordtempo = tempo.getSnakepart().getPosicion().clone();
        switch (this.getDirection()) {
            case LEFT:
                this.getHead().getSnakepart().getPosicion().setX(this.getHead().getSnakepart().getPosicion().getX() - 1);
                break;
            case RIGHT:
                this.getHead().getSnakepart().getPosicion().setX(this.getHead().getSnakepart().getPosicion().getX() + 1);
                break;
            case UP:
                this.getHead().getSnakepart().getPosicion().setY(this.getHead().getSnakepart().getPosicion().getY() - 1);
                break;
            case DOWN:
                this.getHead().getSnakepart().getPosicion().setY(this.getHead().getSnakepart().getPosicion().getY() + 1);
                break;
        }
        while (tempo.getNext() != null) {
            Coordenada tempocambio = tempo.getNext().getSnakepart().getPosicion();
            tempo.getNext().getSnakepart().setPosicion(coordtempo);
            tempo = tempo.getNext();
            coordtempo = tempocambio;
        }

        return this.state;
    }

    public void setDirection(Direction d) {
        this.direction = d;

    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    public int coordenadasegundireccion() {
        int vuelta;
        vuelta = switch (this.getDirection()) {
            case LEFT ->
                15;
            case RIGHT ->
                0;
            case UP ->
                43;
            case DOWN ->
                29;
            default ->
                0;
        };
        return vuelta;
    }

    @Override
    public void draw(GraphicsContext gc) {
        Node tempo = this.head;
        int tamano=16;
        if (this.getDirection()==Direction.UP||this.getDirection()==Direction.DOWN) {
            tamano=12;
        }
        this.head.getSnakepart().drawHead(gc,this.coordenadasegundireccion(),tamano);
        while (tempo.getNext() != null) {
            tempo = tempo.getNext();
            gc.setFill(tempo.getSnakepart().getColor());
            tempo.getSnakepart().draw(gc);
        }
    }

    /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @return the part_size
     */
    public Size getPart_size() {
        return part_size;
    }

    /**
     * @param part_size the part_size to set
     */
    public void setPart_size(Size part_size) {
        this.part_size = part_size;
    }

    /**
     * @return the head
     */
    public Node getHead() {
        return head;
    }

    /**
     * @param head the head to set
     */
    public void setHead(Node head) {
        this.head = head;
    }

    /**
     * @return the conpowerup
     */
    public boolean isConpowerup() {
        return conpowerup;
    }

    /**
     * @param conpowerup the conpowerup to set
     */
    public void setConpowerup(boolean conpowerup) {
        this.conpowerup = conpowerup;
    }

}
