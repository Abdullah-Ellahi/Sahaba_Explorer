import java.util.ArrayList;
import java.util.Scanner;

public class Connections {
    static void add_blood_ties() {
        Scanner s = new Scanner(System.in);
        boolean found = false;

        System.out.println("Enter name of Sahaba for which you want to add");
        String name = s.nextLine();
        int index = Sahaba.findSahaba(name);
        System.out.println("Now enter the name of Sahaba from which you want to connect");
        String name2 = s.nextLine();
        int index2 = Sahaba.findSahaba(name2);

        for (int i = 0; i < Main.blood_ties.get(index).size(); i++) {
            System.out.println("1 "+Main.blood_ties.get(index).get(i).sahaba.popular_name);
            if (Main.blood_ties.get(index).get(i) != null &&
                    Main.blood_ties.get(index).get(i).sahaba.popular_name.equals(Main.sahabas.get(index2).popular_name)) {
                found = true;
                break;
            }
        }

        if(!found) {
            if (index != -1 && index2 != -1) {
                System.out.println("Enter relation from " + Main.sahabas.get(index).popular_name + "'s side");
                String relation2 = s.nextLine();
                System.out.println("Enter relation from " + Main.sahabas.get(index2).popular_name + "'s side");
                String relation = s.nextLine();

                System.out.println("Add blood ties called!");
                Blood_ties link1 = new Blood_ties(Main.sahabas.get(index2), relation2);
                Blood_ties link2 = new Blood_ties(Main.sahabas.get(index), relation);

                System.out.println("index1: " + index);
                System.out.println("index2: " + index2);

                if (index != -1 && index2 != -1) {
                    Main.blood_ties.get(index).add(link1);

                    if (Main.blood_ties.get(index2) == null) {
                        Main.blood_ties.set(index2, new ArrayList<>());
                        Main.blood_ties.get(index2).add(link2);
                        System.out.println("1");

                        System.out.println(Main.blood_ties.get(index2).get(Main.blood_ties.indexOf(link2)));
                    } else {
                        Main.blood_ties.get(index2).add(link2);
                        System.out.println("2");
                    }
                    System.out.println("successfully Added blood tie!");
                }
            }
            else {
                System.out.println("Sahaba with the given name not found!\nKindly check spelling!");
            }
        } else {
            System.out.println("There is already a relation between the given Sahabas!");
        }
    }

    static void edit_blood_ties(){
        Scanner s = new Scanner(System.in);
        boolean edited = false;
        System.out.println("Enter name of first Sahaba with the link to be updated");
        String name = s.nextLine();
        int index = Sahaba.findSahaba(name);

        System.out.println("Enter name of second Sahaba with the link to be updated");
        String name2 = s.nextLine();
        int index2 = Sahaba.findSahaba(name2);

        if (index != -1 && index2 != -1) {
            ArrayList<Blood_ties> list1 = Main.blood_ties.get(index);
            ArrayList<Blood_ties> list2 = Main.blood_ties.get(index2);

            for (int i=0 ; i<list1.size() ; i++){
                if(list1.get(i).sahaba.popular_name.equals(Main.sahabas.get(index2).popular_name)){
                    System.out.println("Enter new blood relation from "+list1.get(i).sahaba.popular_name+"'s side: ");
                    String newRelation = s.nextLine();

                    for (int j=0 ; j<list2.size() ; j++){
                        if(list2.get(j).sahaba.popular_name.equals(Main.sahabas.get(index).popular_name)){
                            System.out.println("Enter new blood relation from "+list2.get(j).sahaba.popular_name+"'s side: ");
                            String newRelation2 = s.nextLine();
                            list1.get(i).relation = newRelation2;
                            list2.get(j).relation = newRelation;
                            break;
                        }
                    }
                    edited = true;
                    break;
                }
            }

            if(edited) {
                System.out.println("Relation edited successfully!");
            }
            else
                System.out.println("Relation between given Sahabas not found!");
        } else {
            System.out.println("Sahaba with the given name not found!\nKindly check spelling!");
        }
    }
    static void remove_blood_ties(){
        Scanner s = new Scanner(System.in);

        boolean removed = false;
        System.out.println("Enter name of first Sahaba with the link to be updated");
        String name = s.nextLine();
        System.out.println("Enter name of second Sahaba with the link to be updated");
        String name2 = s.nextLine();

        int index = Sahaba.findSahaba(name);
        int index2 = Sahaba.findSahaba(name2);

        if (index != -1 && index2 != -1) {
            ArrayList<Blood_ties> list1 = Main.blood_ties.get(index);
            ArrayList<Blood_ties> list2 = Main.blood_ties.get(index2);

            for (int i=0 ; i<list1.size() ; i++){
                System.out.println(list1.get(i).sahaba.popular_name);
                if(list1.get(i).sahaba.popular_name.equals(Main.sahabas.get(index2).popular_name)){
                    System.out.println("sf");
                    list1.remove(i);
                    for (int j=0 ; j<list2.size() ; j++){
                        if(list2.get(j).sahaba.popular_name.equals(Main.sahabas.get(index).popular_name)){
                            System.out.println("sf2");
                            list2.remove(j);
                            break;
                        }
                    }
                    removed = true;
                    break;
                }
            }

            if(removed == true) {
                System.out.println("blood relation removed successfully!");
            }
            else
                System.out.println("No blood relation found between the given Sahaba.");
        } else {
            System.out.println("Sahaba with the given name not found!\n Kindly check spelling!");
        }
    }

    static void add_links(){
        Scanner s = new Scanner(System.in);
        boolean found = false;

        System.out.println("Enter name of Sahaba for which you want to add");
        String name = s.nextLine();
        int index = Sahaba.findSahaba(name);
        System.out.println("Now enter the name of Sahaba from which you want to connect");
        String name2 = s.nextLine();
        int index2 = Sahaba.findSahaba(name2);

        for (int i = 0; i < Main.links.get(index).size(); i++) {
            System.out.println("1 "+Main.links.get(index).get(i).sahaba.popular_name);
            if (Main.links.get(index).get(i) != null &&
                    Main.links.get(index).get(i).sahaba.popular_name.equals(Main.sahabas.get(index2).popular_name)) {
                found = true;
                break;
            }
        }

        if(!found) {
            if (index != -1 && index2 != -1) {
                System.out.println("Enter relation from " + Main.sahabas.get(index).popular_name + "'s side");
                String relation2 = s.nextLine();
                System.out.println("Enter relation from " + Main.sahabas.get(index2).popular_name + "'s side");
                String relation = s.nextLine();

                System.out.println("Add other links called!");
                Links link1 = new Links(Main.sahabas.get(index2), relation2);
                Links link2 = new Links(Main.sahabas.get(index), relation);

                System.out.println("index1: " + index);
                System.out.println("index2: " + index2);

                if (index != -1 && index2 != -1) {
                    Main.links.get(index).add(link1);

                    if (Main.links.get(index2) == null) {
                        Main.links.set(index2, new ArrayList<>());
                        Main.links.get(index2).add(link2);
                        System.out.println("1");

                        System.out.println(Main.links.get(index2).get(Main.links.indexOf(link2)));
                    } else {
                        Main.links.get(index2).add(link2);
                        System.out.println("2");
                    }
                    System.out.println("successfully Added the link!");
                }
            }
            else {
                System.out.println("Sahaba with the given name not found!\nKindly check spelling!");
            }
        } else {
            System.out.println("There is already a link between the given Sahabas!");
        }
    }
    static void edit_links(){
        Scanner s = new Scanner(System.in);
        boolean edited = false;
        System.out.println("Enter name of first Sahaba with the link to be updated");
        String name = s.nextLine();
        int index = Sahaba.findSahaba(name);

        System.out.println("Enter name of second Sahaba with the link to be updated");
        String name2 = s.nextLine();
        int index2 = Sahaba.findSahaba(name2);

        if (index != -1 && index2 != -1) {
            ArrayList<Links> list1 = Main.links.get(index);
            ArrayList<Links> list2 = Main.links.get(index2);

            for (int i=0 ; i<list1.size() ; i++){
                if(list1.get(i).sahaba.popular_name.equals(Main.sahabas.get(index2).popular_name)){
                    System.out.println("Enter new link_type from "+list1.get(i).sahaba.popular_name+"'s side: ");
                    String newRelation = s.nextLine();

                    for (int j=0 ; j<list2.size() ; j++){
                        if(list2.get(j).sahaba.popular_name.equals(Main.sahabas.get(index).popular_name)){
                            System.out.println("Enter new link_type from "+list2.get(j).sahaba.popular_name+"'s side: ");
                            String newRelation2 = s.nextLine();
                            list1.get(i).link_type = newRelation2;
                            list2.get(j).link_type = newRelation;
                            break;
                        }
                    }
                    edited = true;
                    break;
                }
            }

            if(edited) {
                System.out.println("link edited successfully!");
            }
            else
                System.out.println("link between given Sahabas not found!");
        } else {
            System.out.println("Sahaba with the given name not found!\nKindly check spelling!");
        }
    }
    static void remove_links(){
        Scanner s = new Scanner(System.in);

        boolean removed = false;
        System.out.println("Enter name of first Sahaba with the link to be updated");
        String name = s.nextLine();
        System.out.println("Enter name of second Sahaba with the link to be updated");
        String name2 = s.nextLine();

        int index = Sahaba.findSahaba(name);
        int index2 = Sahaba.findSahaba(name2);

        if (index != -1 && index2 != -1) {
            ArrayList<Links> list1 = Main.links.get(index);
            ArrayList<Links> list2 = Main.links.get(index2);

            for (int i=0 ; i<list1.size() ; i++){
                System.out.println(list1.get(i).sahaba.popular_name);
                if(list1.get(i).sahaba.popular_name.equals(Main.sahabas.get(index2).popular_name)){
                    System.out.println("sf");
                    list1.remove(i);
                    for (int j=0 ; j<list2.size() ; j++){
                        if(list2.get(j).sahaba.popular_name.equals(Main.sahabas.get(index).popular_name)){
                            System.out.println("sf2");
                            list2.remove(j);
                            break;
                        }
                    }
                    removed = true;
                    break;
                }
            }

            if(removed == true) {
                System.out.println("Link removed successfully!");
            }
            else
                System.out.println("No Link found between the given Sahaba.");
        } else {
            System.out.println("Sahaba with the given name not found!\n Kindly check spelling!");
        }
    }
}
