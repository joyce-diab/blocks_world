package blocksworld;

import bwgeneratordemo.Demo;
import datamining.*;
import modelling.BooleanVariable;

import java.util.*;

public class DemoDataMining { 

    public static void main(String[] args) {
        

        int n =10000;
        int nbPiles = 3;
        int nbBlocs = 5;
        Random random = new Random();

        BooleanBlocksWorld booleanWorld = new BooleanBlocksWorld(nbBlocs, nbPiles);
        BooleanDatabase db = new BooleanDatabase(booleanWorld.getVariables());
        


        for(int i=0; i<n; i++){
            List<List<Integer>> state = Demo.getState(random);

            Set<BooleanVariable> instance  = booleanWorld.createInstance(state);

            db.add(instance);
        }
        System.out.println("\n\n*****************************************************");
        System.out.println("On génère une database de "+n+" mondes de blocs chacun de "+nbBlocs+" blocs et "+nbPiles+" piles.");
        System.out.println("*****************************************************");

        float minfreq = (float) 2/3;
        float minConf = (float) 95/100;

        System.out.println("\n\n*********************Appriori avec frequence minimal 2/3************************");
        Apriori  apriori = new Apriori(db);
        Set<Itemset> itemset = apriori.extract(minfreq);
        System.out.println("les itemset fréquents: ");
        System.out.println(itemset);
        

        System.out.println("\n\n\n******************BruteForceAssociationRuleMiner avec frequence minimal 2/3 et confiance minimal 95/100**************************");
        BruteForceAssociationRuleMiner bf = new BruteForceAssociationRuleMiner(db);
        System.out.println("Regles trouvés:");
        System.out.println(bf.extract(minfreq, minConf));

    }
}
