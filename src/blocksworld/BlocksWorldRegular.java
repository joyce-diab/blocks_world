package blocksworld;

import java.util.*;
import modelling.*;


/**
 * classe qui represente un monde de bloc contraint avec des contraintes qui assurent
 *  un ecart fixe entre tous les blocs d'une meme pile
 */
public class BlocksWorldRegular extends BlocksWorldConstraints{
    
    protected Set<Constraint> regularConstraints;

    public BlocksWorldRegular(int nbBlocs, int nbPiles){
        super(nbBlocs, nbPiles);
        this.regularConstraints = new HashSet<>();
        addMoreConstraints();
    }

    private void addMoreConstraints(){

        for(Integer intB1: variablesOn.keySet()){
            
            for(Integer intB2: variablesOn.keySet()){

                Set<Object> possibleValues = new TreeSet<>(range(-nbPiles,0));

                if(intB1 != intB2){
                    int possibleValue = 2*intB2 - intB1;
                    if(possibleValue < nbBlocs) possibleValues.add(possibleValue);

                    Implication impConstraint1 = new Implication(
                                            variablesOn.get(intB1), 
                                            Set.of(intB2),
                                            variablesOn.get(intB2), 
                                            possibleValues);
                    regularConstraints.add(impConstraint1);

                }
                
            }
        }
    }

    public Set<Constraint> getRegulConstraints(){
        return regularConstraints;
    }

    public Set<Constraint> getAllConstraints(){
        Set<Constraint> all = new HashSet<>();
        all.addAll(constraints);
        all.addAll(regularConstraints);
        return all;
    }

    public boolean isSatisfied(Map<Variable,Object> instance){
        if(!super.isSatisfied(instance)){
            return false;
        }

        for(Constraint c : getRegulConstraints()){
            if(instance.keySet().containsAll(c.getScope())){
                if(!c.isSatisfiedBy(instance)){
                    return false;
                }
            }
        }

        return true;
    }
}
