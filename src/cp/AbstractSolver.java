package cp;

import modelling.*;
import java.util.*;


public abstract class AbstractSolver implements Solver {
    
    protected Set<Variable> variables;
    protected Set<Constraint> constraints;

    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints){
        this.variables = variables;
        this.constraints = constraints;
    }
    
    //si l'affectation partielle passée en argument satisfait ttes les constraints, retourne vrai, faux sinon
    public boolean isConsistent( Map<Variable,Object> partial){
        for (Constraint c : constraints){
            //on verifie si partial contient tte les variables concernées par la contrainte
            if(partial.keySet().containsAll(c.getScope())){
                if(! c.isSatisfiedBy(partial)){
                    return false;
                }
            }
        }
        return true;
    }

    public Set<Variable> getVariables(){
        return this.variables;
    }

    public Set<Constraint> getConstraints(){
        return this.constraints;
    }
}
