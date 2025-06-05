package blocksworld;
/*exo 8 */
import java.util.*;

import modelling.*;
import planning.*;

public class DemoPlanning {

    public static void main(String[] args) {
        BlocksWorld bw = new BlocksWorld(4, 3);

        List<List<Integer>> piles = new ArrayList<>();
        piles.add(Arrays.asList(2,1,0));
        piles.add(Arrays.asList());
        piles.add(Arrays.asList(3));
        /*test trpo long  7 blocks 3piles*/
        /*piles.add(Arrays.asList(4,3,2,1));
        piles.add(Arrays.asList(0));
        piles.add(Arrays.asList(6,5));*/

        /*test trpo rapide 2 blocks 2 piles */
        /*piles.add(Arrays.asList(1,0));
        piles.add(Arrays.asList(2));*/
        
        Map<Variable,Object> initialState = bw.instantiation(piles);

        List<List<Integer>> finalPiles = new ArrayList<>();
        
        finalPiles.add(Arrays.asList(2,0,3,1));
        finalPiles.add(Arrays.asList());
        finalPiles.add(Arrays.asList());
        /*test trpo long 7 3*/
        /*finalPiles.add(Arrays.asList(0,1));
        finalPiles.add(Arrays.asList(3,2,6));
        finalPiles.add(Arrays.asList(4,5));*/
        
        /*test trpo rapide 2 blocks 2 piles */
        /*finalPiles.add(Arrays.asList(0));
        finalPiles.add(Arrays.asList(1,2));*/
        
        Map<Variable,Object> finalState = bw.instantiation(finalPiles);

        //System.out.println(initialState);
        //System.out.println(finalState);
        
        Goal goal = new BasicGoal(finalState);

        BlocksWorldAction ba = new BlocksWorldAction(4 , 3);

        Set<Action> actions = ba.getBasicActions();
        //System.out.println(actions);

        Heuristic heuristic1 = new BlocksWorldHeuristic1(finalState);

        Heuristic heuristic2 = new BlocksWorldHeuristic2(finalState);
        

        System.out.println("*************************AStarPlanner avec h1*************************");
        Planner astar = new AStarPlanner(initialState, actions, goal, heuristic1); // heuristique simple
        astar.activateNodeCount(true);
        long startTime = System.currentTimeMillis();
        List<Action> aplan = astar.plan();
        long endTime = System.currentTimeMillis();
        System.out.println("result: "+aplan);
        System.out.println("Moves count: "+aplan.size());
        System.out.println("Nodes explored:" +astar.getNodeCount());
        System.out.println("time: " + (endTime-startTime));

        System.out.println("*************************AStarPlanner avec h2*************************");
        Planner astar2 = new AStarPlanner(initialState, actions, goal, heuristic2); // heuristique simple
        astar2.activateNodeCount(true);
        startTime = System.currentTimeMillis();
        List<Action> aplan2 = astar2.plan();
        endTime = System.currentTimeMillis();
        System.out.println("result: "+aplan2);
        System.out.println("Moves count: "+aplan2.size());
        System.out.println("Nodes explored:" +astar2.getNodeCount());
        System.out.println("time: " + (endTime-startTime));

        System.out.println("\n*************************BFSPlanner*************************");
        Planner bfs = new BFSPlanner(initialState, actions, goal);
        bfs.activateNodeCount(true);
        startTime = System.currentTimeMillis();
        List<Action> bplan = bfs.plan();
        endTime = System.currentTimeMillis();
        System.out.println("result:" + bplan);
        System.out.println("Moves count: "+bplan.size());
        System.out.println("Nodes explored: " + bfs.getNodeCount());
        System.out.println("time: " + (endTime-startTime));


        System.out.println("\n*************************DFSPlanner*************************");
        Planner dfs= new DFSPlanner(initialState, actions, goal);
        dfs.activateNodeCount(true);
        startTime = System.currentTimeMillis();
        List<Action> dplan= dfs.plan();
        endTime = System.currentTimeMillis();
        System.out.println("result:" + dplan);
        System.out.println("Moves count: "+dplan.size());
        System.out.println("Nodes explored: "+dfs.getNodeCount());
        System.out.println("time: " + (endTime-startTime));


        System.out.println("\n*************************DijkstraPlanner*************************");
        Planner djk= new DijkstraPlanner(initialState, actions, goal);
        djk.activateNodeCount(true);
        startTime = System.currentTimeMillis();
        List<Action> djkplan =djk.plan();
        endTime = System.currentTimeMillis();
        System.out.println("result:" +djkplan);
        System.out.println("Moves count: "+djkplan.size());
        System.out.println("Nodes explored: " +djk.getNodeCount());
        System.out.println("time: " + (endTime-startTime));



        BlocksWorldDisplay bd = new BlocksWorldDisplay(4,"astar", bw);
        bd.simulatePlan(initialState, aplan2);

    }



}
