package blocksworld;
/*exo 10 */
import cp.*;

import modelling.*;
import java.util.*;

public class DemoSolverIncreasing{
    
    public static void main(String[] args) {

        int nbBlocs = 5;
        int nbPiles= 3;
        BlocksWorldIncreasing bw = new BlocksWorldIncreasing(nbBlocs ,nbPiles);

        Set<Variable> variables = bw.getVariables();
        Set<Constraint> increasingConstraints = bw.getAllConstraints();

        System.out.println("\n############Trouver un monde de blocs croissanr#######################\n");
        System.out.println("un monde de 5 blocks et 3 piles\n");

        System.out.println("******************BackTrackSolver***************************");
        long startTime = System.currentTimeMillis();
        Solver backtrack = new BacktrackSolver( variables , increasingConstraints);
        long endTime = System.currentTimeMillis();
        Map<Variable,Object> solvedBackTrack = backtrack.solve();
        System.out.println("solution : "+solvedBackTrack);
        System.out.println("time: " + (endTime-startTime));


        System.out.println("******************MACSolver***************************");
        Solver mac = new MACSolver( variables , increasingConstraints);
        startTime = System.currentTimeMillis();
        Map<Variable,Object> solvedMac = mac.solve();
        endTime = System.currentTimeMillis();
        System.out.println("solution : "+solvedMac);
        System.out.println("time: " + (endTime-startTime));

        System.out.println("******************HeuristicMACSolver with nbConstraint var heuristic***************************");

        VariableHeuristic varHeuristic1 = new NbConstraintsVariableHeuristic(increasingConstraints, true);

        ValueHeuristic valHeuristic = new RandomValueHeuristic(new Random());
        Solver heuristicMac = new HeuristicMACSolver( variables , increasingConstraints, varHeuristic1,valHeuristic);
        startTime = System.currentTimeMillis();
        Map<Variable,Object> solvedHeuristicMac = heuristicMac.solve();
        endTime = System.currentTimeMillis();
        System.out.println("solution : "+solvedHeuristicMac);
        System.out.println("time: " + (endTime-startTime));


        System.out.println("******************HeuristicMACSolver with domainsize var heuristic***************************");

        VariableHeuristic varHeuristic2 = new DomainSizeVariableHeuristic(true);

        
        Solver heuristicMac2 = new HeuristicMACSolver( variables , increasingConstraints, varHeuristic2,valHeuristic);
        startTime = System.currentTimeMillis();
        Map<Variable,Object> solvedHeuristicMac2 = heuristicMac2.solve();
        endTime = System.currentTimeMillis();
        System.out.println("solution : "+solvedHeuristicMac2);
        System.out.println("time: " + (endTime-startTime));



        
        BlocksWorldDisplay bd = new BlocksWorldDisplay(nbBlocs,"MACHeuristic Solver ", bw);
        bd.displayState(solvedHeuristicMac);
        
    }

}
