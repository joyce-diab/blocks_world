package modelling;

import java.util.*;

/**
 * classe qui implemente Constraint
 * 
 * Represente les contraintes de la forme (v1 appartient à S1) -> (v2 appartient à S2)
 * 
 */
public class Implication implements Constraint{

    private Variable v1;
    private Variable v2;
    private Set<Object> ssens1;
    private Set<Object> ssens2;
    

    /**
     * v1, v2 sont les variables
     * 
     *  S1, S2 des sous-ensembles de leurs domaines respectif
     * 
     */
    public Implication(Variable v1, Set<Object> ssens1 , Variable v2, Set<Object> ssens2){

        this.v1 = v1;
        this.v2 = v2;
        this.ssens1 = ssens1;
        this.ssens2 = ssens2;

    }

    /*
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
     * lance une erreure si l'affectation  ne donne pas au moins une valeur  aux variables du scope
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> affectation){

        if( ! (affectation.containsKey(v1)) || ! (affectation.containsKey(v2))){
            throw new IllegalArgumentException("Cette instance ne contient pas une affectation pour "+ v1.getName() + " ou "+ v2.getName());
        }

        //si v1 appartient a S1:
        //      si v2 appartient a v2 => renvoie true
        //      si v2 n'appartient pas => condition non satisfaite => false
        if( ssens1.contains( affectation.get(v1) ) ){ 
            return ssens2.contains( affectation.get(v2)) ;
        }
        
        //si v2 n'appartient pas à S1 => revoie truc?*
        return true;
    }

    
    @Override
    public String toString() {
        return "Implication: si " + v1.getName() + " ∈ " + ssens1 + ", alors " + v2.getName() + " ∈ " + ssens2;
    }


}
