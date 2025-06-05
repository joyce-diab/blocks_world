package cp;

import java.util.*;
import modelling.*;

public class ArcConsistency {
    //private Set<Constraint> constraints; => pas utile dans notre code
    
    private Set<Constraint> unaryConstraints; 
    private Set<Constraint> binaryConstraints;
    private Set<Variable> variables;

    /*on a choisi d'avoir unary et binary constrainsts comme de attributs 
     * pour eviter de parcourir tous les contraints a chaque fois qu'on a juste besoin des unary ou des binary
     * (optimisation qui permet aux methodes d'etre plus rapide )
     * (pas optimale en point de vu d'espace de stockage (constraintes stocker dans ) )
     */
    public ArcConsistency(Set<Constraint> constraints){
        //this.constraints = constraints;  => pas utile dans notre code
        
        this.unaryConstraints = new HashSet<>();
        this.binaryConstraints = new HashSet<>();
        this.variables = new HashSet<>(); 
        //^ les varibales concernéss par nos contraintes seulement (utilité expliquée dans la methode ac1)
        unaryAndBinaryInitialisation(constraints);
        
    }

    /*permet d'initialiser  unaryConstraints et binaryConstraints afin de trier les contraintes*/
    private void unaryAndBinaryInitialisation(Set<Constraint> constraints){
        
        for (Constraint c : constraints){
            variables.addAll(c.getScope());
            if (c.getScope().size() == 1){
                unaryConstraints.add(c);
            }
            else if (c.getScope().size() == 2){
                binaryConstraints.add(c);
            }
            else{
                throw new IllegalArgumentException("Ni unaire ni binaire");
            }
        }
    }

    /*
     * méthode qui supprimer en place les valeurs v des domaines pour lesquelles il existe une contrainte unaire non satisfaite par v. 
     * renvoie false si au moins un domaine a été vidé, true sinon
     */
    public boolean enforceNodeConsistency (Map<Variable, Set<Object>> domains){
        boolean res = true;
        
        for (Variable var : variables){
            
            Set<Object> aSupprimer = new HashSet<>(); //les vars a supprimers de varibales
            
            for ( Object value : domains.get(var)){
                for ( Constraint c : unaryConstraints){
                    
                    Set<Variable> variableConcerneeParContrainte = c.getScope();

                    Set<Variable> maVariable = new HashSet<>(); //transformer ma varibale en hashset
                    maVariable.add(var);
                    
                    //si ma varibale contient tous les varibales concernee par la contrainte
                    if (maVariable.containsAll(variableConcerneeParContrainte)){
                        
                        Map<Variable,Object> n = new HashMap<>();
                        n.put(var,value);
                        
                        //si la contrainte n'est pas satisfaite par cette affectation de valeur => on supprime la valeur
                        if (!(c.isSatisfiedBy(n))){
                            aSupprimer.add(value);
                        } 
                    }
                }
            }
            
            for (Object value : aSupprimer){
                domains.get(var).remove(value);
            }
            
            if(domains.get(var).isEmpty()){
                res = false;
            }
        }
       
        return res;
    }

    /*
     * rprend en arguments: une variable v1, son domaine D1, une autre variable v2, et son domaine D2. 
     * supprime toutes les valeurs v1 de D1 pour lesquels il n’existe aucune valeur v2 de D2 supportant v1 
     *      pour toutes les contraintes portant sur v1 et v2
     * renvoie true si au moins une valeur a été supprimée de D1, false sinon
     */
    public boolean revise (Variable v1, Set<Object> d1, Variable v2, Set<Object> d2){
        boolean del = false; //si une valeur a ete supprimee ou pas
        Set<Variable> vs = new HashSet<>(); //mes varibales
        vs.add(v1);
        vs.add(v2);

        Set<Object> aSupprimer = new HashSet<>(); 

        //pour chaque valeur de v1
        for (Object value1 : d1){
            boolean viable = false;
            //pour chaque valeur de v2
            for(Object value2 : d2){
                boolean allSatisfied = true;
                for(Constraint c : binaryConstraints){

                    Set<Variable> variableConcernee = c.getScope();
                    
                    //si la contrainte concerne que v1 et v2
                    if (vs.containsAll(variableConcernee)){ 
                        
                        Map<Variable,Object> n = new HashMap<>();
                        n.put(v1,value1);
                        n.put(v2, value2);

                        if (!(c.isSatisfiedBy(n))){
                            allSatisfied = false;
                            break;
                        } 
                    }
                }
                if(allSatisfied){
                    viable = true;
                    break;
                }
            }
            if(viable == false){
                aSupprimer.add(value1);
                del = true;

            }
        }
        for (Object value : aSupprimer){
            d1.remove(value);
        }

        return del;
    }

    public boolean ac1( Map<Variable, Set<Object>>  domains){
        
        if (! enforceNodeConsistency(domains)){
            return false;
        }

        boolean change = false;
        do {
            change = false;
            /*pour une meilleur optimisation, on a choisi un domaine de variables plus reduit */
            /*on itere que sur les variables concernées par nos contraintes et pas tous les varibales du domaine
            vu que les variables qui vont être reduits sonr celles concernés par les contraintes*/

            /*exemple si notre domaine contient une variable d qui ne figure pas dans aucune contrainte donc revise 
             * ne va rien modifier dans d et change reste a false
             */

            for(Variable xi : variables){ 
                for(Variable xj : variables){
                    if(revise(xi,domains.get(xi), xj, domains.get(xj))){
                        change = true;
                    }
                }
            } 
        } while (change); 

        for(Variable x : domains.keySet()){ 
            if(domains.get(x).isEmpty()){
                return false;
            }
        }

        return true;
    }
}
