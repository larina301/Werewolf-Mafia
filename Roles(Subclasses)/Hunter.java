import java.util.List;

public class Hunter extends Character{

    public static boolean isDying = false;

    public Hunter(String N){
        super(N);
    }

    @Override
    public boolean ActionAvailable(){
        return isDying;
    }

    @Override
    public void Action(List<Character> list, Character target){
        if (!ActionAvailable()) return;
        if (target == null){
            target = Werewolf_Mafia_Offline.PickRandomPlayer(p -> p != this);
        }
        target.isAlive = false;
        Debug(name + " as the " + Role() + " decided to take " + target.name + " down with them");
    }

    @Override
    public void OnDeath(boolean lynched) {
        isDying = true;
        if (name.equals("You")) {
            Werewolf_Mafia_Offline.CheckUserAction(this);
        } else {
            this.Action(Werewolf_Mafia_Offline.players, null);
        }
    }

    @Override
    public String Role() { return "Hunter"; }

    @Override
    public void printAction(){
        System.out.println("As a Hunter, You can take down another player with you upon death");
    }

}