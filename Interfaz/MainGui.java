package Interfaz;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
public class MainGui {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = true;
    private static JComboBox<String> mainOptions;
    private static String selectedOption;
    private static JButton button;
        
    private static final String[] options = {
        "Convertir bidireccional","Convertir unidireccional"
    };
    // public MainGui(){
    //     System.out.println("I am from MainGui.java!");
    //     System.out.println("My MainGui interface");
    //     javax.swing.SwingUtilities.invokeLater(new Runnable() {
    //         public void run() {
    //             createAndShowGUI();
    //         }
    //     });
    // }
    
    public static void start(){
        System.out.println("I am from MainGui.java!");
        System.out.println("My MainGui interface");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void addComponentsToPane(Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }


        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        mainOptions = new JComboBox<String>(options);
        selectedOption = options[0];
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        pane.add(mainOptions,c);
        
        button = new JButton("Aceptar");
        if (shouldWeightX) {
            c.weightx = 0.5;
        }
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        pane.add(button, c);

        // button = new JButton("Button 2");
        // c.fill = GridBagConstraints.HORIZONTAL;
        // c.weightx = 0.5;
        // c.gridx = 1;
        // c.gridy = 0;
        // pane.add(button, c);

        // button = new JButton("Button 3");
        // c.fill = GridBagConstraints.HORIZONTAL;
        // c.weightx = 0.5;
        // c.gridx = 2;
        // c.gridy = 0;
        // pane.add(button, c);

        // button = new JButton("Long-Named Button 4");
        // c.fill = GridBagConstraints.HORIZONTAL;
        // c.ipady = 40;      //make this component tall
        // c.weightx = 0.0;
        // c.gridwidth = 3;
        // c.gridx = 0;
        // c.gridy = 1;
        // pane.add(button, c);

        // button = new JButton("5");
        // c.fill = GridBagConstraints.HORIZONTAL;
        // c.ipady = 0;       //reset to default
        // c.weighty = 1.0;   //request any extra vertical space
        // c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        // c.insets = new Insets(10,0,0,0);  //top padding
        // c.gridx = 1;       //aligned with button 2
        // c.gridwidth = 2;   //2 columns wide
        // c.gridy = 2;       //third row
        // pane.add(button, c);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void addActions(){
        button.addActionListener( e -> {
            switch(selectedOption){
                case "Convertir bidireccional":
                    Exchange a = new Exchange();
                break;
                case "Convertir unidireccional":
                    UniExchange b = new UniExchange();
                break;                        
            }
        });
        ItemListener onChangeOption = new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedOption = (String)e.getItem();
                }

                System.out.println("Selected option: " + selectedOption);
            }
        };
        mainOptions.addItemListener(onChangeOption);
    }
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
        addActions();
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        start();
    }
}

