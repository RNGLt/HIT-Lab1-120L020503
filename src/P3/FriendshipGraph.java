package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class FriendshipGraph {
    private Map<Person, Integer> map;
    private ArrayList<ArrayList<Person>> l;

    FriendshipGraph() {
        map = new HashMap<>();
        l = new ArrayList<>();
    }

    public static void main(String[] args) {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println(graph.getDistance(rachel, ross));
//should print 1
        System.out.println(graph.getDistance(rachel, ben));
//should print 2
        System.out.println(graph.getDistance(rachel, rachel));
//should print 0
        System.out.println(graph.getDistance(rachel, kramer));
//should print ‐1
    }

    public Map<Person, Integer> getMap() {
        return this.map;
    }

    public ArrayList<ArrayList<Person>> getL() {
        return this.l;
    }

    public void addVertex(Person p) {
        if (this.map.containsKey(p)) {
            System.out.println("该人员已加入。");
            return;
        } else {
            this.map.put(p, this.l.size());
            this.l.add(new ArrayList<>());
            return;
        }
    }

    public void addEdge(Person p1, Person p2) {
        if (p1.getName() == p2.getName()) {
            System.out.println("同一个人无法建立联系。");
            return;
        } else if (this.map.containsKey(p1) && this.map.containsKey(p2)) {
            this.l.get(this.map.get(p1)).add(p2);
        } else {
            System.out.println("有人员尚未加入。");
            return;
        }
    }

    public int getDistance(Person p1, Person p2) {
        Queue<Person> q = new LinkedList<>();
        ArrayList<Person> Arr = new ArrayList<>();
        int n = 0;
        if (p1.getName() == p2.getName()) {
            return 0;
        }
        Person temp = p1;
        Arr.add(temp);
        do {
            for (Person p : this.l.get(map.get(temp))) {
                if (p.getName() == p2.getName()) {
                    return ++n;
                } else {
                    if (!Arr.contains(p)) {
                        q.offer(p);
                        Arr.add(p);
                    }
                }
            }
            n++;
            temp = q.poll();
        } while (temp != null);
        return -1;
    }
}
