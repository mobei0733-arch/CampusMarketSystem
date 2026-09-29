public class MyHashMap {
    private static int size=16;
    private Node[] table;
    private static class Node{
        int key;//用户id
        User value;//用户名
        Node next;
    }
}
