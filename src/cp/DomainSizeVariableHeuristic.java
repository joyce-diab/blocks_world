package cp;

import java.util.*;
import modelling.*;

/*classe qui se base sur l'heuristique qui depend de la taille du domaine de la variable */
public class DomainSizeVariableHeuristic implements VariableHeuristic{

    private boolean plusGrandDomain;

    public DomainSizeVariableHeuristic(boolean plusGrandDomain){
        this.plusGrandDomain = plusGrandDomain;
    }

    @Override
    //methode qui retourne une variable (la meilleure au sens de l’heuristique)

    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains){
        Map<Variable, Integer> compteur = new HashMap<>();
        for(Variable v : variables){
            compteur.put(v, domains.get(v).size());
        }


        int bestInt = plusGrandDomain ? -1 : Integer.MAX_VALUE;
        Variable bestVar = null;
        //pour chaque variable, trouver la meilleure en fonction de la taille du domaine et la renvoyer
        for(Variable v : compteur.keySet()){
            int nb = compteur.get(v);
            boolean find = false;
            if (plusGrandDomain){
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
