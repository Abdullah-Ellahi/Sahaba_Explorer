import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
class Node_tree{
    Sahaba s;
    Node_tree left , right;
    public Node_tree(Sahaba s){
        this.s = s;
        this.left = null;
        this.right = null;
    }
}
class Node{
    int key;
    Node next;
    public Node(int key){
        this.key = key;
        this.next = null;
    }
}
class visit_node{
    boolean key;
    int idx;
    visit_node next;
    public visit_node(boolean key , int idx){
        this.key = key;
        this.idx = idx;
        this.next = null;
    }
}
//class Edge{
//    Sahaba src , dest;
//    String relation;
//    public Edge(Sahaba src , Sahaba dest , String relation){
//        this.src = src;
//        this.dest = dest;
//        this.relation = relation;
//    }
//}
class Pair implements Comparable<Pair>{
    int node;
    int wt;
    public Pair(int node , int wt){
        this.node = node;
        this.wt = wt;
    }
    public int compareTo(Pair p){
        return this.wt - p.wt;
    }
}
class Features {
    static Node_tree head , rear;
    static Node_tree bst_head;
    static void InBetweenRelations(){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter no of Sahaba");
        int n = Integer.parseInt(s.nextLine());
        int[] save_index = new int[n];

        for (int i=0 ; i<n ; i++){
            System.out.println("Enter name");
            String name = s.nextLine();
            int index = Sahaba.findSahaba(name);
            save_index[i] = index;
        }

        for (int i=0 ; i<n ; i++) {
            for (int j = 0; j < Main.links.get(save_index[i]).size(); j++) {
                String name = Main.links.get(save_index[i]).get(j).sahaba.popular_name;
                for (int k=i ; k<n ; k++) {
                    if ((k+1)%n == i){
                        continue;
                    }
                    if (name.equals(Main.sahabas.get(save_index[(k + 1) % n]).popular_name)) {
                        System.out.println(Main.sahabas.get(save_index[i]).popular_name+" is "+
                                Main.links.get(save_index[i]).get(j).link_type+" of "+name);
                    }
                }
            }
        }
    }
    static void enqueue(Node_tree node){
        if (head == null){
            head = rear = node;
        }
        else {
            rear.right = node;
            rear = node;
        }
    }
    static Node_tree dequeue(){
        Node_tree node = head;
        if (head == rear){
            head = rear = null;
            return node;
        }
        head = head.right;
        return node;
    }
    static Node_tree insert_BST(Node_tree bst_head , Node_tree root){
        if (bst_head == null){
            bst_head = root;
            bst_head.right = null;
            return bst_head;
        }
        if (root.s.conversion.year >= bst_head.s.conversion.year){
            bst_head.left = insert_BST(bst_head.left , root);
        }
        else if (root.s.conversion.year < bst_head.s.conversion.year){
            bst_head.right = insert_BST(bst_head.right , root);
        }

        return bst_head;
    }
    static void inorder(Node_tree bst_head){
        if (bst_head == null){
            return;
        }
        inorder(bst_head.right);
        System.out.println("-> "+bst_head.s.popular_name+" embraced Islam at age: "+bst_head.s.conversion.age);
        inorder(bst_head.left);
    }
    static void show_bst(){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter no of Sahaba: ");
        int n = Integer.parseInt(s.nextLine());
        int count = 0;
        for (int i=0 ; i<n ; i++){
            System.out.println("Enter popular name of "+(i+1));
            String p_n = s.nextLine();
            int index = Sahaba.findSahaba(p_n);
            String name = Main.sahabas.get(index).popular_name;
            if (index != -1){
                enqueue(new Node_tree(Main.sahabas.get(index)));
            }
            else {
                System.out.println("The given name is not in our data.\nYou can add Sahaba from Main Menu!");
                count++;
            }
        }
        for (int i=0 ; i<n-count ; i++){
            bst_head = insert_BST(bst_head , dequeue());
        }
        System.out.println("Ordering the given names w.r.t their acceptance of Islam.");
        inorder(bst_head);
        bst_head = null;
    }
    static Node_tree insert_BST_age(Node_tree bst_head , Node_tree root){
        if (bst_head == null){
            bst_head = root;
            bst_head.right = null;
            return bst_head;
        }
        if (root.s.dob >= bst_head.s.dob){
            bst_head.left = insert_BST_age(bst_head.left , root);
        }
        else if (root.s.dob < bst_head.s.dob){
            bst_head.right = insert_BST_age(bst_head.right , root);
        }
        return bst_head;
    }

    static void show_bst_dob(){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter no of Sahaba");
        int n = Integer.parseInt(s.nextLine());
        int count = 0;

        for (int i=0 ; i<n ; i++){
            System.out.println("Enter popular name of "+""+ (i+1));
            String p_n = s.nextLine();
            int index = Sahaba.findSahaba(p_n);

            if (index != -1){
                enqueue(new Node_tree(Main.sahabas.get(index)));
            }
            else {
                System.out.println("The given name is not in our data.\nYou can add Sahaba from Main Menu!");
                count++;
            }
        }
        for (int i=0 ; i<n-count ; i++){
            bst_head = insert_BST_age(bst_head , dequeue());
        }
        System.out.println("Ordering the given names w.r.t their birth year.");

        inorder_dob(bst_head);
        bst_head = null;
    }
    static void inorder_dob(Node_tree bst_head){
        if (bst_head == null){
            return;
        }
        inorder_dob(bst_head.right);
        System.out.println("-> "+bst_head.s.popular_name+" (birth year: "+bst_head.s.dob+")");
        inorder_dob(bst_head.left);
    }

    static void djk_links(){
        boolean isFound = false;
        ArrayList<ArrayList<Links>> graph = Main.links;
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        Scanner s = new Scanner(System.in);

        System.out.println("Enter 1st name:");
        String name1 = s.nextLine();
        int index1 = Sahaba.findSahaba(name1);
        System.out.println("Enter 2nd name:");
        String name2 = s.nextLine();
        int index = Sahaba.findSahaba(name2);
        if (index1 == -1){
            System.out.println("The given name is not in our data.\nYou can add Sahaba from Main Menu!");
            return;
        }
        if (index == -1){
            System.out.println("The given name is not in our data.\nYou can add Sahaba from Main Menu!");
            return;
        }
        boolean[] visit = new boolean[Main.ModValue];
        int[] dist = new int[Main.ModValue];
        int[] par = new int[Main.ModValue];
        String[] relation = new String[Main.ModValue];
        Arrays.fill(par , -1);

        for (int i=0 ; i<dist.length ; i++){
            if (i != index1){
                dist[i] = Integer.MAX_VALUE;
            }
        }
        pq.add(new Pair(index1 , 0));
        while (!pq.isEmpty()){

            Pair p = pq.remove();
            if (!visit[p.node]) {
                visit[p.node] = true;
                for (int i=0 ; i<graph.get(p.node).size() ; i++){
                    Links e = graph.get(p.node).get(i);
                    e.wt = 1;

                    int index2 = Sahaba.findSahaba(e.sahaba.popular_name);
                    int u = p.node;
                    int v = index2;
                    if (dist[u]+e.wt<dist[v]){
                        dist[v] = dist[u] + e.wt;
                        par[v] = u;
                        relation[v] = e.link_type;
                        if (index2==index){
                            isFound = true;
                            break;
                        }
                        pq.add(new Pair(index2 , dist[v]));
                    }
                }
            }
        }
        if (isFound == false){
            System.out.println("There is no direct/indirect link between "+name1+" and "+name2+
                    "\nIf you know a valid connection between them, then you are welcome to do add it from Main Menu!");
            return;
        }
//        for (int i=0;i<Main.ModValue;i++){
//            System.out.println(dist[i]);
//        }
        while (index != index1){
            System.out.println("->"+Main.sahabas.get(par[index]).popular_name+" is "+relation[index]+" of "+Main.sahabas.get(index).popular_name);
            index = par[index];
        }

    }

    static void djk_blood_ties(){
        boolean isFound = false;
        ArrayList<ArrayList<Blood_ties>> graph = Main.blood_ties;
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        Scanner s = new Scanner(System.in);

        System.out.println("Enter 1st name:");
        String name1 = s.nextLine();
        int index1 = Sahaba.findSahaba(name1);
        System.out.println("Enter 2nd name:");
        String name2 = s.nextLine();
        int index = Sahaba.findSahaba(name2);
        if (index1 == -1){
            System.out.println("The given name is not in our data, from main menu you can add with all required details");
            return;
        }
        if (index == -1){
            System.out.println("The given name is not in our data, from main menu you can add with all required details");
            return;
        }
        boolean[] visit = new boolean[Main.ModValue];
        int[] dist = new int[Main.ModValue];
        int[] par = new int[Main.ModValue];
        String[] relation = new String[Main.ModValue];
        Arrays.fill(par , -1);

        for (int i=0 ; i<dist.length ; i++){
            if (i != index1){
                dist[i] = Integer.MAX_VALUE;
            }
        }
        pq.add(new Pair(index1 , 0));
        while (!pq.isEmpty()){

            Pair p = pq.remove();
            if (!visit[p.node]) {
                visit[p.node] = true;
                for (int i=0 ; i<graph.get(p.node).size() ; i++){
                    Blood_ties e = graph.get(p.node).get(i);
                    e.wt = 1;

                    int index2 = Sahaba.findSahaba(e.sahaba.popular_name);
                    int u = p.node;
                    int v = index2;
                    if (dist[u]+e.wt<dist[v]){
                        dist[v] = dist[u] + e.wt;
                        par[v] = u;
                        relation[v] = e.relation;
                        if (index2==index){
                            isFound = true;
                            break;
                        }
                        pq.add(new Pair(index2 , dist[v]));
                    }
                }
            }
        }
        if (isFound == false){
            System.out.println("There is no direct/indirect blood relation between "+name1+" and "+name2+
                    "\nIf you know a valid connection between them, then you are welcome to do this from main menu");
            return;
        }

        while (index != index1){
            System.out.println("->"+Main.sahabas.get(par[index]).popular_name+" is "+relation[index]+" of "+Main.sahabas.get(index).popular_name);
            index = par[index];
        }
    }
}