import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashSet;

public class Character {
    public String name;
    public int votingPoints = 0;
    public boolean isAlive = true;
    public static HashSet<Character> DyingPlayers = new HashSet<>();
    public static HashSet<Character> SavedPlayers = new HashSet<>();
    public static HashSet<String> knownWerewolfs = new HashSet<>();
    public Character boundChar = null;

    static Random rand = new Random();

    public Character(String N){
        name = N;
    }

    public void setName(String N){
        name = N;
    }

    public void DialogueTurn(String PrevDialogue){
        String Dialogue = name + " said something";
        System.out.println(Dialogue);
        PrevDialogue += " " + name + ": " + Dialogue;
    }

    public boolean ActionAvailable(){
        return false;
    }

    public String Role() {
        return "Vilager";
    }

    public void Action(List<Character> list, Character personalTarget){
        // no action here
    }

    public void OnDeath() {
    }

    public void printAction(){
        System.out.println("As a villager, you don't have any special actions");
    }

    public Character Vote(List<Character> list, String convo){
        //based on convo choose a player from the list given
        Character yikes = this;
        while (yikes == this) yikes = list.get(rand.nextInt(list.size()));
        //System.out.println(name + " voted for " + yikes.name);
        return yikes;
    }

    public void checkPlayers(List<Character> list){
        for (Character p : SavedPlayers){
            DyingPlayers.remove(p);
        }
        for (Character p: DyingPlayers) {
            p.isAlive = false;
            if (p.boundChar != null) {
                p.boundChar.isAlive = false;
                System.out.println("As " + p.name + " and " + p.boundChar.name + "were bound, both of them died");
            }
            p.OnDeath();
        }
        
        if (knownWerewolfs.size() <= 0){
            for (Character player: list){
                if (player.Role().equals("Werewolf")) knownWerewolfs.add(player.name);
            }
        }
    }

    public static void Debug(String n){
        System.out.println("DEBUG: " + n);
    }
}
