package datamining;

import java.util.*;
import modelling.*;

/*classe qui represente un set d'items avec sa fréquence entendue entre 0,0 pour un motif
non supporté, et 1,0 pour un motif supporté par toutes les transactions*/

public class Itemset {
    
    private Set<BooleanVariable> items;
    private float frequency;
    
    public Itemset(Set<BooleanVariable> items, float frequency) {
        this.items = items;
        this.frequency = frequency; //entre 0 et 1 avec 
    }

    public Set<BooleanVariable> getItems() {
        return items;
    }

    public float getFrequency() {
        return frequency;
    }

    @Override
    public String toString(){
        return "{items: "+items + ", freq: "+frequency+"}";
    }
    
}
