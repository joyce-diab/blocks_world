package blocksworld;

import java.util.*;
import modelling.*;
import planning.*;

/**
 * heuristique : nombre de blocs mal placés
 * 
 */
public class BlocksWorldHeuristic1 implements Heuristic{
    
    protected Map<Variable,Object> goal; //etat final

    public BlocksWorldHeuristic1(Map<Variable,Object> goal){
        this.goal = goal;
    }

    /**
     * renvoie le nombre de onB ayant leurs valeurs de l'etat actuel different de l'etat final
     * @param state represente l'etat actuel du monde des blocs
     * @return le nombre blocs mal placés
     */
    @Override
    public float estimate(Map<Variable, Object> state) {
        float misplacedBlocks = 0;

        for(Variable var : state.keySet()){
            
            //si c un onB
            if( state.get(var) instanceof Integer ){
                Object actualValue = state.get(var); //actal position

                Object goalValue = goal.get(var);
                
                if(actualValue !=null && goalValue != null && ! actualValue.equals(goalValue) ){
                    misplacedBlocks+=1;
                }
            }

        }

        return misplacedBlocks;
    }

    public Map<Variable,Object> getGoal(){
        return goal;
    }
}
