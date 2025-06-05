package planning;

import modelling.Variable;
import java.util.*;

/**
 * classe qui implemente Action
 * 
 * repreente les actions basiques dont la precondition et l'effet sont des instances partielles des variables
 */
public class BasicAction implements Action{

    private Map<Variable,Object> prec;
    private Map<Variable,Object> effect;
    private int cost;

    public BasicAction (Map<Variable,Object> prec, Map<Variable,Object> effect, int cost){
        this.prec = prec;
        this.effect = effect;
        this.cost = cost;
    }
    
    /**
     * verifie si les préconditions sont vérifiées
     * 
     * c'est-à-dire vérifie si tous les variables de la précondition sont présentes dans l'etat actuel 
     *      et ont les memes valeurs
     */
    @Override
    public boolean isApplicable(Map<Variable,Object> state){
        for ( Variable p : this.prec.keySet()){
            if (! state.containsKey(p) || state.get(p) != prec.get(p)){
                return false; //p n'appartient pas a state(etat actuel) ou valeur de p dans state est différente que celle de la prec
            }
        }
        return true;
    }
    
    /**
     * si l'action est applicable, renvoie l'etat suivant
     */
    @Override
    public Map<Variable,Object> successor(Map<Variable, Object> state ){
        Map<Variable,Object> next = new HashMap<>();

        if (!isApplicable(state)){
            return null;
        }

        //recopie l'etat actuel
        for ( Variable p : state.keySet()){
            next.put(p, state.get(p));
        }

        //applique les effets 
        for (Variable p : this.effect.keySet()){
            next.put(p, this.effect.get(p));
        }

        //renvoie l'etat suivant
        return next;
    }
    
    /**
     * renvoie le cout de l'action
     */
    @Override
    public int getCost(){
        return this.cost;
    }

    @Override
    public String toString(){
        return "Action: \n  prec =>  "+this.prec+ "\n  effet =>  "+ this.effect +". \n";
    }


}
