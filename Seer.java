import java.util.ArrayList;
import java.util.List;

public class Seer extends Persona{

    public List<Persona> WerewolvesFound = new ArrayList<>();

    public Seer(String N){
        super(N);
    }

    @Override
    public void Action(List<Persona> list, Persona target){
        if (target == null) {
            target = Werewolf_Mafia_Offline.PickRandomPlayer(p -> p != this);
        }

        if (target.Role().equals("Werewolf")) {
            if (name.equals("You")) System.out.println(target.name + " is a Werewolf");
            WerewolvesFound.add(target);
        }
        else if (name.equals("You")) System.out.println(target.name + " is not a Werewolf\n");
        Debug(name + " checked if " + target.name + " is a werewolf\n    the werewolves found are:" + WerewolvesFound);
    }

    @Override
    public boolean ActionAvailable(){
        return true;
    }

    @Override
    public String Role() { return "Seer"; }

    @Override
    public void printAction(){
        System.out.println("As a Seer, you may check 1 person for their Werewolf status tonight");
    }

}