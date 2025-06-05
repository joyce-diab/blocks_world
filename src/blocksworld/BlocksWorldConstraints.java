package blocksworld;

import java.util.*;
import modelling.*;

/*
 * classe qui represente un monde de bloc avec des contraintes de base
 */
public class BlocksWorldConstraints extends BlocksWorld{
    
    protected Set<Constraint> constraints;

    public BlocksWorldConstraints(int nbBlocs, int nbPiles){
        super(nbBlocs, nbPiles);
        this.constraints = new HashSet<>();
        initialiseConstraints();
    }

    private void initialiseConstraints(){
        
        Set<Integer> visitedVariablesOn = new HashSet<>(); //variables traités pour eviter les regles de differences doubles
        for(Integer intB1 : variablesOn.keySet()){
             

            for(Integer intB2 : variablesOn.keySet()){

                if(intB1 != intB2){
                    if(! visitedVariablesOn.contains(intB2)){// {onb1 != onb2} est equivalent a {onb2 != onb1}
                        //2 variables onB ne peuvent pas avoir la meme position
                        DifferenceConstraint diffConstraint = new DifferenceConstraint(variablesOn.get(intB1), variablesOn.get(intB2));
                        constraints.add(diffConstraint);
                    }
                    
                    //si onB1 = b2 alors fixedB2 true
                    Implication impConstraint1 = new Implication(
                                                variablesOn.get(intB1), 
                                                Set.of(intB2),
                                                variablesFixed.get(intB2), 
                                                Set.of(true));

                    constraints.add(impConstraint1);
                }
                
            }
            visitedVariablesOn.add(intB1); //tous les differences contraintes avec intB1 on été générer

            
            for(Integer numPile : variablesFree.keySet()){

                //si onB1 = -(p+1) alors freep true
                Implication impConstraint3 = new Implication(
                                            variablesOn.get(intB1), 
                                            Set.of(-(numPile+1)),
                                            variablesFree.get(numPile), 
                                            Set.of(false));
                    
                constraints.add(impConstraint3);
            }
        }
        
    }

    public Set<Constraint> getBasicConstraints(){
        return constraints;
    }


    /**
     * 
     * @param instance instance du monde de bloc
     * @return true si l'instance verifie tous les contraintes false sinon
     */
    public boolean isSatisfied(Map<Variable,Object> instance){
        for(Constraint c : getBasicConstraints()){
            if(instance.keySet().containsAll(c.getScope())){
                if(!c.isSatisfiedBy(instance)){
                    return false;
                }
            }
        }

        return true;
    }
    

}
