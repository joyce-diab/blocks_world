package modelling;

import java.util.*;

/**
 * classe qui implemente Constraint
 * 
 * Represente les contraintes de la forme v appartient à S (un sous-ensemble du domaine de v)
 * 
 */

public class UnaryConstraint implements Constraint{
    
    private Variable v;
    private Set<Object> ssens;
    
    public UnaryConstraint(Variable v, Set<Object> ssens){

        this.v = v;
        this.ssens = ssens;

    }


    /**
     * renvoie l'ensemble des variables concernées de la contrainte 
     */
    @Override
    public Set<Variable> getScope(){
        Set<Variable> constraints = new HashSet<>();
        
        constraints.add(this.v);
        
        return constraints;
    }


    /**
     * renvoie si l'instanciation est satisfaite par la contrainte
     * 
     * lance une erreure si l'affectation  ne donne pas une valeur à la variable du scope
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> affectation){

        if( ! (affectation.containsKey(v)) ){
            throw new IllegalArgumentException("Cette instance ne contient pas une affectation pour "+ v.getName() );
        }

        //est ce que l'affectation de v appartient au sous-ensemble S
        return ssens.contains( affectation.get(v) ); 
    }

    @Override
    public String toString() {
        return "UnaryConstraint: " + v.getName() + " ∈ " + this.ssens;
    }

}
