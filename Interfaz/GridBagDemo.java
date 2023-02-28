package Interfaz;

// Java program to demonstrate GridBagLayout class.
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.*;
 
// class extends JFrame
public class GridBagDemo extends JFrame {
 
    GridBagDemo()
    {
 
        // Function to set title of JFrame.
        setTitle("GridBagLayoutDemo");
 
        // Creating Object of Jpanel class
        JPanel p = new JPanel();
 
        // set the layout
        p.setLayout(new GridBagLayout());
 
        // creates a constraints object
        GridBagConstraints c = new GridBagConstraints();
 
        // insets for all components//put spaces between components
        c.insets = new Insets(2, 2, 2, 2);
        //WHERE TO PLACE OUR COMPONENT
        // column 0
        c.gridx = 0;
 
        // row 0
        c.gridy = 0;
        //INTERNAL PADDING
        // increases components width by 10 pixels
        c.ipadx = 34;
 
        // increases components height by 50 pixels
        c.ipady = 35;
 
        // constraints passed in
        p.add(new JButton("Java Swing"), c);
 
        // column 1
        c.gridx = 1;
 
        // increases components width by 70 pixels
        c.ipadx = 34;
 
        // increases components height by 40 pixels
        c.ipady = 35;
 
        // constraints passed in
        p.add(new JButton("Layout"), c);
 
        // column 0
        c.gridx = 0;
 
        // row 2
        c.gridy = 1;
 
        // increases components width by 20 pixels
        c.ipadx = 20;
 
        // increases components height by 20 pixels
        c.ipady = 20;
 
        // constraints passed in
        p.add(new JButton("Manager"), c);
 
        // increases components width by 10 pixels
        c.ipadx = 10;
 
        // column 1
        c.gridx = 1;
 
        // constraints passed in
        p.add(new JButton("Demo"), c);
 
        // Creating Object of "wndcloser"
        // class of windowlistener
        WindowListener wndCloser = new WindowAdapter() {
 
            public void windowClosing(WindowEvent e)
            {
                System.out.println("GOOD BYES!");
                // exit the system
                System.exit(0);
            }
        };
 
        // add the actionwindowlistener
        addWindowListener(wndCloser);
 
        // add the content
        getContentPane().add(p);
 
        // Function to set size of JFrame.
        setSize(600, 400);
 
        // Function to set visibility of JFrame.
        setVisible(true);
    }
 
    // Main Method
    public static void main(String[] args)
    {
 
        // calling the constructor
        new GridBagDemo();
    }
}
