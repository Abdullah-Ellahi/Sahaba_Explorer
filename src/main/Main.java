import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Main class for the Data Structures course project
public class Main {
    // Static variables for modular arithmetic and data storage
    static int ModValue = 100;
    static ArrayList<Sahaba> sahabas = new ArrayList<>(ModValue);
    static ArrayList<ArrayList<Blood_ties>> blood_ties = new ArrayList<>(ModValue);
    static ArrayList<ArrayList<Links>> links = new ArrayList<>(ModValue);

    // Main method to run the program
    public static void main(String[] args) {
        try{
            // Scanner for user input
            Scanner s = new Scanner(System.in);

            // Creating a file object for reading Sahaba data
            File file = new File("sahaba.txt");
            // Reading data from file
            readFromfile(file);

            // Checking if Sahaba list is empty
            if (sahabas == null) {
                System.out.println("File is empty!");
                sahabas = new ArrayList<>();
            } else {
                System.out.println("Record read successfully!");
            }

            // Main menu loop
            int choice = 0;
            while (choice != 6) {
                // Displaying the main menu
                displayMainMenu();

                // Reading user choice
                choice = Integer.parseInt(s.nextLine());

                // Switch case for user choice
                switch (choice) {
                    case 1:
                        // Adding a new Sahaba
                        Sahaba.addSahaba();
                        break;

                    case 2: {
                        // Displaying Display Menu
                        int ch2 = 0;

                        while (ch2 != 4) {
                            displayDisplayMenu();
                            ch2 = Integer.parseInt(s.nextLine());

                            if (ch2 == 4) {
                                break;
                            }
                            switch (ch2) {
                                case 1:
                                    displayBloodRelations(blood_ties);
                                    break;

                                case 2:
                                    displayOtherLinks(links);
                                    break;

                                case 3:
                                    Sahaba.print(sahabas);
                                    break;

                                default:
                                    System.out.println("Invalid Choice!");
                            }
                        }
                        break;
                    }
                    case 3: {
                        int ch = 0;
                        while (ch != 4) {
                            // Displaying the menu
                            displayBloodTiesMenu();
                            ch = Integer.parseInt(s.nextLine());
                            if (ch == 4)
                                break;

                            switch (ch) {
                                case 1:
                                    Connections.add_blood_ties();
                                    break;

                                case 2:
                                    Connections.remove_blood_ties();
                                    break;

                                case 3:
                                    Connections.edit_blood_ties();
                                    break;

                                case 4:
                                    break;

                                default:
                                    System.out.println("Invalid Choice!");
                            }
                            if (ch == 4)
                                break;
                        }
                    }
                    break;
                    case 4: {
                        int ch1 = 0;
                        while (ch1 != 4) {
                            // Displaying the menu
                            displayLinksMenu();
                            ch1 = Integer.parseInt(s.nextLine());
                            if (ch1 == 4)
                                break;

                            switch (ch1) {
                                case 1:
                                    Connections.add_links();
                                    break;

                                case 2:
                                    Connections.remove_links();
                                    break;

                                case 3:
                                    Connections.edit_links();
                                    break;

                                case 4:
                                    break;

                                default:
                                    System.out.println("Invalid Choice!");
                            }
                            if (ch1 == 4)
                                break;
                        }
                        break;
                    }

                    case 5:
                        displaySahabaExplorer();
                        int ch = Integer.parseInt(s.nextLine());
                        while (ch != 7) {
                            switch (ch) {
                                case 1:
                                    System.out.println("Enter the name of Sahaba for whom you want to find details");
                                    String required = s.nextLine();
                                    int index = Sahaba.findSahaba(required);

                                    if (index != -1) {
                                        System.out.println("Printing details of the Sahaba...\n");
                                        Sahaba.print(index);
                                    } else {
                                        System.out.println("Sahaba with the given name not found in our data!\nKindly check your spelling!");
                                    }
                                    break;
                                case 2:
                                    Sahaba_Explorer.InBetweenRelations();
                                    break;
                                case 3:
                                    Features.show_bst();
                                    break;
                                case 4:
                                    Features.show_bst_dob();
                                    break;
                                case 5:
                                    Features.djk_blood_ties();
                                    break;
                                case 6:
                                    Features.djk_links();
                                    break;
                                case 7:
                                    break;
                                default:
                                    System.out.println("Invalid choice!!!");
                                    break;
                            }
                            displaySahabaExplorer();
                            ch = Integer.parseInt(s.nextLine());
                            if (ch == 7)
                                break;
                        }
                        break;

                    case 6:
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            }

            System.out.println("Writing to file...");
            writeToFile(sahabas);
        } catch (NumberFormatException e){
            System.out.println("Exception: Invalid Input format!");
            System.out.println("Exiting system!");
            System.exit(0);
        }catch (NullPointerException e){
            System.out.println("Exception: "+e.getMessage());
            System.out.println("Exiting system!");
            System.exit(0);
        }catch (Exception e){
            System.out.println("Exception: "+e.getMessage());
            System.out.println("Exiting system!");
            System.exit(0);
        }

    }

    // Djb2 hash function for string hashing
    public static int hash(String str) {
        int hashValue = 0;
        for (int i = 0; i < str.length(); i++) {
            hashValue = str.charAt(i) + ((hashValue << 5) - hashValue);
        }
        if(hashValue<0)
            hashValue = -hashValue;

        if(Main.sahabas.get(hashValue%ModValue) != null){
            while (Main.sahabas.get(hashValue%ModValue) != null){
                hashValue = (hashValue+1)%Main.ModValue;
            }
        }
        return hashValue;
    }

    // Method to read data from file
    static void readFromfile(File file){
        // Temporary ArrayList to store Sahaba data read from file
        ArrayList<Sahaba> temp = null;

        try {
            // Reading Sahaba data from file
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            temp = (ArrayList<Sahaba>) ois.readObject();

            // Initializing Sahabas list
            initialiseSahabas();

            // Populating Sahabas list using the read data
            for(int i=0 ; i<temp.size() ; i++){
                if(temp.get(i)!=null){
                    int hashIndex = hash(temp.get(i).popular_name) % temp.size();
                    sahabas.set(hashIndex, temp.get(i));
                }
            }
            fis.close();
            ois.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
        } catch (IOException e) {
            System.out.println("Error: "+e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error: "+e.getMessage());
        }

        // Creating a file object for reading links data
        file = new File("links.dat");

        try {
            // Reading links data from file
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Initializing Blood-ties and Other links lists
            initializeAllLinks();

            // Populating Blood-ties and Other links lists using the read data
            ArrayList<ArrayList<Blood_ties>> temp1 = (ArrayList<ArrayList<Blood_ties>>) ois.readObject();
            ArrayList<ArrayList<Links>> temp2 = (ArrayList<ArrayList<Links>>) ois.readObject();
            blood_ties = temp1;
            links = temp2;

            fis.close();
            ois.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
        } catch (IOException e) {
            System.out.println("Error1: "+e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error2: "+e.getMessage());
        }
    }

    // Method to initialize Sahabas list with null values
    static void initialiseSahabas(){
        for(int i=0 ; i<ModValue ; i++){
            sahabas.add(null);
        }
    }

    // Method to initialize Blood-ties and Other links lists with null values
    static void initializeAllLinks(){
        for(int i=0 ; i<ModValue ; i++){
            blood_ties.add(new ArrayList<>());
        }
        for(int i=0 ; i<ModValue ; i++){
            links.add(new ArrayList<>());
        }
    }

    // Method to write data to file
    public static void writeToFile(ArrayList<Sahaba> sahaba){
        try {
            // Creating a file object for writing Sahaba data
            File file = new File("Sahaba.txt");

            // Writing Sahaba data to file
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(sahaba);
            fos.close();
            oos.close();

            System.out.println("Record written successfully!");

        } catch (IOException e) {
            System.out.println("Error: File not found!");
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            System.out.println("Error: Unable To Add Sahaba!");
        }

        try {
            // Creating a file object for writing links data
            File file = new File("links.dat");

            // Writing Blood-ties and Other links data to file
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(blood_ties);
            oos.writeObject(links);
            fos.close();
            oos.close();

            System.out.println("Links written successfully!");

        } catch (IOException e) {
            System.out.println("Error: File not found!");
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            System.out.println("Error: Unable To Add Links!");
        }
    }

    // Method to display Blood-ties
    public static void displayBloodRelations(ArrayList<ArrayList<Blood_ties>> blood_ties) {
        // Looping through Sahaba and displaying Blood-ties
        for (int i = 0 ; i < sahabas.size() ; i++) {
            Sahaba current = sahabas.get(i);
            if(current != null && Main.blood_ties.get(i) != null){
                System.out.println("_______________________________________________________________________________________________________________________________________________________________\n");
                System.out.println(current.popular_name + " has the following Blood Relations:");

                if(Main.blood_ties.get(i).size() != 0){
                    for (int j = 0; j < Main.blood_ties.get(i).size(); j++) {
                        if (Main.blood_ties.get(i) != null) {
                            System.out.println("He is " + Main.blood_ties.get(i).get(j).relation +
                                    " of " + Main.blood_ties.get(i).get(j).sahaba.popular_name);
                        }
                    }
                }
                else {
                    System.out.println("No Blood-Relations are added!");
                }
                System.out.println();
            }
        }
        System.out.println("_______________________________________________________________________________________________________________________________________________________________\n");
    }

    // Method to display Other links
    public static void displayOtherLinks(ArrayList<ArrayList<Links>> other_links) {
        // Looping through Sahaba and displaying Other links
        for (int i = 0 ; i < other_links.size() ; i++) {
            if(sahabas.get(i) != null && Main.links.get(i) != null){
                System.out.println("_______________________________________________________________________________________________________________________________________________________________\n");
                Sahaba current = sahabas.get(i);
                System.out.println(current.popular_name + " has the following Other links:");

                if(Main.blood_ties.get(i).size() != 0) {
                    for (int j = 0; j < Main.links.get(i).size(); j++) {
                        System.out.println("He is " + Main.links.get(i).get(j).link_type +
                                " of " + Main.links.get(i).get(j).sahaba.popular_name);
                    }
                }
                else {
                    System.out.println("No Other-links are added!");
                }
                System.out.println();
            }
        }
        System.out.println("_______________________________________________________________________________________________________________________________________________________________\n");
    }

    // Method to display the main menu
    public static void displayMainMenu() {
        System.out.println();
        System.out.println("*****************************************************************************************************************************************************************");
        System.out.println("*************************************************************       MAIN MENU       *****************************************************************************");
        System.out.println("*****************************************************************************************************************************************************************");
        System.out.println();
        System.out.println("                                                1.  Add Sahaba");
        System.out.println("                                                2.  Display");
        System.out.println("                                                3.  Manage Blood-ties Between Sahaba");
        System.out.println("                                                4.  Manage Links Between Sahaba");
        System.out.println("                                                5.  Sahaba Explorer");
        System.out.println("                                                6.  Exit");
        System.out.println("Enter your choice:");
    }
    public static void displaySahabaExplorer() {
        System.out.println();
        System.out.println("*****************************************************************************************************************************************************************");
        System.out.println("*************************************************************    SAHABA EXPLORER   *****************************************************************************");
        System.out.println("*****************************************************************************************************************************************************************");
        System.out.println();
        System.out.println("                                                1.  Find Sahaba by name");
        System.out.println("                                                2.  Find relation between N Sahabas");
        System.out.println("                                                3.  Compare acceptance of Islam of N Sahabas");
        System.out.println("                                                4.  Find the eldest of N Sahabas");
        System.out.println("                                                5.  Shortest relation between Sahabas via blood ties");
        System.out.println("                                                6.  Shortest relation between Sahabas via other links");
        System.out.println("                                                7.  Back to MAIN MENU");
        System.out.println("Enter your choice:");
    }
    public static void displayDisplayMenu() {
        System.out.println();
        System.out.println("*****************************************************************************************************************************************************************");
        System.out.println("*************************************************************    DISPLAY MENU  *****************************************************************************");
        System.out.println("*****************************************************************************************************************************************************************");
        System.out.println();
        System.out.println("                                                1.  Display all Blood-Ties");
        System.out.println("                                                2.  Display all Links");
        System.out.println("                                                3.  Display all Sahabas");
        System.out.println("                                                4.  Back to MAIN MENU");
        System.out.println("Enter your choice:");
    }
    public static void displayBloodTiesMenu() {
        System.out.println();
        System.out.println("*****************************************************************************************************************************************************************");
        System.out.println("*************************************************************    EDIT BLOOD-TIES    *****************************************************************************");
        System.out.println("*****************************************************************************************************************************************************************");
        System.out.println();
        System.out.println("                                                1.  Add Blood-Tie");
        System.out.println("                                                2.  Delete Blood-Tie");
        System.out.println("                                                3.  Update Blood-Tie");
        System.out.println("                                                4.  Back to MAIN MENU");
        System.out.println("Enter your choice:");
    }
    public static void displayLinksMenu() {
        System.out.println();
        System.out.println("*****************************************************************************************************************************************************************");
        System.out.println("*************************************************************       EDIT LINKS       *****************************************************************************");
        System.out.println("*****************************************************************************************************************************************************************");
        System.out.println();
        System.out.println("                                                1.  Add Link");
        System.out.println("                                                2.  Delete Link");
        System.out.println("                                                3.  Update Link");
        System.out.println("                                                4.  Back to MAIN MENU");
        System.out.println("Enter your choice:");
    }
}