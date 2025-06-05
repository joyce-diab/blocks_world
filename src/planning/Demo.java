package planning;

import modelling.*;
import java.util.*;


public class Demo {
    public static void main(String[] args) {
        
        Set<Object> s1 = new HashSet<>();
        s1.add(0);
        s1.add(1);

        Set<Object> s2 = new HashSet<>();
        s2.add(0);
        s2.add(1);

        Variable v1 = new Variable("joyce", s1);
        
        Variable v2 = new Variable("lyna", s2);

        Map<Variable, Object> initialState = new HashMap<>();
        initialState.put(v1, 0);
        initialState.put(v2, 0);


        Map<Variable, Object> prec1 = new HashMap<>();
        prec1.put(v1, 0);

        Map<Variable, Object> eff1 = new HashMap<>();
        eff1.put(v1, 1);
        Action action1 = new BasicAction(prec1, eff1,1);
        


        Map<Variable, Object> prec2 = new HashMap<>();
        prec2.put(v2, 0);

        Map<Variable, Object> eff2 = new HashMap<>();
        eff2.put(v2, 1);
        Action action2 = new BasicAction(prec2, eff2,1);
    

        Set<Action> actions = new HashSet<>();
        actions.add(action1);
        actions.add(action2);

        Map<Variable, Object> goals = new HashMap<>();
        goals.put(v1,1);
        goals.put(v2,1);
        BasicGoal goal = new BasicGoal(goals);


        //on a supposÃ© que l'heuristique reprÃ©sente le nb de variables Ã  atteindre
        Heuristic heuristic = new Heuristic() {
            @Override
            public float estimate(Map<Variable, Object> state) {
                int count = 0;
                Map<Variable,Object> g = goal.getGoal();
                for (Variable var : state.keySet()) {
                    if (g.get(var) != state.get(var) ) { 
                        count++;
                    }
                }
                return count; //count retourne le nombre de variables non atteintes
            }
        };
        
        System.out.println("*************************AStarPlanner*************************");
        Planner astar = new AStarPlanner(initialState, actions, goal, heuristic); // heuristique simple
        astar.activateNodeCount(true);
        List<Action> aplan = astar.plan();
        System.out.println("result: "+aplan);
        System.out.println("Nodes explored:" +astar.getNodeCount());

        System.out.println("\n*************************BFSPlanner*************************");
        Planner bfs = new BFSPlanner(initialState, actions, goal);
        bfs.activateNodeCount(true);
        List<Action> bplan = bfs.plan();
        System.out.println("result:" + bplan);
        System.out.println("Nodes explored: " + bfs.getNodeCount());


        System.out.println("\n*************************DFSPlanner*************************");
        Planner dfs= new DFSPlanner(initialState, actions, goal);
        dfs.activateNodeCount(true);
        List<Action> dplan= dfs.plan();
        System.out.println("result:" + dplan);
        System.out.println("Nodes explored: "+dfs.getNodeCount());


        System.out.println("\n*************************DijkstraPlanner*************************");
        Planner djk= new DijkstraPlanner(initialState, actions, goal);
        djk.activateNodeCount(true);
        List<Action> djkplan =djk.plan();
        System.out.println("result:" +djkplan);
        System.out.println("Nodes explored: " +djk.getNodeCount());



    }
}
