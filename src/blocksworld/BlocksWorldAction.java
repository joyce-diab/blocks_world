package blocksworld;

import java.util.*;
import planning.*;
import modelling.*;

/*
 * classe qui genere les actions possibles dans un monde de blocs
 * sous formz de basic actions avec des preconditions et des effets
 */

public class BlocksWorldAction  extends BlocksWorld{
    
    private Set<Action> basicActions;

    public BlocksWorldAction(int nbBlocs, int nbPiles){
        super(nbBlocs, nbPiles);
        this.basicActions = new HashSet<>();
        initialiseBasicActions();
    }


    private void initialiseBasicActions(){

        
        for(Integer intB1 : variablesOn.keySet()){
            Variable onB1 = variablesOn.get(intB1);

            for(Integer intB2 : variablesOn.keySet()){

                if(intB1 != intB2){
                    
                    for(Integer intB3 : variablesOn.keySet()){

                        if(intB3 != intB2 && intB3 != intB1){
                            /*CAS 1*/
                            //b1 est sur b2 et se deplace vers sur b3
                            Map<Variable,Object> prec = new HashMap<>();

                            prec.put(onB1, intB2);
                            prec.put(variablesFixed.get(intB1), false);
                            prec.put(variablesFixed.get(intB3), false);

                            Map<Variable,Object> effect = new HashMap<>();

                            effect.put(onB1, intB3);
                            effect.put(variablesFixed.get(intB2), false);
                            effect.put(variablesFixed.get(intB3), true);

                            basicActions.add(new BasicAction(prec, effect, 1));


                        }
                    }

                    
                    for(Integer intP : variablesFree.keySet()){
                        BooleanVariable freePile = variablesFree.get(intP);
                        /*CAS 2 */
                        //b1 est sur b2 et se deplace vers sur p
                        Map<Variable,Object> prec2 = new HashMap<>();

                        prec2.put(onB1, intB2);
                        prec2.put(variablesFixed.get(intB1), false);
                        prec2.put(freePile, true);

                        Map<Variable,Object> effect2 = new HashMap<>();

                        effect2.put(onB1, -(intP+1));
                        effect2.put(variablesFixed.get(intB2), false);
                        effect2.put(freePile, false);

                        basicActions.add(new BasicAction(prec2, effect2, 1));

                        /*CAS 3 */
                        //b1 est sur p et se deplace vers sur b2

                        Map<Variable,Object> prec3 = new HashMap<>();

                        prec3.put(onB1, -(intP+1));
                        prec3.put(variablesFixed.get(intB1), false);
                        prec3.put(variablesFixed.get(intB2), false);

                        Map<Variable,Object> effect3 = new HashMap<>();

                        effect3.put(onB1,intB2);
                        effect3.put(freePile, true);
                        effect3.put(variablesFixed.get(intB2), true);

                        basicActions.add(new BasicAction(prec3, effect3, 1));

                    }

                    
                }

            }

            /*CAS 4 */
            //b1 est sur p1 et se deplace vers sur p2
            for(Integer intP1 : variablesFree.keySet()){
                BooleanVariable freePile1 = variablesFree.get(intP1);
                
                for(Integer intP2 : variablesFree.keySet()){
                    BooleanVariable freePile2 = variablesFree.get(intP2);

                    if(intP1 != intP2){
                        Map<Variable,Object> prec4 = new HashMap<>();

                        prec4.put(onB1, -(intP1+1));
                        prec4.put(variablesFixed.get(intB1), false);
                        prec4.put(freePile2, true);

                        Map<Variable,Object> effect4 = new HashMap<>();

                        effect4.put(onB1, -(intP2+1));
                        effect4.put(freePile1, true);
                        effect4.put(freePile2, false);

                        basicActions.add(new BasicAction(prec4, effect4, 1));
                    }
                }
                

                
            }
        }


    }


    public Set<Action> getBasicActions(){
        return basicActions;
    }
}
