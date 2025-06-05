package planning;

import java.util.*;
import modelling.*;

/**
 * classe qui implemente Planner
 * 
 * Recherche en profondeur
 * 
 * trouve le premier chemin(plan) menant au but
 */
public class DFSPlanner implements Planner {

    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private int nodeCount;
    private boolean activeCount;
    
    public DFSPlanner( Map<Variable, Object> initialState, Set<Action> actions, Goal goal ){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.nodeCount = 0;
        this.activeCount = false;
    }

    /**
     * initialise les paramètres pour appeler dfs et renvoie le plan trouver
     */
    @Override
    public List<Action> plan(){
        List<Action> planFinal = new ArrayList<Action>(); 
        Set< Map<Variable, Object> > closed = new HashSet<>();
            
        closed.add(initialState);

        return DFS(initialState, planFinal, closed);

    }


    /**
     * revoie le premier plan trouver allant de l'etat initial au but
     * @param state l'etat actuel (les variables et leurs valeurs à ce moment de la recherche)
     * @param plan liste des actions faites pour arriver à l'état actuel
     * @param closed l'ensemble des états déjà visités
     * @return le premier plan trouver (la liste des acions qui mènent au but(goal), null sinon
     */

    
    public List<Action> DFS(Map<Variable, Object> state, List<Action> plan, Set< Map<Variable, Object> > closed){
        Map<Variable,Object> next = new HashMap<>();
        List<Action> subplan = new ArrayList<>();
        if (goal.isSatisfiedBy(state)){
            return plan;
        }
        for (Action a : this.actions){ //on parcours tous les actions possibles
            if (a.isApplicable(state)){
                next = a.successor(state); //si l'action est applicable on recupère l'etat suivant
                
                if(activeCount){
                    nodeCount++;
                }
               
                if (! closed.contains(next)){ //si on n'a pas encore exploré l'état suivant 
                    plan.add(a);
                    closed.add(next);
                    subplan = DFS(next, plan, closed); //on test si en prenant ce chemin on peut atteindre le but

                    if(subplan != null){
                        return subplan; //si oui alors chemin trouvé!
                    }
                    plan.remove(plan.size()-1); //sinon alors mauvaise idéé => on ne va pas passer par ce noed/cette etat/cette action
                }
            }
        }
        return null; //renvoie null si aucun chemin trouvé

    }

    
    @Override
    public Set<Action> getActions(){
        return actions;
    }

    
    @Override
    public Map<Variable, Object> getInitialState(){
        return initialState;
    }

    
    @Override
    public Goal getGoal(){
        return goal;
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
