package modelling;

import java.util.*;

/**
 * sous-classe de la classe Variable
 * 
 * prend juste le nom de la varibale
 * 
 * associe à la variable un domaine true,false
 */
public class BooleanVariable extends Variable{ 
    
    public BooleanVariable(String name){
        super(name, new HashSet<>());
        this.domain.add(true);
        this.domain.add(false);
    }
}
