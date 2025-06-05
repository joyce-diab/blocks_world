package cp;

import modelling.*;
import java.util.*;

public class BacktrackSolver extends AbstractSolver {

    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints){
        super(variables,constraints);
    }

    @Override
    public Map<Variable, Object> solve(){
        LinkedList<Variable> vars = new LinkedList<>(variables);
        Map<Variable,Object> solution = BT(new HashMap<>(),vars);
        return solution;
    }

    /*on implemente l'algorithme du backtracking
     * backtrack si la valeur choisie pour la variable en cours d'instanciation (dans ce cas : instance) n'est pas validée
     * avec l'instanciation partielle sur les contraintes possibles;
    */
    public Map<Variable, Object> BT (Map<Variable, Object> partial, LinkedList<Variable> vars ){
        
        if (vars.isEmpty()){
            return partial;
        }
        Variable x = vars.removeFirst(); //valeur quelconque des variables non instanciées
        
        Set<Object> domain = x.getDomain();

        // value c une valeur du domaine de definition de x
        for(Object value : domain ){
            Map<Variable, Object> instance = new HashMap<>(partial);
            instance.put(x,value);
            

            if (isConsistent(instance)){
                
                Map<Variable, Object> result = BT(instance, vars);
                
                if(result != null){
                    return result;
                }
            }
        }
        
        vars.addLast(x);
        
        return null;
    }
}

