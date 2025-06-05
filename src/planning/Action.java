package planning;

import modelling.*;
import java.util.*;

public interface Action {
    
    public boolean isApplicable(Map<Variable,Object> state); //verifie si les préconditions de l'action sont vérifiées
    public Map<Variable,Object> successor(Map<Variable, Object> state ); //renvoie l'etat suivant
    public int getCost(); //renvoie le cout de l'action

}
