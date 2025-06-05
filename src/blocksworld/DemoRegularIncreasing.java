package blocksworld;
/*exo 10 */
import java.util.Map;
import java.util.Random;
import java.util.Set;

import cp.BacktrackSolver;
import cp.DomainSizeVariableHeuristic;
import cp.HeuristicMACSolver;
import cp.MACSolver;
import cp.NbConstraintsVariableHeuristic;
import cp.RandomValueHeuristic;
import cp.Solver;
import cp.ValueHeuristic;
import cp.VariableHeuristic;
import modelling.Constraint;
import modelling.Variable;

public class DemoRegularIncreasing {
    
    public static void main(String[] args) {
        int nbBlocs = 5;
        int nbPiles= 3;
        
        BlocksWorldConstraints bw = new BlocksWorldConstraints(nbBlocs, nbPiles);
        BlocksWorldRegular bwr= new BlocksWorldRegular(nbBlocs ,nbPiles);
        BlocksWorldIncreasing bwi = new BlocksWorldIncreasing(nbBlocs, nbPiles);

        Set<Variable> variables = bw.getVariables();
        Set<Constraint> constraints = bw.getBasicConstraints();
        constraints.addAll(bwr.getRegulConstraints());
        constraints.addAll(bwi.getIncreasingConstraints());

        System.out.println("\n############Trouver un monde de blocs regulier et croissant#######################\n");
        System.out.println("un monde de 5 blocks et 3 piles\n");

        System.out.println("******************BackTrackSolver***************************");
        long startTime = System.currentTimeMillis();
        Solver backtrack = new BacktrackSolver( variables , constraints);
        long endTime = System.currentTimeMillis();
        Map<Variable,Object> solvedBackTrack = backtrack.solve();
        System.out.println("solution : "+solvedBackTrack);
        System.out.println("time: " + (endTime-startTime));


        System.out.println("******************MACSolver***************************");
        Solver mac = new MACSolver( variables , constraints);
        startTime = System.currentTimeMillis();
        Map<Variable,Object> solvedMac = mac.solve();
        endTime = System.currentTimeMillis();
        System.out.println("solution : "+solvedMac);
        System.out.println("time: " + (endTime-startTime));

        System.out.println("******************HeuristicMACSolver with nbConstraint var heuristic***************************");

        VariableHeuristic varHeuristic1 = new NbConstraintsVariableHeuristic(constraints, true);

        ValueHeuristic valHeuristic = new RandomValueHeuristic(new Random());
        Solver heuristicMac = new HeuristicMACSolver( variables , constraints, varHeuristic1,valHeuristic);
        startTime = System.currentTimeMillis();
        Map<Variable,Object> solvedHeuristicMac = heuristicMac.solve();
        endTime = System.currentTimeMillis();
        System.out.println("solution : "+solvedHeuristicMac);
        System.out.println("time: " + (endTime-startTime));


        System.out.println("******************HeuristicMACSolver with domainsize var heuristic***************************");

        VariableHeuristic varHeuristic2 = new DomainSizeVariableHeuristic(true);

        
        Solver heuristicMac2 = new HeuristicMACSolver( variables , constraints, varHeuristic2,valHeuristic);
        startTime = System.currentTimeMillis();
        Map<Variable,Object> solvedHeuristicMac2 = heuristicMac2.solve();
        endTime = System.currentTimeMillis();
        System.out.println("solution : "+solvedHeuristicMac2);
        System.out.println("time: " + (endTime-startTime));



        
        BlocksWorldDisplay bd = new BlocksWorldDisplay( nbBlocs,"MAC Solver ", bw);
        bd.displayState(solvedMac);
    }
}
