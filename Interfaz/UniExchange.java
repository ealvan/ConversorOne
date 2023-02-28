package Interfaz;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.event.*;
import StaticData.CoinData;
import StaticData.Coins;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class UniExchange {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    final static String[] options = Arrays.copyOf(CoinData.COIN_OPTIONS, CoinData.COIN_OPTIONS.length);
    
    private static JComboBox<String> mainOptions;
    private static  JFormattedTextField amountField;
    private static JButton pushOk;
    private static HashMap<String,Coins> options_to_coin;
    public UniExchange(){
        System.out.println("I am from Gui.java!");
        System.out.println("My GUI interface");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
                // WindowListener wndCloser = new WindowAdapter() {
                //     public void windowClosing(WindowEvent e)
                //     {
                //         System.out.println("GOOD BYES!");
                //         // exit the system
                //         System.exit(WindowConstants.DISPOSE_ON_CLOSE);
                //     }
                // };
            }
        });
        
    }
    
    public static void start(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        
    }
    private static void fillOptions(){
        options_to_coin = new HashMap<String, Coins>();

        Coins selectedCoin = Coins.values()[0];
        constantCoin = selectedCoin;
        options[0] = "---Seleccionar tipo de cambio---";
        options_to_coin.put(options[0], Coins.values()[0]);
        for(int i = 1; i < options.length; ++i){
            // Coins opt = options[i];
            options[i] = "De "+selectedCoin.toString()+" a "+options[i];
            options_to_coin.put(options[i], Coins.values()[i]);
        }
    }
    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new GridBagLayout());
        // pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        GridBagConstraints gcon = new GridBagConstraints();
        gcon.insets = new Insets(2, 2, 2, 2);
        // gcon.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel labelOptions = new JLabel("Elija el tipo de cambio:");
        gcon.gridx = 0;
        gcon.gridy = 0;

        // gcon.gridwidth = GridBagConstraints.REMAINDER;
        pane.add(labelOptions,gcon);

        mainOptions = new JComboBox<String>(options);
        gcon.gridx = 1;
        // gcon.weightx = 2;
        // gcon.gridwidth = GridBagConstraints.REMAINDER;
        pane.add(mainOptions, gcon);

        JLabel amountLabel = new JLabel("Ingrese la cantidad: ");
        gcon.gridx=0;
        gcon.gridy=1;
        // gcon.gridwidth = 2;
        pane.add(amountLabel, gcon);


        DecimalFormat amountFormat = new DecimalFormat("#.##");// DecimalFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(amountFormat);
        // amountFormat.setMaximumFractionDigits(2);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0.0);
        formatter.setMaximum(1000000.0);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        amountField = new JFormattedTextField(formatter);
        amountField.setPreferredSize(new Dimension(200,20));
        // gcon.gridwidth = GridBagConstraints.REMAINDER;
        gcon.gridx = 1;
        gcon.gridy = 1;
        // gcon.gridy = 1;
        pane.add(amountField, gcon);

        pushOk = new JButton("Aceptar");
        gcon.gridx = 0;
        gcon.gridy = 2;
        gcon.gridwidth = 2;
        pane.add(pushOk,gcon);
    }
    private static Double amount = null;
    private static String optionSelected = null;
    private static Coins selected = null;
    private static Coins constantCoin = null;
    public static void addActions(){
        optionSelected = options[0];
        pushOk.addActionListener(e -> {
            System.out.println((Double)amountField.getValue()+" click");
            UniExchange.amount = (Double)amountField.getValue();
 
            if(!(amount == null || optionSelected == null || selected == null || constantCoin == null || options[0] == optionSelected)){
                // System.out.println(constantCoin.ordinal()+" - "+selected.ordinal()+" - " + CoinData.getTable()[constantCoin.ordinal()][selected.ordinal()]);
                Double result = CoinData.getTable()[constantCoin.ordinal()][selected.ordinal()]*amount;
                String message = String.format("%,.2f %s son %,.2f %s", amount, constantCoin.toString(), result, selected.toString());
                JOptionPane.showMessageDialog(frame, message, "Resultado de Conversion", JOptionPane.INFORMATION_MESSAGE);
            }else{
                String nullMessage = 
                amount == null ? "Cantidad de dinero a convertir"
                : optionSelected == null ? "Tipo de cambio": selected == null ? "Moneda a convertir"
                : constantCoin == null ? "Moneda que tiene " 
                : options[0] == optionSelected ? "Tipo de dinero a convertir" 
                : "Error inesperado";
                nullMessage += " no especificado";
                JOptionPane.showMessageDialog(frame, nullMessage, "Error", JOptionPane.ERROR_MESSAGE);
                // System.out.println("You don't have a requirement");
            }
        });
        
        mainOptions.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String selectedOption = (String) e.getItem();
                    System.out.println("Selected option: " + selectedOption);
                    selected = options_to_coin.get(selectedOption);
                    optionSelected = selectedOption;
                }
            }
        });   
    }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static JFrame frame;
    private static void createAndShowGUI() {
        fillOptions();
        //Create and set up the window.
        frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

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
