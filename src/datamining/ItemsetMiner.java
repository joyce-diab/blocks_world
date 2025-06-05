package datamining;

import java.util.*;;

public interface ItemsetMiner {
    public BooleanDatabase getDatabase();
    public Set<Itemset> extract(float minFrequency); //extraction des itelset frequents
}
