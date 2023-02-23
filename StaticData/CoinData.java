package StaticData;

public class CoinData {
    public static final String COIN_OPTIONS[] = {"Dolar","Euros","Libras Esterlinas","Yen Japones","Won sul-coreano"};
    public static final double DOLAR_TO_EUROS  = 1d;
    public static final double DOLAR_TO_LIBRAS = 2d;
    public static final double DOLAR_TO_YEN    = 3d;
    public static final double DOLAR_TO_WON    = 4d;
    public static final double EUROS_TO_LIBRA  = 5d;
    public static final double EUROS_TO_YEN    = 6d;
    public static final double EUROS_TO_WON    = 7d;
    public static final double LIBRA_TO_YEN    = 8d;
    public static final double LIBRA_TO_WON    = 9d;
    public static final double YEN_TO_WON    = 10d;
    
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
    
    public static Double table[][] = new Double[Coins.values().length][Coins.values().length];
    
    public static void getTable(){
        int size = Coins.values().length;
        int k = 0;
        for(int i = 0; i < size; ++i){
            for(int j = 0; j < size; ++j){
                if(j > i){
                    int coinA = Coins.values()[i].ordinal();
                    int coinB = Coins.values()[j].ordinal();
                    if(j-i == 1) k = coinB;
                    // System.out.println(coinA+" | | "+(j-k));
                    table[coinA][coinB] = conversor[i][j-coinB];
                    table[coinB][coinA] = 1/conversor[i][j-coinB];                    
                }
            }
            
        }

    }
    public static void printTable(){
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
