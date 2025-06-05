package blocksworld;
/*exo 9 */
import cp.*;
import modelling.*;
import java.util.*;

public class DemoSolverRegular {
    
    public static void main(String[] args) {

        int nbBlocs = 5;
        int nbPiles= 3;
        BlocksWorldRegular bw = new BlocksWorldRegular(nbBlocs ,nbPiles);

        Set<Variable> variables = bw.getVariables();
        Set<Constraint> regularConstraints = bw.getAllConstraints();

        System.out.println("\n############Trouver un monde de blocs regulier#######################\n");
        System.out.println("un monde de 5 blocks et 3 piles\n");

        System.out.println("******************BackTrackSolver***************************");
        long startTime = System.currentTimeMillis();
        Solver backtrack = new BacktrackSolver( variables , regularConstraints);
        long endTime = System.currentTimeMillis();
        Map<Variable,Object> solvedBackTrack = backtrack.solve();
        System.out.println("solution : "+solvedBackTrack);
        System.out.println("time: " + (endTime-startTime));


        System.out.println("******************MACSolver***************************");
        Solver mac = new MACSolver( variables , regularConstraints);
        startTime = System.currentTimeMillis();
        Map<Variable,Object> solvedMac = mac.solve();
        endTime = System.currentTimeMillis();
        System.out.println("solution : "+solvedMac);
        System.out.println("time: " + (endTime-startTime));

        System.out.println("******************HeuristicMACSolver with nbConstraint var heuristic***************************");

        VariableHeuristic varHeuristic1 = new NbConstraintsVariableHeuristic(regularConstraints, true);

        ValueHeuristic valHeuristic = new RandomValueHeuristic(new Random());
        Solver heuristicMac = new HeuristicMACSolver( variables , regularConstraints, varHeuristic1,valHeuristic);
        startTime = System.currentTimeMillis();
        Map<Variable,Object> solvedHeuristicMac = heuristicMac.solve();
        endTime = System.currentTimeMillis();
        System.out.println("solution : "+solvedHeuristicMac);
        System.out.println("time: " + (endTime-startTime));


        System.out.println("******************HeuristicMACSolver with domainsize var heuristic***************************");

        VariableHeuristic varHeuristic2 = new DomainSizeVariableHeuristic(true);

        
        Solver heuristicMac2 = new HeuristicMACSolver( variables , regularConstraints, varHeuristic2,valHeuristic);
        startTime = System.currentTimeMillis();
        Map<Variable,Object> solvedHeuristicMac2 = heuristicMac2.solve();
        endTime = System.currentTimeMillis();
        System.out.println("solution : "+solvedHeuristicMac2);
        System.out.println("time: " + (endTime-startTime));



        
        BlocksWorldDisplay bd = new BlocksWorldDisplay( nbBlocs,"MAC Solver ", bw);
        bd.displayState(solvedMac);
        
    }

}
