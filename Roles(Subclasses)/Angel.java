public class Angel extends Character{

    public Angel(String N){
        super(N);
    }

    @Override
    public boolean ActionAvailable(){
        return false;
    }

    @Override
    public String Role() { return "Angel"; }

    @Override
    public void printAction(){
        System.out.println("As a Angel, you can bind two people's lifespans together");
    }

}