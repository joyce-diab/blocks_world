package datamining;

import java.util.*;
import modelling.*;


public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {
    
    private BooleanDatabase database;

    public AbstractAssociationRuleMiner(BooleanDatabase database){
        this.database = database;
    }

    @Override
    public BooleanDatabase getDatabase() {
        return database;
    }
    
    /*renvoie la fréquence d'un item a partir d'un set d'itemsets */
    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets){
        
        for(Itemset itemset : itemsets){
            if(itemset.getItems().equals(items)){
                return itemset.getFrequency();
            }
        }

        throw new IllegalArgumentException("L'ensemble d'items ne figure dans dans l'ensemble d'itemset");
    }

    /*calcul la confiance d'une regle potentielle a partir d'une premise, conclusion et un set d'sitemset */
    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemsets){
        SortedSet<BooleanVariable> union = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        union.addAll(premise);
        union.addAll(conclusion);

        float freqUnion = frequency(union, itemsets); 
        float freqPremise =frequency(premise, itemsets);

        return freqUnion/freqPremise; //c la frequence de l'union entre la premisse te la conclusion sur la frequence de la premisse
    }
}
