package modelling;
import java.util.*;

public class Variable {
    
    protected String name;
    protected Set<Object> domain;

    public Variable(String name, Set<Object> domain){
        this.name = name;
        this.domain= domain;
    }

    @Override
    public boolean equals(Object o){ // 2 variables sont égales si elles ont le meme "name"
        if(o == null || !(o instanceof Variable)){ //si c pas une variable donc pas egale
            return false;
        }
        Variable v = (Variable) o; // pour pouvoir acceder a name (cast object en variable)
        return v.name.equals(this.name); 
    }

    @Override
    public int hashCode(){ //hashage en fonction du nom
        return Objects.hash(name);
    }

    public String getName(){
        return this.name;
    }

    public Set<Object> getDomain(){
        return this.domain;
    }

    @Override
    public String toString(){
        return this.name;
    }


}
