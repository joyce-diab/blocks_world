package planning;

import java.util.*;
import modelling.*;

/**
 * classe qui implemente Planner
 * 
 * Recherche suivant le cout minimal
 * 
 * trouve le chemin(le plan) de coût minimal
 */

public class DijkstraPlanner implements Planner {

    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private int nodeCount;
    private boolean activeCount;
    
    public DijkstraPlanner( Map<Variable, Object> initialState, Set<Action> actions, Goal goal ){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.nodeCount = 0;
        this.activeCount = false;
    }

    /**
     * initialise les paramètres pour appeler dijkstra et renvoie le plan trouver
     */
    @Override
    public List<Action> plan(){
        Map< Map<Variable, Object> , Action> plan = new HashMap<>();
        Map< Map<Variable, Object> , Integer > distance = new HashMap<>();
        Map< Map<Variable, Object>, Map<Variable, Object> > father = new HashMap<>();
        return Dijkstra(plan, distance, father);

    }



    /**
     * renvoie le plan (la liste d'actions) qui mène au but ayant un cout minimal, null sinon
     * @param plan Map qui associe pour chaque état, l'action qui a permis de l'atteindre.
     * @param distance Map qui associe pour chaque état, la distance minimal(cout total des actions) pour y arriver depuis l'état initila.
     * @param father Map de {etat:précedent}: pour chaque état on précise le parent (l'état précédent)
     * @return la liste des acions qui mènent au but qui représente le plan optimal de cout minimal, null si non
     */
    public List<Action> Dijkstra(Map< Map<Variable, Object> , Action> plan, Map< Map<Variable, Object> , Integer > distance ,  Map< Map<Variable, Object>, Map<Variable, Object> >  father){
        Set<Map<Variable, Object>> goals = new HashSet<>(); 
        Set< Map <Variable, Object>> open = new HashSet<>(); //ensemble des état à explorer

        father.put(initialState , null); //l'etat de depart n'a pas de parent
        distance.put(initialState , 0); //sa distance est de 0.
        open.add(initialState); //l'ajouter pour l'explorer

        Map<Variable, Object> instanciation = new HashMap<>();
        Map<Variable,Object> next = new HashMap<>();

        while (!open.isEmpty()){
            
            instanciation = argMin(open,distance); //récupère l'état avec la plus petite diatnce (cout minimal) dans open
            open.remove(instanciation);

            if (goal.isSatisfiedBy(instanciation)){ //on marque létat but s'il est satisfait
                goals.add(instanciation);
            }

            for (Action a : actions){
                if (a.isApplicable(instanciation)){
                    next = a.successor(instanciation);
                    
                    if(activeCount){
                        nodeCount++;
                    }
                    
                    //si l'etat suivant n'a pas encore de distance défini, on l'initialise à infini 
                    if (! distance.containsKey(next)){
                        distance.put(next, Integer.MAX_VALUE);
                    }
                    
                    //si le chemin actuel vers cet état est plus court que le chemin déja défini
                    if (distance.get(next) > (distance.get(instanciation) + a.getCost())){
                        distance.put(next, distance.get(instanciation) + a.getCost()); //mise à jour de la distance
                        father.put(next, instanciation); //mise a jour du parent
                        plan.put(next,a); //mise a jour de l'action qui a menée
                        open.add(next); //ajout de l'élément pour l'explorer
                    }
                }
            }
        }
        if(goals.isEmpty()){
            return null; //si aucun but trouvé on renvoie null
        }

        return get_dijkstra_plan(father,plan,goals,distance);
    }


    //renvoie l'état dans open qui a la plus petite distance(cout) dans dist
    public Map <Variable, Object> argMin (Set< Map <Variable, Object>> open, Map< Map<Variable, Object> , Integer > dist ){
        int minDist = Integer.MAX_VALUE ;
        Map<Variable, Object> minState= null; 
        for ( Map <Variable, Object> state :  open){
            int curr = dist.get(state);
            if(curr < minDist){
                minDist = curr;
                minState = state;
            }
        }
        

        return minState;
    }

    /**
     * a partir de plan, father, goal et diastance renvoie la liste des action (le chemin/le plan) trouvé ayant le plus petit cout
     */
    public List<Action> get_dijkstra_plan(Map< Map<Variable, Object> , Map<Variable, Object> > father, Map< Map<Variable, Object> , Action > plan, Set<Map<Variable, Object>> goals, Map< Map<Variable, Object> , Integer > distance){
        
        LinkedList<Action> DIJ_plan = new LinkedList<>();
        Map <Variable, Object> g = argMin(goals,distance);

        while( father.get(g) != null){
            DIJ_plan.addFirst(plan.get(g));
            g = father.get(g);
        }

        return DIJ_plan;
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
