package datamining;

import java.util.*;
import modelling.*;

/*classe qui représente des extracteurs fonctionnant sur le principe de l’algorithme « apriori » */
public class Apriori extends AbstractItemsetMiner{
    
    public Apriori (BooleanDatabase database){
        super(database);
    }

    /*renvoie tous les itemsets singleton(un item + frequence) dont la frequence est supérieur à minFrequency */
    public Set<Itemset> frequentSingletons(float minFrequency){
        Set<BooleanVariable> items = getDatabase().getItems();

        Set<Itemset> frequentSingleitems = new HashSet<>();

        /*pour chaque item des items de la base */
        for (BooleanVariable item : items) {
            
            /*on cree un set qui contient cet item */
            Set<BooleanVariable> singleItemset = new HashSet<>();
            singleItemset.add(item);

            
            float freq = frequency(singleItemset); //on calcule sa frequence
            
            if( freq >= minFrequency){
                frequentSingleitems.add(new Itemset(singleItemset, freq));
            }
    
        }
        return frequentSingleitems;
    }

    /* méthode statique qui prend en arguments 
     * deux ensembles (triés) d’items
     * renvoie un ensemble (trié) d’items obtenu en les combinant
     * à 3 conditions: 
     *    - les deux ensembles aient la même taille k,
     *    - les deux ensembles aient les mêmes k − 1 premiers items,
     *    - les deux ensembles aient des kes items différents.
     * renvoie null sinon.
     */

    /*
     * si on a par exemple ABCD et ABCE => ABCDE
     *                     ABC et ABCE => null
     *                     ABCD et ABDE => null
     *                     ABCD et ABCD => null
     */
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> set1, SortedSet<BooleanVariable> set2){
        //taille differente ou ensemble vide ou meme dernier element
        if(set1.size() != set2.size() || set1.size() == 0 || set1.last() == set2.last() ){
            return null;
        }

        //k-1 elts differents (ex: ABCD et ABDE)
            /*ABCD.headSet(ABCD.last) = ABCD.headSet(D) = ABC*/
        // ABC != ABD => null
        if ( ! set1.headSet(set1.last()).equals(set2.headSet(set2.last()))){
            return null;
        }
        
        //on k-1 elt communs + dernier elt de chaque ensemble
        SortedSet<BooleanVariable> combinedSet = new TreeSet<>(set1);
        combinedSet.add(set2.last());
        
        return combinedSet;
    }


    /*
     * methode statique qui se base sur la propriété d’antimonotonie de la fréquence 
     * assure  que les tous les sous-ensembles d'un ensemble donné sont fréquents.
     */
    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> frequentItems){
        SortedSet<BooleanVariable> subset = new TreeSet<>(COMPARATOR);
        subset.addAll(items);

        for(BooleanVariable v : items){ 
            subset.remove(v); //on cree un sous-ensemble d'items privé de v
            
            if(! frequentItems.contains(subset)){ 
                return false; //le sous-ensemble n'est pas fréquent
            }

            subset.add(v);
        }
            
        return true;
    }



    /*Les fontions qu'on va utiliser dans extract:
     * frequentSingletons(freq): singletons qui st freq (taille d'items k=1)
     * combine(k,k) -> (k+1): on génère un elt de taille  k+1 a partir de 2 elts de taille k
     * allSubsetsFrequent: (k+1, {k}) -> true si k+1 peut potentiellement être frequent
     */

    /*methode qui permet d'exrtaire les itemset fréquents selon une frequence minimal fourni en argumenst*/
    @Override
    public Set<Itemset> extract(float minFrequency) {
        Set<Itemset> frequentItemsets = new HashSet<>(); // (items, frequence)
        frequentItemsets.addAll(frequentSingletons(minFrequency)); //tous les freq de taille k=1
        
        List<SortedSet<BooleanVariable>> kSizeItems = new ArrayList<>(); //tous les items de taille k

        /*ajouter les items singletons fréquents a kSizeItems */
        for (Itemset singleton : frequentItemsets) {
            SortedSet<BooleanVariable> items = new TreeSet<>(COMPARATOR);
            items.addAll(singleton.getItems());
            kSizeItems.add(items);
        }

        List<SortedSet<BooleanVariable>> kplus1; //elts frequents de taille k+1
        
        
        //tant qu'on peut générer des items kplus1 a partir de k
        while(!kSizeItems.isEmpty()){
            //a partir de k on genere les elemnts frequents de taille k+1
            kplus1= generateKplus1 (minFrequency, kSizeItems, frequentItemsets);
            kSizeItems = kplus1;
        }
        
        return frequentItemsets;
        
    }

    /*
     * permet d'extraire les items de taille k+1 qui sont frequents selon les arguments fournis:
     * - une frequence min
     * - une liste d'items de taille k qui sont frequents 
     * - la liste de tous les itemset frequents pour la completer a fur et a mesure
     * 
     */
    public List<SortedSet<BooleanVariable>> generateKplus1 (float minFrequency, List<SortedSet<BooleanVariable>> kSizeItems,Set<Itemset> frequentItemsets ){

        List<SortedSet<BooleanVariable>> kplus1 = new ArrayList<>();
        List<SortedSet<BooleanVariable>> kCandidats = new ArrayList<>(kSizeItems); //items fréquents pas encore explorés

        /*pour chaque item1 de taille k, on génère tous les couples (item1,item2) possibles
         * on les combine pour générer un item de taille k+1 si possible
         * on verifie si le nouvel item peut etre frequent avec allSubsetsFrequent
         * s'il est candidat, on calcul sa frequence pour verifier du'il est >= min freq
         * si oui on l'ajoure a la liste des items frequents k+1 et on cree l'itemset et l'ajout a frequentItemsets
        */
        for(SortedSet<BooleanVariable> item1 : kSizeItems){
            
            kCandidats.remove(item1); /*on enleve l'item qu'on explore */
            
            for(SortedSet<BooleanVariable> item2 : kCandidats){ /*pour chaque item pas encore explorer */
                SortedSet<BooleanVariable> b = combine(item1,item2);
                if( b != null && allSubsetsFrequent(b, kSizeItems)){
                    
                    float frqB = frequency(b);
                    if(frqB >= minFrequency){
                        kplus1.add(b);
                        frequentItemsets.add(new Itemset(b, frqB));
                    }
                }
                
            }
        }


        return kplus1;
    }

}
