package cp;

import java.util.*;
import modelling.*;


public class RandomValueHeuristic implements ValueHeuristic{
    
    private Random random;

    public RandomValueHeuristic(Random random){
        this.random = random;
    }

    /*retourne une liste mélangée (pseudo-)uniformément */
    @Override
    public List<Object> ordering (Variable var,  Set<Object> domain){
        
        List<Object> res = new ArrayList<>(domain);
        Collections.shuffle(res,random);

        return res;
    } 
}
