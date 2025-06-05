package modelling;
import java.util.*;

public class Demo {
    
    public static void main(String [] args){

        
    	System.out.println("************* demo de la classe Variable ************* \n");
    	Set<Object> t1 = new HashSet<Object>(Arrays.asList(1,2,3));
    	
        Variable v1 = new Variable("hello", t1 );
        Variable v2 = new Variable("hello", t1 );
        Variable v3 = new Variable("hell", t1 );
        
        
        Set<Object> t2 = new HashSet<Object>(Arrays.asList(11,23));
        Variable v4 = new Variable("hello", t2 );
        System.out.println("Is : "+v1.toString()+" equal to : " +v2.toString()+" ? "+ v1.equals(v2));
        System.out.println("Is : "+v1.toString()+" equal to : null ? "+ v1.equals(null));
        //System.out.println(v1.equals(null));
        //System.out.println(v1.equals(t2));
        System.out.println("Is : "+v1.toString()+" equal to : " +v3.toString()+" ? "+ v1.equals(v3));
        //System.out.println(v1.equals(v3));
        System.out.println("Is : "+v1.toString()+" equal to : " +v4.toString()+" ? "+ v1.equals(v4));
        //System.out.println(v1.equals(v4));

        
        
        System.out.println("\n ************* demo de la classe BooleanVariable ************* \n");
        Variable v6 = new BooleanVariable("bnjj");
        System.out.println(v6.getName()+" " + v6.getDomain());
        
        Variable v7 = new BooleanVariable("bnjj");
        
        System.out.println("Is : "+v6.toString()+" equal to : " +v7.toString()+" ? "+ v6.equals(v7));
        Variable v8 = new BooleanVariable("bonne nuit");
        System.out.println("Is : "+v6.toString()+" equal to : " +v8.toString()+" ? "+ v6.equals(v8));
        System.out.println("Is hashCode of : " + v6.toString() + " equal to hashCode of : " + v7.toString() + " ?: " + (v6.hashCode() == v7.hashCode()));
        System.out.println("Is hashCode of : " + v6.toString() + " equal to hashCode of : " + v8.toString() + " ?: " + (v6.hashCode() == v8.hashCode()));
        
        System.out.println("\n ************* demo de la classe UnaryConstraint ************* \n");
        Set<Object> ssens = new HashSet<>();
        ssens.add(3);
        ssens.add(2);
        UnaryConstraint uContrainte = new UnaryConstraint(v1, ssens);
        Map<Variable, Object> affectation1 = new HashMap<>();
        affectation1.put(v1, 3);
        Map<Variable, Object> affectation2 = new HashMap<>();
        affectation2.put(v1, 1);
        
        
        System.out.println("est ce "+ uContrainte.toString()+" est satisfaite par l'affectation1 "+affectation1.toString()+" ? " + uContrainte.isSatisfiedBy(affectation1));
        System.out.println("est ce "+ uContrainte.toString()+" est satisfaite par l'affectation2 "+affectation2.toString()+" ? " + uContrainte.isSatisfiedBy(affectation2));
        
        System.out.println("\n ************* demo de la classe Implication ************* \n");
        
        Set<Object> ssens1 = new HashSet<>(Arrays.asList(true));
        Set<Object> ssens2 = new HashSet<>(Arrays.asList(2,3));
        
        Implication implication = new Implication(v6, ssens1, v1, ssens2);
        
        Map<Variable, Object> affectation3 = new HashMap<>();
        affectation3.put(v6, true);
        affectation3.put(v8, false);
        affectation3.put(v1,2);
        System.out.println(implication.toString() +" est elle satisfaite par affectation3 "+affectation3.toString()+ "? " + implication.isSatisfiedBy(affectation3));
        
        Map<Variable, Object> affectation4 = new HashMap<>();
        affectation4.put(v6, true);
        affectation4.put(v8, true);
        affectation4.put(v1,1);
        System.out.println(implication.toString() +" est elle satisfaite par affectation4 "+affectation4.toString()+ "? " + implication.isSatisfiedBy(affectation4));
        
        Map<Variable, Object> affectation42 = new HashMap<>();
        affectation42.put(v6, false);
        affectation42.put(v8, true);
        affectation42.put(v1,1);
        System.out.println(implication.toString() +" est elle satisfaite par affectation4 "+affectation42.toString()+ "? " + implication.isSatisfiedBy(affectation42));
        

        //Map<Variable, Object> affectation5 = new HashMap<>();
        //affectation5.put(v6, true);
        //System.out.println(implication.toString() +" est elle satisfaite par affectation5 "+affectation5.toString()+ "? " + implication.isSatisfiedBy(affectation5));
        System.out.println("\n ************* demo de la classe DifferenceConstraint ************* \n");
        DifferenceConstraint diff = new DifferenceConstraint(v1, v3);
        Map<Variable, Object> affectation5 = new HashMap<>();
        affectation5.put(v1, 3);
        affectation5.put(v3, 2);
        
        Map<Variable, Object> affectation6 = new HashMap<>();
        affectation6.put(v1, 1);
        affectation6.put(v3, 1);
        System.out.println(diff.toString() +" est elle satisfaite par affectation5 "+affectation5.toString()+ "? " + diff.isSatisfiedBy(affectation5));
        System.out.println(diff.toString() +" est elle satisfaite par affectation6 "+affectation6.toString()+ "? " + diff.isSatisfiedBy(affectation6));
        
    
    }
}
