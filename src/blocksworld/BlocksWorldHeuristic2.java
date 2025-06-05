package blocksworld;

import java.util.*;

import modelling.*;
import planning.*;

/**
 * heuristique: le nombre minimal d'actions pour passer de l'etat actuel a l'etat final
 * 
 * pour chaque pile on calcul la hauteur du plus profond bloc mal placé
 * 
 * on renvoie la somme de tous les hauteurs calculées des piles
 * 
 */
public class BlocksWorldHeuristic2 implements Heuristic{
    
    protected Map<Variable,Object> goal;

    public BlocksWorldHeuristic2(Map<Variable,Object> goal){
        this.goal = goal;
    }

    
    @Override
    public float estimate(Map<Variable, Object> state) {

        Set<Integer> topBlocks = new HashSet<>();
        Map<Integer,Integer> stateInt = new HashMap<>();
        Set<Integer> misplacedBlocks = new HashSet<>();

        /*on parcours les variables et on recupere 
                un map de int1:int2 qui represente onBint1=int2
                un set des int des blocs malplacé
                un set des sommets, kle sommet de chaque pile*/
        for(Variable var : state.keySet()){

            Object value = state.get(var);

            //on recupere le num block : place sur num block
            if(value instanceof Integer){
                
                int numBlock = Integer.parseInt(var.getName().substring(3));
                if(! value.equals(goal.get(var))) misplacedBlocks.add(numBlock);
                
                stateInt.put(numBlock, (Integer) value );
            }

            //on recupere les blocks qui sont au sommet de chaque pile (genre fixedB = false)
            else if(state.get(var).equals(false) && var.getName().substring(0,6).equals("fixedB")){
                topBlocks.add(Integer.parseInt(var.getName().substring(6)));
            }

        }

        float total=0;
        /*pour chaque sommet d'une pile */
        for(Integer block : topBlocks){
            int actualBlock = block;
            int depth = 0;
            int lowestMisplaced = 0;

            while(actualBlock>=0){ /*on parcours jusqu'à l'arrivée a la table (dernier bloc on-table) */
                depth++;
                if(misplacedBlocks.contains(actualBlock)){ //si c un bloc mal placee on recupere son depth
                    lowestMisplaced = depth;
                }
                actualBlock = stateInt.get(actualBlock); //le bloc en dessous

            }

            total+=lowestMisplaced;
            
        }

        return total;


    }
}
