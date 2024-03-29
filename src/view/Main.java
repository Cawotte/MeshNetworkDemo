package view;


import model.MeshNetwork;
import model.MeshNetworkDisruptor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Main extends JFrame implements MouseListener, MouseMotionListener {

    // Define constants
    static final int CANVAS_WIDTH  = 960;
    static final int CANVAS_HEIGHT = 600;
    static final boolean DRAW_LINKS = true;
    static final int AVERAGE_DIST_BETWEEN_NODES = 60;

    private GraphCanvas canvas;
    private MeshNetwork meshNetwork;
    private MeshNetworkDisruptor meshNetworkDisruptor;

    public Main() {

        //DATA
        this.meshNetwork = new MeshNetwork(CANVAS_WIDTH, CANVAS_HEIGHT, AVERAGE_DIST_BETWEEN_NODES);

        this.meshNetworkDisruptor = new MeshNetworkDisruptor(meshNetwork);
        this.meshNetworkDisruptor.notifyObserver = new Runnable() {
            @Override
            public void run() {
                repaintGraph();
            }
        };
        this.meshNetworkDisruptor.startRecurrentDisabling();

        canvas = new GraphCanvas(meshNetwork, CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.drawLinks = DRAW_LINKS;
        // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Set the Test.Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.add(canvas);
        // or "setContentPane(canvas);"

        addMouseListener( this );
        addMouseMotionListener( this );

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("MeshNetworkDemo");  // "super" JFrame sets the title
        setVisible(true);    // "super" JFrame show
    }


    private void repaintGraph() {
        this.repaint();
    }

    public static void main(String[] args) {
        // Run the GUI codes on the Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main(); // Let the constructor do the job
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        canvas.setPosPointer(mouseEvent.getX(), mouseEvent.getY());
        this.repaint();
    }
}
