package datamining;

import java.util.*;
import modelling.*;

/*classe qui permet de générer les regles d'une database selin une drequence minimale et une confiance minimale */
public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner{
    
    public BruteForceAssociationRuleMiner(BooleanDatabase database){
        super(database);
    }

    /*renvoie tous les premisses possibles de items 
     * plutot renvoie ’ensemble de tous ses sous-ensemblesde items
     * à l’exception de l’ensemble vide et de l’ensemble lui-même
    */

    /*l'algo est basé sur les nombres binaires. 
     * un ensemble d'items de taille n possede 2^n sous-ensembles differents
     * chaque sous ens peut etre representer par un nb binaire a n bits.
     * chaque bit reoresente un item avec 1 si l'item est inclus dans le sous-ensemble et 0 sinon
     * 
     * exemple si items est ABC et on a 101 cad on prend A et C et pas B donc on aura AC
    */
    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items){
        Set<Set<BooleanVariable>> allSubsets = new HashSet<>();
        int nbCombinaitions = 1 << items.size();
        
        List<BooleanVariable> itemList = new ArrayList<>(items);
        // on commence par 1 pur evieter l'ensemble vide i= 00..0 jusqu a nb-1 pour eviter i=11..1 qui repressente l'ensemble lui meme 
        for (int i = 1; i < nbCombinaitions - 1; i++) {
            Set<BooleanVariable> subset = new HashSet<>();

            //j represente chaque bit de i
            for (int j = 0; j < itemList.size(); j++) {
                //si le j-ième bit de i=1 alors i & 1<<j == 1 
                if ((i & (1 << j)) != 0) {
                    subset.add(itemList.get(j));
                }
            }
            allSubsets.add(subset);
        }
        
        return allSubsets;
    }

    /*methode qui permet de generer les regles valides et frequente d'un database */
    @Override
    public Set<AssociationRule> extract(float minFreq, float minConf) {
        Set<AssociationRule> rules = new HashSet<>();
        Apriori apriori = new Apriori(getDatabase());
        
        /*tous les items frequents */
        Set<Itemset> frequentItemsets = apriori.extract(minFreq);

        

        for (Itemset itemset : frequentItemsets) {
            /*soit items ABC */
            Set<BooleanVariable> items = itemset.getItems();
            float ItemsetFrequency = itemset.getFrequency();

            //allpremises= {{A},{C},{C},{AB},{AC},{BC}}
            Set<Set<BooleanVariable>> allPremises = allCandidatePremises(items);

            for(Set<BooleanVariable> premise : allPremises) {
                /*premise = {A} => conclusion = {ABC\A} = {BC}*/
                Set<BooleanVariable> conclusion = new HashSet<>(items);
                conclusion.removeAll(premise);

                if (!conclusion.isEmpty()) {
                    /*conf de A -> BC */
                    float confidence = confidence(premise, conclusion, frequentItemsets);
                    if (confidence >= minConf) { 
                        /*la regle est freq et valide */
                        rules.add(new AssociationRule(premise, conclusion, ItemsetFrequency, confidence));
                    }
                }
            }
        }
        return rules;
    }



}
