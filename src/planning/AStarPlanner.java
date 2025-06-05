package planning;

import modelling.*;
import java.util.*;

/**
 * classe qui implemente Planner
 * 
 * Recherche heuristique
 * 
 * trouve le chemin de cout minimal en explorant uniquement les noeuds utiles
 */
public class AStarPlanner implements Planner {
    
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private Heuristic heuristic;
    private int nodeCount;
    private boolean activeCount;

    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic ){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
        this.nodeCount = 0;
        this.activeCount = false;
    }

    /**
     * initialise les paramètres pour appeler astra et renvoie le plan trouver
     */
    @Override
    public List<Action> plan(){
        Map< Map<Variable, Object> , Action> plan = new HashMap<>();
        Map< Map<Variable, Object> , Integer > distance = new HashMap<>();
        Map< Map<Variable, Object>, Map<Variable, Object> > father = new HashMap<>();
        Map< Map<Variable, Object> , Float > value = new HashMap<>(); 
        return ASTAR(plan, father, distance, value);

    }


    /**
     * renvoie le plan (la liste d'actions) qui mène au but ayant un cout minimal et en explorant que les noeuds qui semblent utiles, null sinon
     * @param plan Map qui associe pour chaque état, l'action qui a permis de l'atteindre.
     * @param father Map de {etat:précedent}: pour chaque état on précise le parent (l'état précédent)
     * @param distance Map qui associe pour chaque état, la distance minimal(cout total des actions) pour y arriver depuis l'état initila.
     * @param value Map qui associe pour chaque état, une estiamtion heuristique pour atteindre le but
     * @return la liste des acions qui mènent au but qui représente le plan optimal de cout minimal, null si non
     */
    public List<Action> ASTAR(Map< Map<Variable, Object> , Action> plan, Map< Map<Variable, Object>, Map<Variable, Object> >  father, Map< Map<Variable, Object> , Integer > distance, Map< Map<Variable, Object> , Float > value ){
        Set< Map <Variable, Object>> open = new HashSet<>();

        
        father.put(initialState , null);
        distance.put(initialState , 0);
        open.add(initialState);
        value.put(initialState, heuristic.estimate(initialState)); //estimation de l'heuristique

        Map<Variable, Object> instanciation = new HashMap<>();
        Map<Variable,Object> next = new HashMap<>();

        while (!open.isEmpty()){
            
            instanciation = argMin(open,value); //récupère l'état avec la plus petite heristique dans value

            if (goal.isSatisfiedBy(instanciation)){ //si le but est satisfait on renvoie le plan
                return BFSPlanner.getBfsPlan(father, plan, instanciation);
            }

            open.remove(instanciation);
            
            for (Action a : actions){
                if (a.isApplicable(instanciation)){
                    next = a.successor(instanciation);
                    
                    if(activeCount){
                        nodeCount++;
                    }

                    if (! distance.containsKey(next)){
                        distance.put(next, Integer.MAX_VALUE);
                    }

                    if (distance.get(next) > (distance.get(instanciation) + a.getCost())){
                        distance.put(next, distance.get(instanciation) + a.getCost());
                        value.put(next, distance.get(next)+ heuristic.estimate(next)); //on calcule la nouvelle heuristique
                        father.put(next, instanciation);
                        plan.put(next,a);
                        open.add(next);
                    }
                }
            }
        }
        return null;
    }

    //renvoie l'état dans open qui a la plus petite heuristique(cout) dans value
    public Map <Variable, Object> argMin (Set< Map <Variable, Object>> open, Map< Map<Variable, Object> , Float > value ){
        float minDist = Float.POSITIVE_INFINITY ;
        Map<Variable, Object> minState= null; 
        for ( Map <Variable, Object> state :  open){
            float curr = value.get(state);
            if(curr < minDist){
                minDist = curr;
                minState = state;
            }
        }
        

        return minState;
    }

    @Override
    public Set<Action> getActions(){
        return this.actions;
    }

    @Override
    public Map<Variable, Object> getInitialState(){
        return this.initialState;
    }

    @Override
    public Goal getGoal(){
        return this.goal;
    }


    /**
     * si on active le compte de noeud on peut reccupérer le nb de noeuds parcourus pour trouver le chemin
     */
    @Override
    public void activateNodeCount(boolean activate){
        activeCount = activate;
    }

    /**
     * renvoie le nb de noeuds parcourus pour atteindre le but 
     */
    @Override
    public int getNodeCount(){
        return nodeCount;
    }
}
