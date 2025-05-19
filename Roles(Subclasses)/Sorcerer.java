import java.util.List;

public class Sorcerer extends Character{

    public Character TheSeer = null;

    public Sorcerer(String N){
        super(N);
    }

    @Override
    public void Action(List<Character> list, Character target){
        if (target == null) {
            target = Werewolf_Mafia_Offline.PickRandomPlayer(p -> !(p instanceof Werewolf) && p != this);
        }

        if (target instanceof Seer) {
            if (name.equals("You")) System.out.println(target.name + " is the Seer");
            TheSeer = target;
        }
        else if (name.equals("You")) System.out.println(target.name + "is not the Seer");

        System.out.println(name + " checked if " + target.name + " is a Seer\n    the Seer found is:" + TheSeer);
    }

    @Override
    public boolean ActionAvailable(){
        return true;
    }

    @Override
    public String Role() { return "Sorcerer"; }

    @Override
    public void printAction(){
        System.out.println("As a Sorcerer, you may check 1 person for their Seer status tonight\nThe werewolves are: " + knownWerewolfs);
    }

}