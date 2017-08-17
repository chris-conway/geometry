package controller;

import model.DoublePoint;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AnimationTimer implements ActionListener {

    private Controller cont;
    private Timer t;
    private int addNewPointCounter;

    public AnimationTimer(Controller c) {
        this.cont = c;
        this.t = new Timer(1000, this);
        addNewPointCounter = 0;
    }

    public void startTimer(){
        this.t.start();
    }

    public void stopTimer() {
        this.t.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        cont.repaintPanel();
    }
}
