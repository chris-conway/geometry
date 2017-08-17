package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Chris on 17/05/2017.
 */
public class InputListener implements KeyListener{

    private Controller cont;

    public InputListener(Controller c) {
        cont = c;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("keypress");

        if(e.getKeyCode() == (KeyEvent.VK_SPACE)){
            cont.getAnimationTimer().stopTimer();
        }else if(e.getKeyCode() == KeyEvent.VK_UP){
            cont.getPoints().addPoint();
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            cont.getPoints().removePoint();
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            cont.getPoints().rotatePoints(false);
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            cont.getPoints().rotatePoints(true);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == (KeyEvent.VK_SPACE)){
            cont.getAnimationTimer().startTimer();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
