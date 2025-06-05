package datamining;

import java.util.*;


public interface AssociationRuleMiner {
    public BooleanDatabase getDatabase();
    public Set<AssociationRule> extract(float minFrequency, float minconfidence); //extraire les regles selon une frequence et confiance minimaux
}
