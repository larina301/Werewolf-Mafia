import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Character {
    public String name;
    public int votingPoints = 0;
    public boolean isAlive = true;
    public static List<Character> DyingPlayers = new ArrayList<>();
    public static List<Character> SavedPlayers = new ArrayList<>();
    public static List<String> knownWerewolfs = new ArrayList<>();

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
        for (int i = SavedPlayers.size()-1; i >= 0; i--){
            int index = DyingPlayers.indexOf(SavedPlayers.get(i));
            while (index != -1){
                DyingPlayers.remove(index);
                index = DyingPlayers.indexOf(SavedPlayers.get(i));
            } 
        }
        for (Character p: DyingPlayers) p.isAlive = false;

        //KEEPS ADDING THE SAME PLAYER IF CHECKING MULTIPLE TIMES
        //Fix it or trust AI to not check the same person more than once?
        if (knownWerewolfs.size() <= 0){
            for (Character player: list){
                if (player.Role().equals("Werewolf")) knownWerewolfs.add(player.name);
            }
        }
    }
}
