package planning;

import java.util.*;
import modelling.*;

public interface Planner {
    
    public List<Action> plan();
    public Set<Action> getActions();
    public Map<Variable, Object> getInitialState();
    public Goal getGoal();
    public void activateNodeCount(boolean activate);
    public int getNodeCount();

}
