package Interfaz;

import StaticData.*;

//--------------------------------
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.*;
import javax.swing.event.*;
import java.text.*;
import java.util.HashMap;
import java.util.Locale;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
public class Exchange {
    final static boolean RIGHT_TO_LEFT = false;
    JComboBox<String> optionsLeft,optionsRight;

    private JFormattedTextField amountField;
    private JButton acept;
    private JFrame frame;
    private Coins sourceCoin=null, targetCoin=null;
    private Double amount = null;
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
    public void setUp(){
        fillBook();
        frame = new JFrame("Exchange");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponents(frame.getContentPane());
        actions();
        frame.pack();
        frame.setVisible(true);
    }
    private static void fillBook(){
        for(int i = 0; i < CoinData.COIN_OPTIONS.length; ++i){
            book.put( CoinData.COIN_OPTIONS[i],Coins.values()[i]);
        }
    }
    public void addComponents(Container pane){
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipady = 40; 
        c.ipadx = 20;
        
        String country[] = CoinData.COIN_OPTIONS; //{"Dolar","Euros","Libras Esterlinas","Yen Japones","Won sul-coreano"};     
        optionsLeft = new JComboBox<String>(country);
        sourceCoin = Coins.DOLAR;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        pane.add(optionsLeft,c);

        optionsRight = new JComboBox<String>(country);
        optionsRight.setSelectedItem(country[1]);
        targetCoin = Coins.EURO;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 2;
        pane.add(optionsRight,c);

        JLabel amountLabel = new JLabel("Amount of Money:");
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
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.insets = new Insets(10,0,0,0);  //top padding
        pane.add(amountLabel,c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 2;
        pane.add(amountField,c);

        acept = new JButton("OK");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        pane.add(acept,c);
    }

    public void actions(){
        acept.addActionListener(e -> {
            System.out.println((Double)amountField.getValue()+" click");
            this.amount = (Double)amountField.getValue();
            if(sourceCoin != null && targetCoin != null && this.amount != null && sourceCoin != targetCoin){
                System.out.println(sourceCoin.toString()+" - "+targetCoin.toString());
                System.out.println(CoinData.getTable()[sourceCoin.ordinal()][targetCoin.ordinal()]);
                Double multiplier = CoinData.getTable()[sourceCoin.ordinal()][targetCoin.ordinal()];

                Double converted = this.amount*multiplier;
                String message = String.format(Locale.FRANCE,"%,.2f %s is %,.4f %s",this.amount,sourceCoin.toString(),converted,targetCoin.toString());
                JOptionPane.showMessageDialog(frame, message, "Resultados del Conversor", JOptionPane.INFORMATION_MESSAGE);
            }else{
                
                String isNull = "Error";
                isNull = sourceCoin == null ? "Moneda que tiene"
                        : targetCoin == null ? "Moneda que desea" : this.amount == null ? "Monto": sourceCoin == targetCoin ? "No se puede cambiar a la misma moneda": "Error"; 
                
                String message = String.format("%s no especificado(a)",isNull == "$$"?"Error inesperado":isNull);
                JOptionPane.showMessageDialog(frame, message, "Error information", JOptionPane.ERROR_MESSAGE);
            }
        });
        ItemListener selectedOption = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedOption = (String) e.getItem();
                    System.out.println("Selected option: " + selectedOption);
                    Object source = e.getSource();
                    if(source == optionsLeft){
                        sourceCoin = book.get(selectedOption);
                        System.out.println("LEFT - "+sourceCoin.ordinal());
                    }
                    if(source == optionsRight){
                        targetCoin = book.get(selectedOption);
                        System.out.println("RIGHT - "+targetCoin.ordinal());
                    }
                }                               
            }
        };
        optionsLeft.addItemListener(selectedOption);
        optionsRight.addItemListener(selectedOption);
    }
}
