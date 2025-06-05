package cp;

import java.util.*;
import modelling.*;


/*classe similaire a mac solver mais depend des heuristiques pour optimiser la recherche */
public class HeuristicMACSolver extends AbstractSolver{

    private VariableHeuristic variableHeuristic;
    private ValueHeuristic valueHeuristic;
    
    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic){
        super(variables, constraints);

        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
    }

    @Override
    public Map<Variable, Object> solve(){
        Map<Variable, Object> initial = new HashMap<>();
        Map<Variable, Set<Object>> domains= new HashMap<>();
        
        for(Variable v : this.variables){
            domains.put(v, v.getDomain());
        }
        
        return MACheuristique(initial,new HashSet<>(this.variables),domains );
    }


    public Map<Variable, Object> MACheuristique(Map<Variable, Object> partial, Set<Variable> vars, Map<Variable, Set<Object>> domains){
        
        ArcConsistency arc = new ArcConsistency(constraints);
        
        if (vars.isEmpty()){
            return partial;
        }

        if (! arc.ac1(domains)){
            return null;
        }

        Variable x = variableHeuristic.best(vars, domains); //on chosie la best variable
        vars.remove(x);

        List<Object> orderedDomain = valueHeuristic.ordering(x, domains.get(x)); //on ordonne son domain restant
        
        //on parcourt les valeurs ordonnées de la variable concernée en fonction de l'heuristique
        for(Object value : orderedDomain){
            Map<Variable, Object> instance = new HashMap<>(partial);
            instance.put(x,value);

            if(isConsistent(instance)){
                Map<Variable, Object> res = MACheuristique(instance, vars, domains);
                
                if(res != null){
                    return res;
                }
            }
        }

        vars.add(x);
        return null;
    }
}
