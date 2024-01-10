// Import necessary libraries
import java.io.*;
import java.util.*;

// Serializable class representing Sahaba
class Sahaba implements Serializable {
    // Serialization version UID
    private static final long serialVersionUID = 1L;

    // Sahaba attributes
    String full_name;
    String popular_name;
    int dob;
    String tribe;
    Conversion conversion;

    // null constructor
    public Sahaba() {
    }

    // Parameterized constructor
    public Sahaba(String full_name, String popular_name, int yearOfBirth, String tribe, Conversion conversion, int date, int month, int year) {
        this.full_name = full_name;
        this.popular_name = popular_name;
        this.dob = yearOfBirth;
        this.tribe = tribe;
        this.conversion = conversion;
    }

    // Method to add a Sahaba
    static void addSahaba() {
        Scanner s = new Scanner(System.in);
        Sahaba sahaba = new Sahaba();

        // Collect Sahaba information from the user
        System.out.println("Enter popular name of Sahaba: ");
        sahaba.popular_name = s.nextLine();
        System.out.println("Enter full name of Sahaba: ");
        sahaba.full_name = s.nextLine();
        System.out.println("Enter year(AD) of birth: ");
        sahaba.dob = Integer.parseInt(s.nextLine());
        System.out.println("Enter tribe name of Sahaba: ");
        sahaba.tribe = s.nextLine();

        // Collect conversion information from the user
        System.out.println("Enter year of embracing Islam: ");
        String year = s.nextLine();
        System.out.println("Enter Age of embracing Islam: ");
        String age = s.nextLine();
        System.out.println("Enter circumstances of embracing Islam: ");
        String circumstance = s.nextLine();

        // Set conversion details
        sahaba.conversion = new Conversion(Integer.parseInt(year), Integer.parseInt(age), circumstance);

        // Calculate hash index for the Sahaba
        int hashIndex = Main.hash(sahaba.popular_name) % Main.ModValue;
        System.out.println("hashIndex: "+hashIndex);

        Main.sahabas.set(hashIndex, sahaba);
        System.out.println(Main.sahabas.get(hashIndex).popular_name);
        // Initialize blood_ties and links for the Sahaba
        ArrayList<Blood_ties> temporary1 = new ArrayList<>();
        Main.blood_ties.set(hashIndex, temporary1);

        ArrayList<Links> temporary2 = new ArrayList<>();
        Main.links.set(hashIndex, temporary2);

        int response1 = -1;
        while (response1 != 2) {
            System.out.println("Do you want to enter blood-ties: ");
            System.out.println("1. Yes");
            System.out.println("2. No");
            response1 = Integer.parseInt(s.nextLine());
            if (response1 == 2) break;

            switch (response1) {
                case 1:
                    String required_name;
                    while (response1 != 2) {
                        System.out.println("Give popular name of the Sahaba you want to tie: ");
                        required_name = s.nextLine();
                        int index = findSahaba(required_name);
                        if (index != -1) {
                            System.out.println("Enter the relation from his side:");
                            String relation1 = s.nextLine();
                            System.out.println("Enter the relation from " + Main.sahabas.get(index).popular_name + "'s side:");
                            String relation2 = s.nextLine();
                            System.out.println("executed 1");
                            sahaba.addBloodTies(temporary1, Main.sahabas.get(index), relation1, relation2);
                        } else
                            System.out.println("The Sahaba with given name not found!");

                        System.out.println("Do you want to enter more blood-relatives: ");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        response1 = Integer.parseInt(s.nextLine());
                    }
                    break;

                case 2:
                    break;

                default:
                    System.out.println("Invalid Choice!");
                    break;
            }
            if (response1 == 2) break;
        }

        int response2 = -1;
        while (response2 != 2) {
            System.out.println("Do you want to enter other-links: ");
            System.out.println("1. Yes");
            System.out.println("2. No");

            response2 = Integer.parseInt(s.nextLine());

            switch (response2) {
                case 1:
                    String required_name;
                    while (response2 != 2) {
                        System.out.println("Give popular name of the Sahaba you want to link: ");
                        required_name = s.nextLine();
                        int index = findSahaba(required_name);
                        if (index != -1) {
                            System.out.println("Enter the relation from " + sahaba.popular_name + "'s side:");
                            String relation1 = s.nextLine();
                            System.out.println("Enter the relation from " + Main.sahabas.get(index).popular_name + "'s side:");
                            String relation2 = s.nextLine();
                            sahaba.addOtherLinks(temporary2, Main.sahabas.get(index), relation1, relation2);
                        } else
                            System.out.println("The Sahaba with given name not found!");

                        System.out.println("Do you want to enter more links: ");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        response2 = Integer.parseInt(s.nextLine());
                    }
                    break;

                case 2:
                    break;

                default:
                    System.out.println("Invalid Choice!");
                    break;
            }
            if (response2 == 2) break;
        }
        System.out.println("Sahaba Successfully added to data!");
    }

    // Method to add blood ties for a Sahaba
    public void addBloodTies(ArrayList<Blood_ties> i, Sahaba j, String relation1, String relation2) {
        Blood_ties link1 = new Blood_ties(j, relation1);
        Blood_ties link2 = new Blood_ties(this, relation2);

        int index1 = Main.blood_ties.indexOf(i);
        int index2 = Main.sahabas.indexOf(j);

        if (index1 != -1 && index2 != -1) {
            i.add(link1);

            if(Main.blood_ties.get(index2) == null) {
                Main.blood_ties.set(index2,new ArrayList<>());
                Main.blood_ties.get(index2).add(link2);
            }
            else {
                Main.blood_ties.get(index2).add(link2);
            }
            System.out.println("successfully Added blood tie!");
        }
    }

    // Method to add other links for a Sahaba
    public void addOtherLinks(ArrayList<Links> i, Sahaba j, String relation1, String relation2) {
        Links link1 = new Links(j, relation1);
        Links link2 = new Links(this, relation2);

        int index1 = Main.links.indexOf(i);
        int index2 = Main.sahabas.indexOf(j);

        if (index1 != -1 && index2 != -1) {
            i.add(link1);

            if(Main.links.get(index2) == null) {
                Main.links.set(index2,new ArrayList<>());
                Main.links.get(index2).add(link2);
            }
            else {
                Main.links.get(index2).add(link2);
            }
            System.out.println("successfully Added other link!");
        }
    }

    // Method to find a Sahaba by name
    static int findSahaba(String name) {
        Scanner s = new Scanner(System.in);
        if (Main.sahabas == null) {
            return -1;
        }

        int hashIndex = Main.hash(name) % Main.ModValue;
        int originalIndex = hashIndex;
        Queue<Sahaba> queue = new LinkedList<>();

        // Start linear probing from the calculated hash index || Hash-search loop
        while (Main.sahabas.get(hashIndex) != null) {
            // If a Sahaba is found at the current index and has the matching name, return the index
            if (    Main.sahabas.get(hashIndex).full_name.toLowerCase().contains(name.toLowerCase()) ||
                    Main.sahabas.get(hashIndex).popular_name.toLowerCase().contains(name.toLowerCase())) {
                    queue.add(Main.sahabas.get(hashIndex));
            }
            else { // Moving to the next index using linear probing
                hashIndex = (hashIndex + 1) % Main.ModValue;
            }
            // Check if we have completed one full loop, which means we have checked all possible slots
            if (hashIndex == originalIndex) {
                break;
            }
        }

        if(queue.isEmpty()) {
            // Exhaustive-search loop
            for (int i = 0; i < Main.sahabas.size(); i++) {
                if (Main.sahabas.get(i) != null &&
                                (Main.sahabas.get(i).full_name.toLowerCase().contains(name.toLowerCase()) ||
                                 Main.sahabas.get(i).popular_name.toLowerCase().contains(name.toLowerCase()))) {
                    queue.add(Main.sahabas.get(i));
                }
            }
        }

        int res;
        // if there are more than one matches for the name user has entered
        if(queue.size()>1){
            System.out.println("The given name matches more than one result\n" +
                    "Kindly select your choice from the following:\n");

            int[] index = new int[queue.size()];
            // printing all the matches
            for(int i=0 ; i<index.length ; i++){
                Sahaba temp = queue.remove();
                index[i] = Main.sahabas.indexOf(temp);
                System.out.println((i+1)+") "+temp.popular_name);
            }

            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(s.nextLine());

            res = index[choice-1];
        }
        else {
            res = Main.sahabas.indexOf(queue.poll());
        }

        // If the loop completes both hash search and exhaustive search loop without finding a match, return -1
        return res;
    }


    // Method to print details of a Sahaba
    static void print(int indx) {
        Sahaba temp = Main.sahabas.get(indx);
        System.out.println("*****************************************************************************************************************************************************************");
        System.out.println("*************************************************************     Hazrat "+temp.popular_name+"    **************************************************************");
        System.out.println("*****************************************************************************************************************************************************************");
        System.out.println("Full Name:     Hazrat " + temp.full_name);
        System.out.println("tribe:         " + temp.tribe);
        System.out.println("Year of Brith: " + temp.dob);
        System.out.println("CONVERSION TO ISLAM");
        System.out.println("Year:          " + temp.conversion.year);
        System.out.println("Age:           " + temp.conversion.age);
        System.out.println("Circumstances: " + temp.conversion.circumstances);
        int index = Main.sahabas.indexOf(temp);

        boolean found = false;
        if (Main.blood_ties.get(index) != null) {
            System.out.println("BLOOD RELATIVES:");
            for (int i = 0; i < Main.blood_ties.get(index).size(); i++) {
                System.out.println("He is " + Main.blood_ties.get(index).get(i).relation +
                        " of " + Main.blood_ties.get(index).get(i).sahaba.popular_name);
                found = true;
            }
        }
        if(!found)
            System.out.println("No Blood-relatives are added!");

        found = false;
        if (Main.links.get(index) != null) {
            System.out.println("OTHER LINKS:");
            for (int i = 0; i < Main.links.get(index).size(); i++) {
                System.out.println("He is " + Main.links.get(index).get(i).link_type +
                        " of " + Main.links.get(index).get(i).sahaba.popular_name);
                found = true;
            }
        }
        if(!found)
            System.out.println("No Other Links are added!");

        System.out.println();
    }

    // Static method to print details of all Sahabas
    static void print(ArrayList<Sahaba> sahabas) {
        System.out.println("Printing all sahabas...");
        for (int j = 0; j < sahabas.size(); j++) {
            if (sahabas.get(j) != null) {
                Sahaba temp = sahabas.get(j);
                System.out.println("*****************************************************************************************************************************************************************");
                System.out.println("*************************************************************     Hazrat "+temp.popular_name+"    **************************************************************");
                System.out.println("*****************************************************************************************************************************************************************");
                System.out.println("Full Name:     Hazrat " + temp.full_name);
                System.out.println("Year of Brith: " + temp.dob);
                System.out.println("tribe:         " + temp.tribe);
                System.out.println("CONVERSION TO ISLAM");
                System.out.println("Year:          " + temp.conversion.year);
                System.out.println("Age:           " + temp.conversion.age);
                System.out.println("Circumstances: " + temp.conversion.circumstances);

                boolean found = false;
                if (Main.blood_ties.get(j).size() != 0) {
                    System.out.println("BLOOD RELATIVES:");
                    for (int i = 0; i < Main.blood_ties.get(j).size(); i++) {
                        System.out.println("He is " + Main.blood_ties.get(j).get(i).relation +
                                " of " + Main.blood_ties.get(j).get(i).sahaba.popular_name);
                        found = true;
                    }
                }
                if(!found)
                    System.out.println("No Blood-relatives are added!");

                found = false;
                if (Main.links.get(j).size() != 0) {
                    System.out.println("OTHER LINKS:");
                    for (int i = 0; i < Main.links.get(j).size(); i++) {
                        System.out.println("He is " + Main.links.get(j).get(i).link_type +
                                " of " + Main.links.get(j).get(i).sahaba.popular_name);
                    found = true;
                    }
                }
                if(!found)
                    System.out.println("No Other Links are added!");

                System.out.println();
            }
        }
    }
}

// Serializable class representing the conversion details of a Sahaba
class Conversion implements Serializable {
    // Serialization version UID
    private static final long serialVersionUID = 1L;

    // Conversion details
    int year;
    int age;
    String circumstances;

    // Parameterized constructor
    public Conversion(int year, int age, String circumstances) {
        this.year = year;
        this.age = age;
        this.circumstances = circumstances;
    }
}

// Serializable class representing the blood ties of a Sahaba
class Blood_ties implements Serializable {
    // Serialization version UID
    private static final long serialVersionUID = 1L;

    // Blood ties details
    Sahaba sahaba;
    String relation;
    int wt;

    // Parameterized constructor
    public Blood_ties(Sahaba sahaba, String relation) {
        this.sahaba = sahaba;
        this.relation = relation;
        this.wt = 1;
    }
}

// Serializable class representing the other links of a Sahaba
class Links implements Serializable {
    // Serialization version UID
    private static final long serialVersionUID = 1L;

    // Other links details
    Sahaba sahaba;
    String link_type;
    int wt;

    // Parameterized constructor
    public Links(Sahaba sahaba, String link_type) {
        this.sahaba = sahaba;
        this.link_type = link_type;
        this.wt = 1;
    }
}
