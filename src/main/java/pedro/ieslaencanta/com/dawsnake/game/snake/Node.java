/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawsnake.game.snake;

/**
 *
 * @author Mateo
 */
public class Node {

    private Node next;
    //referencia al siguiente
    private SnakePart snakepart;

    public Node() {
        this.next = null;
        this.snakepart = null;
    }

    public Node(SnakePart parte) {
        this.snakepart = parte;
        this.next = null;
    }

    public Node(Node next, SnakePart snakepart) {
        this.next = next;
        this.snakepart = snakepart;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    /**
     * @return the snakepart
     */
    public SnakePart getSnakepart() {
        return snakepart;
    }

    /**
     * @param snakepart the snakepart to set
     */
    public void setSnakepart(SnakePart snakepart) {
        this.snakepart = snakepart;
    }

    
}
