package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Chris on 15/05/2017.
 */
public class ViewFrame extends JFrame {

    public ViewFrame() throws HeadlessException {
        super();
        Dimension dim = new Dimension(1000,1000);
        this.setMaximumSize(dim);
        this.setPreferredSize(dim);
        this.setMinimumSize(dim);
        this.setBackground(Color.white);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
