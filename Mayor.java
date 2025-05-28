
public class Mayor extends Persona{

    public Mayor(String N){
        super(N);
    }

    @Override
    public String Role() { return "Mayor"; }

    @Override
    public void printAction(){
        System.out.println("As a Mayor, You have double the votes when Lynching");
    }

}