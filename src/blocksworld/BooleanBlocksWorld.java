package blocksworld;

import java.util.*;
import modelling.*;


/**
 * represente une un monde de blocks avec seulement des BooleanVaribales
 */
public class BooleanBlocksWorld {
    
    private int nbBlocs, nbPiles;
    private Set<BooleanVariable> base;

    public BooleanBlocksWorld (int nbBlocs, int nbPiles){
        this.nbBlocs = nbBlocs;
        this.nbPiles = nbPiles;
        this.base= new HashSet<>();
        initialisation();
    }

    private void initialisation(){

        for(int b=0; b< nbBlocs; b++){
            for(int b2=0; b2<nbBlocs; b2++){

                if(b!=b2){
                    base.add(new BooleanVariable("on"+b+","+b2));
                }
            }

            for(int p=0; p<nbPiles; p++){
                base.add(new BooleanVariable("on-table"+b+","+p));
                base.add(new BooleanVariable("freeP"+p));
            }

            base.add(new BooleanVariable("fixedB"+b));
        }
    } 

    /**
     * renvoie une instance du monde avec les BooleanVariables a true seulement
     */
    public Set<BooleanVariable>createInstance(List<List<Integer>> piles){
        Set<BooleanVariable> instance = new HashSet<>();

        for(int p=0; p<piles.size(); p++){

            List<Integer> pile = piles.get(p);

            if(pile.isEmpty()){
                instance.add(new BooleanVariable("freeP"+p));
            }else{

                for(int b=0; b<pile.size();b++){
                    int block = pile.get(b);

                    if(b==0){
                        instance.add(new BooleanVariable("on-table"+block+","+p));
                    }else{
                        instance.add(new BooleanVariable("on"+block+","+pile.get(b-1)));
                    }

                    if(b != pile.size()-1){
                        instance.add(new BooleanVariable("fixedB"+block));
                    }
                }
            }
        }
        return instance;
    }

    public Set<BooleanVariable> getVariables(){
        return base;
    }
}
