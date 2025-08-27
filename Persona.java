import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Persona {
    public String name;
    public int votingPoints = 0;
    public boolean isAlive = true;
    public String personality = "";
    public static List<Persona> DyingPlayers = new ArrayList<>();
    public static List<Persona> SavedPlayers = new ArrayList<>();
    public static List<String> knownWerewolfs = new ArrayList<>();
    public Persona boundChar = null;

    static Random rand = new Random();

    public static List<String> personalities = new ArrayList<>(List.of(
    "they hates anybody whose name has the letter 'A'", "is extremely judgmental", "their father died recently and they're still not over it",
    "likes impersonating Sherlock Holmes", "they are a mother of three kids, they will tell everybody about them", "is very secretive",
    "likes accusing and instigating arguments", "they hate the role they got", "they only really want to live", "they might be suicidal",
    "they believe to be the main character", "they want to be the main antagonist", "tries to derail the conversation and cause chaos",
    "they are very hungry all the time", "they accuse a new person each round", "randomly speaks spanish"
    )); //so far only 16 entries, needs more than 16

    public Persona(String N){
        name = N;
        personality = personalities.remove(rand.nextInt(personalities.size()));
    }

    public void setName(String N){
        name = N;
    }

    public String getPersonality(){
        return personality;
    }

    public void DialogueTurn(){
        String Dialogue = name + ": " + GenerateResponse(-1) + "\n";
        System.out.println(Dialogue);        
        Werewolf_Mafia_Offline.AddLogEntry(Dialogue);
    }

    public boolean ActionAvailable(){
        return false;
    }

    public String Role() {
        return "Vilager";
    }

    public void Action(List<Persona> list, Persona personalTarget){
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

    public Persona Vote(List<Persona> list){
        //based on convo choose a player from the list given
        Persona yikes = null;
        do {
            String nameVoting = GenerateResponse(0);
            for (Persona p: list){
                if (p.name.equals(nameVoting))  yikes = p;
                break;
            }
        } while (yikes == null);
        
        //while (yikes == this) yikes = list.get(rand.nextInt(list.size()));
        Debug(name + " voted for " + yikes.name);
        return yikes;
    }

    public void checkPlayers(List<Persona> list){
        for (Persona p : SavedPlayers){
            DyingPlayers.remove(p);
        }
        for (Persona p: DyingPlayers) {
            p.isAlive = false;
            if (p.boundChar != null) {
                p.boundChar.isAlive = false;
                System.out.println("As " + p.name + " and " + p.boundChar.name + " were bound, both of them died");
            }
            p.OnDeath(false);
            Werewolf_Mafia_Offline.AddLogEntry(p.name + " has died during the night\n");
        }
        
        if (knownWerewolfs.size() <= 0){
            for (Persona player: list){
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
        EnsureLog();
        log += text;
    }

    public String GenerateResponse(int Case) {
        EnsureLog();
        String verb = log.length() > 100 ? "Continue" : "Begin";
        String convoCont = log + " " + verb +
        " the conversation as " + name + " in one sentence. Just write what you would say without indirect quoting.";

        String lynchCont = log + " Choose a character to lynch as " + name + ". Just write the name";
        String ChoosePlayer = " Choose a character to ";

        String situation = "";
            switch (Case) {
                case 0:
                    situation = lynchCont;
                    break;
            
                case 1:
                    situation = ChoosePlayer;
                    break;

                default:
                    situation = convoCont;
                    break;
            }
        
        return Werewolf_Mafia_Offline.SendRequestToServer(situation);
    }

    public void EnsureLog() {
        if (log == null)
            log = Werewolf_Mafia_Offline.GenerateInitialPrompt(this);
    }

    String log;
}
