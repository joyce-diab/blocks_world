package modelling;

import java.util.*;


/**
 * Classe qui implemente Constraint
 * 
 * Représente les contraintes de la forme v1 != v2
 * 
 */
public class DifferenceConstraint implements Constraint{
   
    private Variable v1;
    private Variable v2;
    
    
    public DifferenceConstraint(Variable v1, Variable v2){
        this.v1 = v1;
        this.v2 = v2;
        
    }

    /**
     * renvoie l'ensemble des variables concernées de la contrainte 
     */
    @Override
    public Set<Variable> getScope(){ 
        Set<Variable> constraints = new HashSet<>();
        
        constraints.add(this.v1);
        constraints.add(this.v2);
        
        return constraints;
    }

    /**
     * renvoie si l'instanciation est satisfaite par la contrainte
     * 
     * lance une erreure si l'affectation  ne donne pas une valeur à au moins chaque variable du scope
     */

    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> affectation){

        if( ! (affectation.containsKey(v1)) || ! (affectation.containsKey(v2))){
            throw new IllegalArgumentException("Cette instance ne contient pas une affectation pour "+ v1.getName() + " ou "+ v2.getName());
        }


        return !(affectation.get(v1).equals(affectation.get(v2)));
    } 

    @Override
    public String toString() {
    	return "Difference : "+v1.getName() + " ≠ " + v2.getName();
    }

}
