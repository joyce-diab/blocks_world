package cp;

import java.util.*;
import modelling.*;

/*classe qui se base sur l'heuristique qui depend du nb de contraintes pour chaque variable */
public class NbConstraintsVariableHeuristic implements VariableHeuristic {
    
    private Set<Constraint> constraints;
    private boolean inMostConstraints;

    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean inMostConstraints){
        this.inMostConstraints = inMostConstraints;
        this.constraints = constraints; /*indique si l’on préfère les variables apparaissant dans le plus de contraintes
        (true) ou dans le moins de contraintes (false)*/
    }

    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains){
        Map<Variable, Integer> compteur = new HashMap<>();
        for(Variable v : variables){
            compteur.put(v,0);
        }

        for(Constraint c : constraints){
            for(Variable v : c.getScope()){
                if(variables.contains(v)){
                    compteur.put(v, compteur.get(v)+1);
                
                }
            }
        }

        int bestInt = inMostConstraints ? -1 : Integer.MAX_VALUE;
        Variable bestVar = null;

        for(Variable v : compteur.keySet()){
            int nb = compteur.get(v);
            boolean find = false;
            if (inMostConstraints){
                find  = bestInt <  nb;
            }else{
                find  = bestInt >  nb;
            }

            if (find){
                bestVar = v;
                bestInt = nb;
            }
        }

        return bestVar;
    }
}
