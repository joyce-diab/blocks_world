package cp;

import modelling.*;
import java.util.*;


public interface Solver {
    public Map<Variable, Object> solve(); //retourne une solution de type Map<Variable, Object>, null s'il y en a pas
}
