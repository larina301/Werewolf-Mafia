public class Executioner extends Character{

    public Character personalTarget;

    public Executioner(String N){
        super(N);
        personalTarget = Werewolf_Mafia_Offline.PickRandomPlayer(p -> p != this);
        //System.out.println(N + "'s personal target is " + personalTarget.name);
    }

    @Override
    public boolean ActionAvailable(){
        return false;
    }

    @Override
    public String Role() { return "Executioner"; }

    @Override
    public void printAction(){
        System.out.println("As an Executioner, You have lynch out your target to win. If they die, you lose");
    }

    

}