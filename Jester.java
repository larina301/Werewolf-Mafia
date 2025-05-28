public class Jester extends Persona{

    public Jester(String N){
        super(N);
    }

    @Override
    public String Role() { return "Jester"; }

    @Override
    public void printAction(){
        System.out.println("As a Jester, You have to be voted out to win");
    }

    @Override
    public void OnDeath(boolean lynched){
        if (lynched){
            if (name.equals("You")) Werewolf_Mafia_Offline.CheckGameStatus(1);
            Debug(name + ", the jester, has won");
        }
    }

}