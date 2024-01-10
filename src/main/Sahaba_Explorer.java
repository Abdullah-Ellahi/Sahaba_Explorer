import java.util.Scanner;
class Sahaba_Explorer {
    static void InBetweenRelations(){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter no of Sahaba");
        int n = Integer.parseInt(s.nextLine());
        int[] save_index = new int[n];

        for (int i=0 ; i<n ; i++){
            System.out.println("Enter name of "+(i+1)+" Sahaba: ");
            String name = s.nextLine();
            int index = Sahaba.findSahaba(name);
            if(index == -1) {
                System.out.println("Sahaba with given name not found!\nKindly check spelling!");
                return;
            }
            save_index[i] = index;
        }

        boolean haveRelation = false;
        for (int i=0 ; i<n ; i++) {
            for (int j = 0; j < Main.links.get(save_index[i]).size(); j++) {
                String name = Main.links.get(save_index[i]).get(j).sahaba.popular_name;
                for (int k=i ; k<n ; k++) {
                    if ((k+1)%n == i){
                        continue;
                    }
                    if (name.equals(Main.sahabas.get(save_index[(k + 1) % n]).popular_name)) {
                        haveRelation = true;
                        System.out.println(Main.sahabas.get(save_index[i]).popular_name+" is "+
                                Main.links.get(save_index[i]).get(j).link_type+" of "+name);
                    }
                }
            }
        }
        if(!haveRelation){
            System.out.println("There is no direct relation between the given Sahabas");
        }
    }
}
