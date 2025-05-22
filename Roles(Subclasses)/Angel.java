import java.util.List;
import java.util.Scanner;

public class Angel extends Character{

    boolean actionDone = false;
    Character target2;

    public Angel(String N){
        super(N);
    }

    @Override
    public void Action(List<Character> list, Character target){
        if (actionDone) return;
        list.remove(target);
        if (target != null) {
            target2 = Werewolf_Mafia_Offline.findPlayer("Choose the second target", list);
        }
        else{
            target = Werewolf_Mafia_Offline.PickRandomPlayer(p -> true);
            final Character finalTarget = target;
            target2 = Werewolf_Mafia_Offline.PickRandomPlayer(p -> !p.equals(finalTarget));
        }
        actionDone = true;
        Debug(name + ", the " + Role() + ", decided to bind " + target.name + " and " + target2.name);

        target.boundChar = target2;
        target2.boundChar = target;
    }

    @Override
    public boolean ActionAvailable(){
        return !actionDone;
    }

    @Override
    public String Role() { return "Angel"; }

    @Override
    public void printAction(){
        System.out.println("As a Angel, you can bind two people's lifespans together");
    }

}