package blocksworld;

import java.util.*;
import modelling.*;
import planning.Heuristic;

public class DemoHeuristic {
    
    public static void main(String[] args) {
        BlocksWorld bw = new BlocksWorld(7, 3);
        
        Map<Variable,Object> instance = new HashMap<>();

        instance.put(bw.variablesOn.get(0), -2);
        instance.put(bw.variablesOn.get(1), -1);
        instance.put(bw.variablesOn.get(2), 1);
        instance.put(bw.variablesOn.get(3), 2);
        instance.put(bw.variablesOn.get(4), 3);
        instance.put(bw.variablesOn.get(5), -3);
        instance.put(bw.variablesOn.get(6), 5);

        instance.put(bw.variablesFixed.get(0), false);
        instance.put(bw.variablesFixed.get(1), true);
        instance.put(bw.variablesFixed.get(2), true);
        instance.put(bw.variablesFixed.get(3), true);
        instance.put(bw.variablesFixed.get(4), false);
        instance.put(bw.variablesFixed.get(5), true);
        instance.put(bw.variablesFixed.get(6), false);

        instance.put(bw.variablesFree.get(0), false);
        instance.put(bw.variablesFree.get(1), false);
        instance.put(bw.variablesFree.get(2), false);

        Map<Variable,Object> goal = new HashMap<>();

        goal.put(bw.variablesOn.get(0), 1);
        goal.put(bw.variablesOn.get(1), -1);
        goal.put(bw.variablesOn.get(2), 6);
        goal.put(bw.variablesOn.get(3), 2);
        goal.put(bw.variablesOn.get(4), 5);
        goal.put(bw.variablesOn.get(5), -3);
        goal.put(bw.variablesOn.get(6), -2);

        goal.put(bw.variablesFixed.get(0), false);
        goal.put(bw.variablesFixed.get(1), true);
        goal.put(bw.variablesFixed.get(2), true);
        goal.put(bw.variablesFixed.get(3), false);
        goal.put(bw.variablesFixed.get(4), false);
        goal.put(bw.variablesFixed.get(5), true);
        goal.put(bw.variablesFixed.get(6), true);

        goal.put(bw.variablesFree.get(0), false);
        goal.put(bw.variablesFree.get(1), false);
        goal.put(bw.variablesFree.get(2), false);

        System.out.println("cet demo est pour verfifier que l'heuristique hauteur est plus efficace que les blocs mal placés");

        Heuristic h1 = new BlocksWorldHeuristic1(goal);

        Heuristic h2 = new BlocksWorldHeuristic2(goal);
        
        System.out.println(h1.estimate(instance));
        System.out.println(h2.estimate(instance));
    }
}
