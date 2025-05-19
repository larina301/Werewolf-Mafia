import java.util.List;

public class Werewolf extends Character{

    public Werewolf(String N){
        super(N);
    }

    @Override
    public void Action(List<Character> list, Character target){
        if (target == null) {
            target = Werewolf_Mafia_Offline.PickRandomPlayer(p -> !(p instanceof Werewolf));
        }
        DyingPlayers.add(target);
        System.out.println(name + " as the " + Role() + " decided to kill " + target.name);
    }

    @Override
    public boolean ActionAvailable(){
        return true;
    }

    @Override
    public String Role() { return "Werewolf"; }

    @Override
    public void printAction(){
        System.out.println("As a Werewolf, you may kill 1 person at night\nThe werewolves are: " + knownWerewolfs);
    }
}