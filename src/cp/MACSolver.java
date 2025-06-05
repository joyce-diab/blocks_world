package cp;

import java.util.*;
import modelling.*;


public class MACSolver extends AbstractSolver{
    
    public MACSolver(Set<Variable> variables, Set<Constraint> constraints){
        super(variables, constraints);
    }

    @Override
    public Map<Variable, Object> solve(){
        Map<Variable, Object> initial = new HashMap<>();

        Map<Variable, Set<Object>> domains= new HashMap<>();
        
        for(Variable v : this.variables){
            domains.put(v, v.getDomain());
        }
        
        return MAC(initial,new LinkedList<>(this.variables),domains );
    }


    public Map<Variable, Object> MAC(Map<Variable, Object> partial, LinkedList<Variable> vars, Map<Variable, Set<Object>> domains){
        
        ArcConsistency arc = new ArcConsistency(constraints);

        if (vars.isEmpty()){
            return partial;
        }
        //réduction des domaines des variables par l’arc-cohérence
        if (! arc.ac1(domains)){
            return null;
        }
        //choisir une variable non encore instanciée
        Variable x = vars.removeFirst();

        
        //choisir une valeur dans le domaine de x
        for(Object value : domains.get(x)){
            Map<Variable, Object> instance = new HashMap<>(partial);
            instance.put(x,value);

            if(isConsistent(instance)){
                Map<Variable, Object> res = MAC(instance, vars, domains);
                
                if(res != null){
                    return res;
                }
            }
        }

        vars.addLast(x);
        return null;
    }
}
