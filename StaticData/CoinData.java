package StaticData;

public class CoinData {
    public static final String COIN_OPTIONS[] = {
        "Dolar","Euros","Libras Esterlinas",
        "Yen Japones","Won sul-coreano"
    };
    public static final double DOLAR_TO_EUROS  = 0.94d;
    public static final double DOLAR_TO_LIBRAS = 0.83d;
    public static final double DOLAR_TO_YEN    = 134.7d;
    public static final double DOLAR_TO_WON    = 1298.11d;
    public static final double EUROS_TO_LIBRA  = 0.88d;
    public static final double EUROS_TO_YEN    = 142.68d;
    public static final double EUROS_TO_WON    = 1375.59d;
    public static final double LIBRA_TO_YEN    = 161.80d;
    public static final double LIBRA_TO_WON    = 1559.54d;
    public static final double YEN_TO_WON    = 9.64d;
    
    public static final double conversor[][] = {        
        {
            DOLAR_TO_EUROS ,
            DOLAR_TO_LIBRAS,
            DOLAR_TO_YEN   ,
            DOLAR_TO_WON   
        },
        {
            EUROS_TO_LIBRA,
            EUROS_TO_YEN  ,
            EUROS_TO_WON  
        },
        {
            LIBRA_TO_YEN,
            LIBRA_TO_WON
        },
        {
            YEN_TO_WON
        }
    };
    
    private static Double table[][] = new Double[Coins.values().length][Coins.values().length];
    
    public static Double[][] getTable(){
        int size = Coins.values().length;
        int k = 0;
        for(int i = 0; i < size; ++i){
            for(int j = 0; j < size; ++j){
                if(j > i){
                    int coinA = Coins.values()[i].ordinal();
                    int coinB = Coins.values()[j].ordinal();
                    if(j-i == 1) {
                        k = coinB;
                    }
                    table[coinA][coinB] = conversor[i][j-k];
                    table[coinB][coinA] = 1/conversor[i][j-k];                    
                }
            }
        }
        // printTable();
        return CoinData.table;
    }
    public static void printTable(){
        System.out.println("Table mxm is: "+table.length);
        for(int i = 0; i < table.length; ++i){
            for(int j = 0; j < table.length; ++j){
                System.out.print(table[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        getTable();        
        printTable();
    }
}
