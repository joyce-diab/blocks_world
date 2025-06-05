package cp;

import java.util.*;
import modelling.*;

public interface ValueHeuristic {

    public List<Object> ordering (Variable var,  Set<Object> domain);
    //retourne une liste contenant les valeurs du domaine, ordonnées selon l’heuristique 
    //(la meilleure enpremier)
} 