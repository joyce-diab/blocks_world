package blocksworld;

import java.util.*;


import modelling.*;
/**
 * classe qui represente un monde de blocs contraint en ajoutant des contraintes
 *  qui assure la croissance d'une pile
 * 
 * un bloc ne peut être positionné que sur un bloc de numéro plus petit (ou directement sur la table).
 */
public class BlocksWorldIncreasing extends BlocksWorldConstraints{
    
    protected Set<Constraint> increasingConstraints;

    public BlocksWorldIncreasing(int nbBlocs, int nbPiles){
        super(nbBlocs, nbPiles);
        this.increasingConstraints = new HashSet<>();
        addMoreConstraints();
    }

    private void addMoreConstraints(){
        for(Integer intB: variablesOn.keySet()){
            
            UnaryConstraint c = new UnaryConstraint(variablesOn.get(intB), new TreeSet<Object>(range(-nbPiles, intB)));
            increasingConstraints.add(c);
        }
    }

    public Set<Constraint> getIncreasingConstraints(){
        return increasingConstraints;
    }

    public Set<Constraint> getAllConstraints(){ //basic constraints du monde des blocs et un monde croissant 
        Set<Constraint> all = new HashSet<>();
        all.addAll(constraints);
        all.addAll(increasingConstraints);
        return all;
    }

    /**
     * renvoie true si une instance est satisfaite par tous les contraintes, false sinon
     */
    public boolean isSatisfied(Map<Variable,Object> instance){
        if(!super.isSatisfied(instance)){ //si ne verifie pas les contraintes basic
            return false;
        }

        for(Constraint c : getIncreasingConstraints()){
            if(instance.keySet().containsAll(c.getScope())){
                if(!c.isSatisfiedBy(instance)){
                    return false;
                }
            }
        }

        return true;
    }
}
