package datamining;

import modelling.*;
import java.util.*;

/*classe qui represente des bases de données transactionnelles */
public class BooleanDatabase {
    
    private Set<BooleanVariable> items;
    private List<Set<BooleanVariable>> transactions;

    public BooleanDatabase(Set<BooleanVariable> items){
        this.items = items;
        this.transactions = new ArrayList<>();
    }

    //ajouter une transaction
    public void add(Set<BooleanVariable> newItems){
        transactions.add(newItems);
    }

    public Set<BooleanVariable> getItems(){
        return items;
    }

    public List<Set<BooleanVariable>> getTransactions() {
        return transactions;
    }

    public String toString(){
        return "\n   items => " + items + "\n   tansactions => " +transactions;
    }
 
}
