
public class Mayor extends Character{

    public Mayor(String N){
        super(N);
    }

    @Override
    public boolean ActionAvailable(){
        return false;
    }

    @Override
    public String Role() { return "Mayor"; }

    @Override
    public void printAction(){
        System.out.println("As a Mayor, You have double the votes when Lynching");
    }

}