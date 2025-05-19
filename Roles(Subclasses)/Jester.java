public class Jester extends Character{

    public Jester(String N){
        super(N);
    }

    @Override
    public boolean ActionAvailable(){
        return false;
    }

    @Override
    public String Role() { return "Jester"; }

    @Override
    public void printAction(){
        System.out.println("As a Jester, You have to be voted out to win");
    }

}