import java.util.List;

public class Executioner extends Character{

    public Character personalTarget;

    public Executioner(String N){
        super(N);
        personalTarget = Werewolf_Mafia_Offline.PickRandomPlayer(p -> p != this);
    }

    @Override
    public String Role() { return "Executioner"; }

    @Override
    public void printAction(){
        System.out.println("As an Executioner, You have lynch out your target to win. If they die, you lose");
    }

    @Override
    public void Action(List<Character> list, Character target){
        if (target.equals(personalTarget)){
            if (name.equals("You")) Werewolf_Mafia_Offline.CheckGameStatus(0);
            Debug(name + ", the Executioner, has won");
        }
    }

}