package Interfaz;

import StaticData.*;

//--------------------------------
import java.awt.*;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
// import javax.swing.UIManager;
// import javax.swing.text.*;
// import javax.swing.event.*;
// import java.text.*;
import java.util.HashMap;
import java.util.Locale;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
// import org.apache.commons.lang3.tuple.Triple; 
public class Exchange {
    final static boolean RIGHT_TO_LEFT = false;
    JComboBox<String> optionsLeft,optionsRight;
    JLabel rightLabel,leftLabel;
    private  JFormattedTextField amountField;
    private  JButton acept;
    private  JFrame frame;
    private  Coins sourceCoin=null, targetCoin=null;
    private  Double amount = null;
    private static HashMap<String,Coins> book = new HashMap<String,Coins>();
    
    public Exchange(){
        System.out.println("We are in Exchange class");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // UIManager.put("swing.boldMetal", Boolean.FALSE);
                setUp();
            }
        });
    }
    public  void start(){
        setUp();
    }
    public  void setUp(){
        fillBook();
        frame = new JFrame("Exchange");
        frame.setSize(800,400);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addComponents(frame.getContentPane());
        actions();
        frame.pack();
        frame.setVisible(true);
    }
    private  void fillBook(){
        for(int i = 0; i < CoinData.COIN_OPTIONS.length; ++i){
            book.put( CoinData.COIN_OPTIONS[i],Coins.values()[i]);
        }
    }
    public  void addComponents(Container pane){
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        pane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.ipady = 40; 
        c.ipadx = 20;
        
        /********************************************/
        rightLabel = new JLabel("Moneda que tiene:");
        leftLabel = new JLabel("Moneda que desea:");
        //optionsLeft and right are initialized
        //button acept initialized
        GridBagConstraints gcon = new GridBagConstraints();
        gcon.insets = new Insets(3, 3, 3, 3);
        gcon.fill = GridBagConstraints.HORIZONTAL;
        // gcon.weightx = 1;
        // gcon.gridwidth = 1;
        pane.add(rightLabel, gcon);

        gcon.gridwidth = GridBagConstraints.REMAINDER;
        pane.add(leftLabel, gcon);
        
        String country[] = CoinData.COIN_OPTIONS; //{"Dolar","Euros","Libras Esterlinas","Yen Japones","Won sul-coreano"};     
        optionsLeft = new JComboBox<String>(country);
        sourceCoin = Coins.DOLAR;
        gcon.gridwidth = 1;
        pane.add(optionsLeft, gcon);

        optionsRight = new JComboBox<String>(country);
        optionsRight.setSelectedItem(country[1]);
        targetCoin = Coins.EURO;
        gcon.gridwidth = GridBagConstraints.REMAINDER;
        pane.add(optionsRight, gcon);

        JLabel amountLabel = new JLabel("Amount of Money:");
        gcon.gridwidth = 1;
        gcon.weightx = 2;
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
        amountLabel.setLabelFor(amountField);

        gcon.gridwidth = GridBagConstraints.REMAINDER;
        gcon.weightx = 2;
        pane.add(amountField, gcon);
        
        acept = new JButton("OK");
        gcon.gridwidth = GridBagConstraints.REMAINDER;
        pane.add(acept,gcon);
    }

    public void actions(){
        acept.addActionListener(e -> {
            System.out.println((Double)amountField.getValue()+" click");
            this.amount = (Double)amountField.getValue();
            if(sourceCoin != null && targetCoin != null && this.amount != null && sourceCoin != targetCoin){
                // System.out.println(sourceCoin.toString()+" - "+targetCoin.toString());
                // System.out.println(CoinData.getTable()[sourceCoin.ordinal()][targetCoin.ordinal()]);
                Double multiplier = CoinData.getTable()[sourceCoin.ordinal()][targetCoin.ordinal()];

                Double converted = this.amount*multiplier;
                String message = String.format(Locale.FRANCE,"%,.2f %s is %,.4f %s",this.amount,sourceCoin.toString(),converted,targetCoin.toString());
                JOptionPane.showMessageDialog(frame, message, "Resultados del Conversor", JOptionPane.INFORMATION_MESSAGE);
            }else{
                String isNull = "Error";
                isNull = sourceCoin == null ? "Moneda que tiene"
                        : targetCoin == null ? "Moneda que desea" 
                        : this.amount == null ? "Monto"
                        : sourceCoin == targetCoin ? "No se puede cambiar a la misma moneda": "Error"; 
                
                String message = String.format("%s no especificado(a)",isNull == "$$"?"Error inesperado":isNull);
                JOptionPane.showMessageDialog(frame, message, "Error information", JOptionPane.ERROR_MESSAGE);
            }
        });
        ItemListener selectedOption = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedOption = (String) e.getItem();
                    // System.out.println("Selected option: " + selectedOption);
                    Object source = e.getSource();
                    if(source == optionsLeft){
                        sourceCoin = book.get(selectedOption);
                        // System.out.println("LEFT - "+sourceCoin.ordinal());
                    }
                    if(source == optionsRight){
                        targetCoin = book.get(selectedOption);
                        // System.out.println("RIGHT - "+targetCoin.ordinal());
                    }
                }                               
            }
        };
        optionsLeft.addItemListener(selectedOption);
        optionsRight.addItemListener(selectedOption);
    }
}
