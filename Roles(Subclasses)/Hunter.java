public class Hunter extends Character{

    public Hunter(String N){
        super(N);
    }

    @Override
    public boolean ActionAvailable(){
        return false;
    }

    @Override
    public String Role() { return "Hunter"; }

    @Override
    public void printAction(){
        System.out.println("As a Hunter, You can take down another player with you upon death");
    }

}