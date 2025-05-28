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
    public Character boundChar = null;

    static Random rand = new Random();

    public Character(String N){
        name = N;
        log = Werewolf_Mafia_Offline.GenerateInitialPrompt(this);
    }

    public void setName(String N){
        name = N;
    }

    public void DialogueTurn(){
        
        String Dialogue = GenerateResponse();
        System.out.println(Dialogue);
        Werewolf_Mafia_Offline.conversation += " " + name + ": " + Dialogue + "; ";
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

    public void OnDeath(boolean lynched) {
        if (name.equals("You")) {
            System.out.println("\n you have died");
            Werewolf_Mafia_Offline.ContGame();
        }
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
                System.out.println("As " + p.name + " and " + p.boundChar.name + " were bound, both of them died");
            }
            p.OnDeath(false);
            Werewolf_Mafia_Offline.conversation += " " + p.name + " has died; ";
        }
        
        if (knownWerewolfs.size() <= 0){
            for (Character player: list){
                if (player.Role().equals("Werewolf")) knownWerewolfs.add(player.name);
            }
        }

        DyingPlayers.clear();
        SavedPlayers.clear();

    }

    public static void Debug(String n){
        System.out.println("DEBUG: " + n);
    }

    public void AddLogEntry(String text) {
        log += text;
    }

    public String GenerateResponse() {
        return Werewolf_Mafia_Offline.SendRequestToServer(log + name + ": ");
    }

    String log;
}
