package planning;

import java.util.*;
import modelling.*;


/**
 * classe qui implemente Planner
 * 
 * Recherche en largeur
 * 
 * trouve le chemin(plan) le plus court
 */
public class BFSPlanner implements Planner {

    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private int nodeCount;
    private boolean activeCount;
    
    public BFSPlanner( Map<Variable, Object> initialState, Set<Action> actions, Goal goal ){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.activeCount = false;
    }

    
    @Override
    public List<Action> plan(){
        return BFS();
    }

    /**
     * revoie le plan le plus court allant de l'etat initial au but
     *      (la liste des acions qui mènent au but(goal), null sinon
     */
    
    public List<Action> BFS(){

        //{etat:précedent} pour chaque etat on précise le parent (l'etat précédent)
        Map< Map<Variable, Object> , Map<Variable, Object> > father = new HashMap<>(); 
        father.put(this.initialState,null); //l'etat precedent de l'etat initila est null c le point de depart
        

        //pour chaque état on associe l'action qui a permis de l'atteindre.
        Map< Map<Variable, Object> , Action > plan = new HashMap<>(); 
        
        //l'ensemble des états déjà visités
        Set< Map<Variable, Object> > closed = new HashSet<>(); 
        closed.add(this.initialState); //on l'initialise avec l'etat initiale
        
        //file qui contient les noeuds(etats) à explorer
        Queue < Map<Variable, Object> > open = new LinkedList<>();  
        open.add(this.initialState); //on l'initilaise avec l'etat initial
        
        
        Map<Variable,Object> next = new HashMap<>();
        Map<Variable, Object> instanciation = new HashMap<>(); //etat courant


        //si l'etat initial satisfait le but alors aucune action est nécessaire
        if (this.goal.isSatisfiedBy(this.initialState)){
            return new LinkedList<Action>(); 
        }

        while ( ! open.isEmpty()){
            instanciation = open.poll();
            closed.add(instanciation);

            for (Action a : this.actions){
                if (a.isApplicable(instanciation)){
                    next = a.successor(instanciation);
                    
                    if(activeCount){
                        nodeCount++;
                    }

                    //si next n'est pas encore visité ou exploré
                    if ( !closed.contains(next) && !open.contains(next)) {
                        father.put(next , instanciation);
                        plan.put(next, a);
                        if(this.goal.isSatisfiedBy(next)){
                            return getBfsPlan(father , plan , next); //renvoie la liste des actions
                        }
                        else{
                            open.add(next); //on ajoute next a open pour l'explorer plus tard
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * a partir de plan et de father renvoie la liste des action (le chemin/le plan) trouvé
     */
    public static List<Action> getBfsPlan(Map< Map<Variable, Object> , Map<Variable, Object> > father, Map< Map<Variable, Object> , Action > plan, Map<Variable, Object> g){
        LinkedList<Action> BFSPlan = new LinkedList<>();
        while( father.get(g) != null){
            BFSPlan.addFirst(plan.get(g));
            g = father.get(g);
        }

        return BFSPlan;
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
