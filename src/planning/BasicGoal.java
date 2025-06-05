package planning;

import java.util.*;
import modelling.*;


/**
 * classe qui implement goal
 * 
 * goal basique un but spécifié par une instanciation partielle des variables
 */
public class BasicGoal implements Goal{

    private Map<Variable, Object> goal;
    
    public BasicGoal(Map<Variable, Object> goal){
        this.goal = goal;
    }

    /**
     * Verifie si le but est atteint
     * 
     * verifie si state affecte toutes les variables à la bonne valeur.
     */
    public boolean isSatisfiedBy(Map<Variable,Object> state){
        for (Variable v : this.goal.keySet() ){
            if (! state.containsKey(v) || state.get(v) != this.goal.get(v)){
                return false;
            }
        }
        return true;
    }

    public Map<Variable, Object> getGoal(){
        return this.goal;
    }

    @Override
    public String toString(){
        return " Goal : " + goal;
    }
}
