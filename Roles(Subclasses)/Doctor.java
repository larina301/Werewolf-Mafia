import java.util.List;

public class Doctor extends Character{

    public Doctor(String N){
        super(N);
    }

    @Override
    public void Action(List<Character> list, Character target){
        if (target == null) target = Werewolf_Mafia_Offline.PickRandomPlayer(p -> true);
        SavedPlayers.add(target);
        Debug(name + " as the " + Role() + " decided to save " + target.name);
    }

    @Override
    public boolean ActionAvailable(){
        return true;
    }

    @Override
    public String Role() { return "Doctor"; }

    @Override
    public void printAction(){
        System.out.println("As a Doctor, you may save 1 person tonight");
    }
}