package datamining;

import java.util.*;
import modelling.*;

public abstract class AbstractItemsetMiner implements ItemsetMiner {
    
    private BooleanDatabase database;

    /* permettant de comparer les item selon leurs noms*/
    public static final Comparator<BooleanVariable> COMPARATOR = 
        (var1, var2) -> var1.getName().compareTo(var2.getName());

    

    public AbstractItemsetMiner(BooleanDatabase database) {
        this.database = database;
    }

    @Override
    public BooleanDatabase getDatabase() {
        return database;
    }
    

    /*
     * prend en argument un set d'items
     * retourn la fréquence de l'ensemble d’items dans la base  (database)*/
    public float frequency(Set<BooleanVariable> items){
        List<Set<BooleanVariable>> transactions = database.getTransactions();
        float nbTransactions = transactions.size(); //nb total de transactions

        float freq = 0; //nb total des transactions qui contiennent ce set

        for(Set<BooleanVariable> transaction : transactions){
            /*si la transaction contient tout l'ensemble des items */
            if(transaction.containsAll(items)){
                freq++;
            }
        }
        return freq/nbTransactions;
    }
}
