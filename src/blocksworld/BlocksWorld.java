package blocksworld;

import java.util.*;
import modelling.*;


/**
 * classe qui représente un monde de blocs comme ensembles de variables
 * 
 * -un bloc (entier positive ou nul) est représente par 2 variables 
 *          OnB qui precise le bloc en dessous 
 *          fixedB qui indique si l ebloc est deplacable 
 * 
 * -une pile (entier negative, Pentier positive ou nul) est representee par une varibale 
 *          freeP qui indique si la pile est vide ou  pas
 * 
 * ^^ la premiere pile est la pile P0. le premier bloc de P0 a comme valeur onB =-1
 * 
 */
public class BlocksWorld {
    
    protected int nbBlocs, nbPiles;
    protected Set<Variable> variables;
    protected Map<Integer,Variable> variablesOn;
    protected Map<Integer,BooleanVariable> variablesFixed, variablesFree;

    public BlocksWorld(int nbBlocs, int nbPiles){
        this.nbBlocs = nbBlocs;
        this.nbPiles = nbPiles;

        this.variables = new HashSet<>();
        this.variablesOn = new HashMap<>();
        this.variablesFixed = new HashMap<>();
        this.variablesFree = new HashMap<>();

        initialiseVariables();
    }

    /*initialise les variables du monde de blocs */
    private void initialiseVariables(){
        
        /*domaine de toutes les variables */
        Set<Integer> domain = range(-nbPiles, nbBlocs);
        

        /*on cree les variables */
        for( int i=0; i< nbBlocs; i++){
            String blocName = "onB"+ Integer.toString(i);
            Set<Object> blocDomain = new TreeSet<>(domain);
            blocDomain.remove(i);
            Variable bloc = new Variable(blocName,blocDomain );
            
            variables.add(bloc);
            variablesOn.put(i,bloc);

            BooleanVariable fixedBloc = new BooleanVariable("fixedB" + Integer.toString(i));
            variables.add(fixedBloc);
            variablesFixed.put(i,fixedBloc);
        }

        for(int i = 0; i< nbPiles; i++){
            BooleanVariable freePile = new BooleanVariable("freeP" + Integer.toString(i));
        
            variables.add(freePile);
            variablesFree.put(i,freePile);
        }
        
    }

    /*methode qui renvoie un set de int allant de start jusqu'a end */
    public Set<Integer> range(int start, int end){
        Set<Integer> rangeSet = new TreeSet<>();
        for(int i = start; i< end; i++){
            rangeSet.add(i);
        }
        return rangeSet;
    }

    public int getNbBlocs() {
        return nbBlocs;
    }

    public int getNbPiles() {
        return nbPiles;
    }

    /**
     * 
     * @return un set de toutes les variables
     */
    public Set<Variable> getVariables() {
        return variables;
    }

    /**
     * 
     * @return un map de int : variables de type onB 
     */
    public Map<Integer,Variable> getVariablesOn() {
        return variablesOn;
    }

    /**
     * 
     * @return un map de int : variables de type fixedB 
     */
    public Map<Integer,BooleanVariable> getVariablesFixed() {
        return variablesFixed;
    }

    /**
     * 
     * @return un map de int : variables de type freeP 
     */
    public Map<Integer,BooleanVariable> getVariablesFree() {
        return variablesFree;
    }
    
    /**
     * cree une instance du monde des blocs par affectaions selon la liste des piles
     * @param piles List de List de Integer: ensembles de piles. 
     *              une pile [block on table,......, bloc on top]
     * @return une instance du monde des blocs 
     */
    public Map<Variable,Object> instantiation(List<List<Integer>> piles){
        Map<Variable,Object> instance = new HashMap<>();

        for(int i=0; i<nbPiles; i++){
            List<Integer> pile = piles.get(i);
            Variable freep = variablesFree.get(i);
            
            if(pile.isEmpty()){
                instance.put(freep, true);
            }else{
                instance.put(freep, false);
            }

            for(int j=0; j<pile.size(); j++){
                Integer block = pile.get(j);
                Variable onB = variablesOn.get(block);
                Variable fixedB = variablesFixed.get(block);

                if(j == 0){ //si c le premier bloc de la pile
                    instance.put(onB, -(i+1));
                }else{
                    instance.put(onB, pile.get(j-1));
                }

                if(j == pile.size()-1){ //si c'est le dernier block de la pile
                    instance.put(fixedB,false);
                }else{
                    instance.put(fixedB, true);
                }


            }
        }

        return instance;
    }

    
}
