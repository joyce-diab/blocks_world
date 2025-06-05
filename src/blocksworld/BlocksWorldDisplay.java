package blocksworld;

import java.util.*;

import javax.swing.*;

import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWIntegerGUI;
import bwui.BWComponent;

import modelling.*;
import planning.Action;

public class BlocksWorldDisplay {
    
    
    private BlocksWorld bw;
    private int n;
    private BWIntegerGUI gui;
    private BWComponent<Integer> component;
    private JFrame frame;

    public BlocksWorldDisplay(int n, String title, BlocksWorld bw){
        this.gui = new BWIntegerGUI(n);
        this.frame = new JFrame("test");
        this.bw = bw;
        this.n= n;
    }

    /** 
     * cree un BWstate a partir d'une instance
     */
    public BWState<Integer> makeBwState(Map<Variable, Object> state){
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);
        for (int b = 0; b < n; b++) {
            Variable onB = bw.variablesOn.get(b); // get instance of Variable for "on_b"
            int under = (int) state.get(onB);
            if (under >= 0) { // if the value is a block (as opposed to a stack)
                builder.setOn(b, under);
            }
        }
        return builder.getState();
    }

    /**
     * 
     * visualise les effets apres chauqe action sur l'instance
     */
    public void simulatePlan(Map<Variable, Object> initialState, List<Action> plan){
        
        displayState(initialState);

        Map<Variable,Object> state = initialState;
        for (Action a: plan) {
            try { Thread.sleep(1_000); }
            catch (InterruptedException e) { e.printStackTrace(); }
            state=a.successor(state);
            component.setState(makeBwState(state));
        }
        System.out.println("Simulation of plan done."+ plan.size() + " moves.");
    }

    /**
     * affiche/visualise l'etat du monde state
     */
    public void displayState(Map<Variable, Object> state){
        BWState<Integer> bwState = makeBwState(state);
        if(this.component == null) {
            this.component = gui.getComponent(bwState);
        }else{
            this.component.setState(bwState);
        }
        
        frame.add(component);
        frame.pack();
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setFrame(JFrame f){
        this.frame=f;
    }
}
