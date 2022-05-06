package P3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FriendshipGraphTest {

    @Test
    public void testaddVertex() {
        FriendshipGraph G = new FriendshipGraph();
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Person d = new Person("d");
        Person[] persons;
        G.addVertex(a);
        G.addVertex(b);
        G.addVertex(c);
        assertEquals(true, G.getMap().containsKey(a));
        assertEquals(true, G.getMap().containsKey(b));
        assertEquals(true, G.getMap().containsKey(c));
        assertEquals(false, G.getMap().containsKey(d));

    }

    @Test
    public void testaddEdge() {
        FriendshipGraph G = new FriendshipGraph();
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        G.addVertex(a);
        G.addVertex(b);
        G.addVertex(c);
        G.addEdge(a, b);
        assertEquals(true, G.getL().get(G.getMap().get(a)).contains(b));
        assertEquals(false, G.getL().get(G.getMap().get(b)).contains(a));
        assertEquals(false, G.getL().get(G.getMap().get(a)).contains(c));
    }

    @Test
    public void getDistance() {
        FriendshipGraph G = new FriendshipGraph();
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        G.addVertex(a);
        G.addVertex(b);
        G.addVertex(c);
        G.addEdge(a, b);
        G.addEdge(b, a);
        assertEquals(G.getDistance(a, b), 1);
        assertEquals(G.getDistance(b, a), 1);
        assertEquals(G.getDistance(a, a), 0);
        assertEquals(G.getDistance(b, b), 0);
        assertEquals(G.getDistance(c, c), 0);
        assertEquals(G.getDistance(a, c), -1);
        assertEquals(G.getDistance(c, a), -1);
        assertEquals(G.getDistance(b, c), -1);
        assertEquals(G.getDistance(c, b), -1);
    }
}