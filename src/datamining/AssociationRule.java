package datamining;

import java.util.*;
import modelling.*;

/*classe qui represente les règles d'association de la forme premise -> conclusion
 * et ayant une frequence et une confiance
 */
public class AssociationRule {
    
    private Set<BooleanVariable> premise, conclusion;
    private float frequency, confidence;

    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float frequency, float confidence){
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = frequency;
        this.confidence = confidence;
    }


    public Set<BooleanVariable> getPremise() {
        return premise;
    }

    public Set<BooleanVariable> getConclusion() {
        return conclusion;
    }

    public float getFrequency() {
        return frequency;
    }

    public float getConfidence() {
        return confidence;
    }

    @Override
    public String toString(){
        return "R: "+premise+" -> "+conclusion+" (frequency: "+frequency +", confidence: "+confidence+")";
    }
}
