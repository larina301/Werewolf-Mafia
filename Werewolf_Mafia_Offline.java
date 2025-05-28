import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Predicate;

public class Werewolf_Mafia_Offline {

    //bot names and roles to randomly choose from
    static List<String> nameList;
    static List<Class> RolesList, extraRoles;

    //list of the players and their stats
    static List<Character> players = new ArrayList<>();
    //the entire conversation for AI to review to contribute
    static public String conversation;
    //checks what round it currently is
    static int round;
    static public boolean GameOn;

    static Scanner in = new Scanner(System.in);
    static Random rand = new Random();

    //starts the game by asking the size of the game
    public static void main(String[] args){
        int max = 16;
        int min = 6;

        while (true){
            ResetGame();
            for (int j = 0; j < 10; j++) System.out.println(".");
            System.out.println("How many players do you wish to have in the game? 6 min - 16 max\nEnter 0 to exit");
            int PlayerCount = in.nextInt();
            //checks if amount given is more or less than the max/min stated and sets it to that
            if (PlayerCount > max) {
                System.out.println("maximum amount selected (" + max + ")");
                PlayerCount = max;
            }
            if (PlayerCount < min){
                if (PlayerCount == 0) break;
                System.out.println("minimum amount selected (" + min + ")");
                PlayerCount = min;
            }
            //creates the amount of roles based on player count
            constructRoles(PlayerCount);

            //tells main player their role in the game
            System.out.println(".\n".repeat(5));
            if (players.size() > 0) System.out.println("You are a... " + players.getLast().Role());
            System.out.println(".\n".repeat(5));

            //starts the game officialy
            GameOn = true;
            BeginGame();
        }
    }

    //resets the game by settting the default values
    static void ResetGame(){
        nameList = new ArrayList<>(List.of("Jack", 
        "Ethan", "Lucas", "Noah", "Oliver", "Henry", "Leo", "Samuel", "Benjamin", "Elijah", 
        "Ava", "Emma", "Sophia", "Isabella", "Mia","Charlotte", "Amelia", "Lily", "Elysia",
        "Scarlett", "Grace", "Alex", "Jordan", "Riley", "Taylor", "Casey", "Morgan", "Skyler", 
        "Jamie", "Avery", "Quinn", "Orion", "Zephyr", "Nyx", "Astra", "Solis", "Thorne", "Seraph", 
        "Vesper", "Alaric"));

        RolesList = new ArrayList<>(List.of(Seer.class, Doctor.class, Sorcerer.class));
        extraRoles = new ArrayList<>(List.of(
            Mayor.class, 
            Jester.class, 
            Executioner.class, 
            Hunter.class, 
            Angel.class, 
            Doctor.class));
        players.clear();

        conversation = "";
        round = 0;
    }

    //decides what roles can be randomly picked depending on the size of the group
    static void constructRoles(int PlayerCount){
        int r;

        //playercount = 6
        //doctor seer sorcerer + villager + villager + villager + were + were

        PlayerCount -= 3;
        //creates the amount of villagers and werewolfs in the party based on percent/ratio
        for (int i = 0; i < (int) Math.floor(PlayerCount * 0.50); i++) RolesList.add(Character.class);
        for (int i = 0; i < (int) Math.ceil(PlayerCount * 0.20); i++) RolesList.add(Werewolf.class);

        PlayerCount += 3;
        //adds extra roles if there's still space
        while (RolesList.size() < PlayerCount && extraRoles.size() > 0){
            r = rand.nextInt(extraRoles.size());
            RolesList.add(extraRoles.remove(r));
        }

        //creates each player's profile
        for (int i = 0; i < PlayerCount; i++) formPlayer();
        if (players.size() > 0) players.getLast().setName("You");
    }

    //forms the bot players
    static void formPlayer(){
        //randomly choosing a name for character
        int r = rand.nextInt(nameList.size());
        String name = nameList.remove(r);
        
        //randomly choose a role for character
        r = rand.nextInt(RolesList.size());
        Class<Character> role = RolesList.remove(r);
        try {
            Constructor<Character> constructor = role.getConstructor(String.class);
            Character player = constructor.newInstance(name);
            players.add(player);
        } catch (Exception e) {
        }
        //making and adding the profile to character list
    }

    //just starts the game
    static void BeginGame(){

        //states who the players are in the game
        System.out.println("The players are: ");
        // System.out.print(players.stream().map(p -> p.name).join(", "));
        for (int i = 0; i < players.size() - 1; ++i) {
            System.out.print(players.get(i).name + (i == players.size() - 2 ? ", and " : ", "));
        }
        if (players.size() > 0) System.out.print(players.getLast().name);

        System.out.println("\nEverybody gets to talk once during the three rounds.");
        System.out.println("Press enter to continue");
        in.nextLine();

        //starts the day cycles for the game
        // !!!FIX !!! change condition
        if (players.size() > 0) players.getLast().checkPlayers(players);
        while (GameOn){
            DayTimeCycle();
            NightTimeCycle();
        }

    }

    //the day time cycle of three rounds of conversation
    static void DayTimeCycle(){

        round++;

        if (round > 1){
            //states who the players are in the game
            System.out.println("These players returned today... ");
            // System.out.print(players.stream().map(p -> p.name).join(", "));
            ListPlayers(players);
            
        }

        //makes sure to not take previous text as response
        in.nextLine();

        for (int i = 1; i <= 3; i++){

            //three rounds total to converse and argue
            System.out.println("\nRound " + i + ": the conversation begins...");
            //gives each character a turn to speak
            for (Character user: players){
                if (user.name.equals("You")){
                    //the character is the player, they get custom
                    System.out.print("You: ");
                    conversation += " Main Player: " + in.nextLine();
                }
                else{
                    //allows bot to continue the conversation as the character
                    user.DialogueTurn();
                }
            }
        }
        // can't lynch on the first day
        if (round > 1){
            System.out.println("\nit is now time to vote to lynch a player...");
            VotingRound(players);
        }
        System.out.println("\nDaytime of day " + round + " has ended\n");
    }

    //the night time cycle where users gets to use their actions
    static void NightTimeCycle(){
        //String response = "";
        for (Character user: players){

            // checking who the user is
            if (user.name.equals("You")){
                // letting the player know their action
                    CheckUserAction(user);
                }
            else{
                if (user.ActionAvailable()) user.Action(players, null);
                System.out.println();
            }
        }

            System.out.println("\nNighttime of day " + round + " has ended\n");
            CheckPlayerStatus();
            System.out.println("\nPlease press enter to continue\n");
        }

    //player choosing their action is the player
    static void CheckUserAction(Character user){
        System.out.println("You are " + user.Role());
        user.printAction();
            if (user.ActionAvailable()){
                System.out.println("\nOut of these players:");
                ListPlayers(players);
                user.Action(players, findPlayer("\nPlease choose a target", players));
            }
        }

    //allows each player to vote for each other
    static void VotingRound(List<Character> suspects){
        Character ExecutionerPresent = null;
        //creates a list for characters with most voting points
        List<Character> newSuspects = new ArrayList<>();
        //resets the voting points of each character alive
        for (Character characters: players){
            characters.votingPoints = 0;
        }
        System.out.println("Here are the characters you can lynch: ");
        ListPlayers(suspects);
        // adds each players vote
        for (Character user: players){
            if (user.name.equals("You")){
                findPlayer("\nPlease choose a player to lynch", suspects).votingPoints++;
            }
            //mayors get double voting points
            else if (user.Role().equals("Mayor")) user.Vote(players, conversation).votingPoints += 2;
            else user.Vote(suspects, conversation).votingPoints++;

            if (user.Role().equals("Executioner")) ExecutionerPresent = user;
        }
        //edits the NewSuspects list to include either the player lynched or the players tied
        newSuspects.add(suspects.get(0));
        System.out.println();
        for (Character user: suspects){
            System.out.println(user.name + ": " + user.votingPoints);
            //don't check oneself with self, please
            if (user.equals(newSuspects.get(0))) continue;

            //complete suspect
            if (user.votingPoints > newSuspects.get(0).votingPoints) {
                newSuspects.clear();
                newSuspects.add(user);
            }
            //tied suspects
            else if (user.votingPoints == newSuspects.get(0).votingPoints) newSuspects.add(user);
        }

        //if there's a tie, resets the voting round with the players tied
        if (newSuspects.size() > 1) {
            conversation += " There was a tie in the voting points, please re-vote ";// UPDATE THIS
            System.out.println("\nThere was a tie in the voting points, we will re-vote with our tied players");
            VotingRound(newSuspects);
        }
        else{
            //officially lynches a player
            newSuspects.get(0).isAlive = false;
            if (newSuspects.get(0).boundChar != null) newSuspects.get(0).boundChar.isAlive = false;
            System.out.println(newSuspects.get(0).name + " has been lynched"); 
            if (ExecutionerPresent != null) ExecutionerPresent.Action(players, newSuspects.get(0));
            newSuspects.get(0).OnDeath(true);
            players.remove(newSuspects.get(0));
        }
    }

    //picks a random player, predicate - a preset condition
    static Character PickRandomPlayer(Predicate<Character> p) {
        Character target = null;
        do {
            target = players.get(rand.nextInt(players.size()));
        } while (!p.test(target));
        return target;
    }

    //checks which players are alive or not after each night
    static void CheckPlayerStatus(){
        players.getLast().checkPlayers(players);
        players.stream().filter(p -> !p.isAlive).forEach(p -> 
            System.out.println(p.name + " has died during the night"));
        players.removeIf(p -> !p.isAlive);
        CheckGameStatus(3);
    }

    //finds the player that a user types within the list of players alive
    static Character findPlayer(String text, List<Character> list){
        String name;
        while (true){
            try {
                System.out.println(text);
                name = in.nextLine();
                for (Character p : list) {
                    if (p.name.equalsIgnoreCase(name)) {
                        return p;
                    }
                }
                throw new Exception(); // If player wasn't found, throw exception

            } catch (Exception e) {
                System.out.println("That player is either not in the list or has been lynched/killed");
            }
        }
    }

    static void AddLogEntry(Character character, String text) {
        for (var player : players) {
            player.AddLogEntry(character.name + ": " + text + "\n");
        }
    }

    static void CheckGameStatus(int Case){
        //checks if any side (OR ROLES) won the game,
        //check after how many turns it's possible
        int EvilPlayers = 0;

        //CASEs: 0 - Executioner win; 1 - Jester win; any other number - check general side win

        switch (Case){
            case 0:
                System.out.println("As the Executioner, you won!");
                ContGame();
                break;

            case 1:
                System.out.println("As the Jester, you won!");
                ContGame();
                break;

            default:
                //cataloguing the amount of evil players
                for (Character p : players){
                    switch (p.Role()){
                        case ("Werewolf"):
                            EvilPlayers++;
                            break;
                        case ("Sorcerer"):
                            EvilPlayers++;
                            break;
                    }
                    
                }

                //System.out.println("Amount of Evil Players in game " + EvilPlayers);
                if (EvilPlayers > (players.size()-EvilPlayers)){
                    System.out.println("\nToo many on the evil sides remain, wiping out the rest of the folk.");
                    //lose code
                    GameOn = false;
                }
                else if (EvilPlayers <= 0){
                    System.out.println("\nAll of the werewolves and their allies have been wiped out.");
                    //win code
                    GameOn = false;
                }

                break;
            }

    }

    static void ListPlayers(List<Character> list){
        for (int k = 0; k < list.size() - 1; k++) {
            System.out.print(list.get(k).name + (k == list.size() - 2 ? ", and " : ", "));
        }
        System.out.print(list.getLast().name);
    }

    static void ContGame(){
        String response = "";
        while (true){
            try{
                System.out.println("would you like to continue the game as a spectator?");
                response = in.nextLine().toLowerCase();
                if (response.equals("y") || response.equals("yes")) break;
                else if (response.equals("n") || response.equals("no")) {
                    GameOn = false;
                    break;
                }
                else throw new Exception();
            } catch (Exception e){
                System.out.println("\nThat's not a valid response. Please type yes/no or y/n");
            }

        }
    }

    public static String SendRequestToServer(String prompt) {
        return "Said nothing.";
    }

    public static String GenerateInitialPrompt(Character c) {
        return "This is a log of mafia game, where " + c.name + " plays " + c.Role() + "\n";
    }
}
