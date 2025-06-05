package modelling;
 
import java.util.*;

public interface Constraint {

    public Set<Variable> getScope(); //renvoie l'ensemble des variables concernées de la contrainte
    public boolean isSatisfiedBy(Map<Variable,Object> instanciation); //renvoie si l'instanciation est satisfaite par la contrainte
}
