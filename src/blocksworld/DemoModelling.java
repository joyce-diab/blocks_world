package blocksworld;
/*exo5 */
import java.util.*;
import modelling.*;

public class DemoModelling {
    
    public static void main(String[] args) {
        BlocksWorld bw = new BlocksWorld(3, 2);
        System.out.println(bw.getVariablesOn().values());

        Map<Variable,Object> instance = new HashMap<>();

        instance.put(bw.variablesOn.get(0), -1);
        instance.put(bw.variablesOn.get(1), -2);
        instance.put(bw.variablesOn.get(2), 0);

        instance.put(bw.variablesFixed.get(0), true);
        instance.put(bw.variablesFixed.get(1), false);
        instance.put(bw.variablesFixed.get(2), false);

        instance.put(bw.variablesFree.get(0), false);
        instance.put(bw.variablesFree.get(1), false);

        BlocksWorldConstraints bconst = new BlocksWorldConstraints(3, 2);
        BlocksWorldRegular breg= new BlocksWorldRegular(3, 2);
        BlocksWorldIncreasing bcroi= new BlocksWorldIncreasing(3, 2);
        
        System.out.println("2 on 0 on P0 / 1 on P1");
        System.out.println("isSatisfiedBy basic constraints :"+bconst.isSatisfied(instance));
        System.out.println("isSatisfiedBy regular constraints :"+breg.isSatisfied(instance));
        System.out.println("isSatisfiedBy increasing constraints :"+bcroi.isSatisfied(instance));

        instance.put(bw.variablesOn.get(1), 2);
        instance.put(bw.variablesFixed.get(2), true);
        instance.put(bw.variablesFree.get(1), true);

        
        System.out.println("1 on 2 on 0 on P0 ");
        System.out.println("isSatisfiedBy basic constraints :"+bconst.isSatisfied(instance));
        System.out.println("isSatisfiedBy regular constraints :"+breg.isSatisfied(instance));
        System.out.println("isSatisfiedBy increasing constraints :"+bcroi.isSatisfied(instance));

        BlocksWorldDisplay bd = new BlocksWorldDisplay(3,"modelling", bw);
        bd.displayState(instance);

        instance.put(bw.variablesFixed.get(2), false);

        System.out.println("1 on 2 and fixedB2 = false");
        System.out.println("1 on 2 on 0 on P0 ");
        System.out.println("isSatisfiedBy basic constraints :"+bconst.isSatisfied(instance));
        System.out.println("isSatisfiedBy regular constraints :"+breg.isSatisfied(instance));
        System.out.println("isSatisfiedBy increasing constraints :"+bcroi.isSatisfied(instance));

    }
}
