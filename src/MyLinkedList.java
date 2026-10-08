public class MyLinkedList<Order> {
    private static class Node<Order> {

         Order data;          // 节点保存的数据
        Node<Order> next;    // 指向下一个节点

        Node(Order data) {
            this.data = data;
            this.next = null;
        }
    }
    private Node<Order> head;
    private int size;

    public MyLinkedList() {
        head = null;
        size = 0;
    }

    public void add(Order data) {

        Node<Order> newNode = new Node<>(data);

        // 链表为空
        if (head == null) {
            head = newNode;
        } else {

            // 找到最后一个节点
            Node<Order> current = head;

            while (current.next != null) {
                current = current.next;
            }

            // 最后一个节点指向新节点
            current.next = newNode;
        }

        size++;
    }

    public Order get(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("下标越界");
        }

        Node<Order> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    public Order remove(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("下标越界");
        }

        // 删除头节点
        if (index == 0) {

            Order data = head.data;

            head = head.next;

            size--;

            return data;
        }

        Node<Order> current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        // 保存要删除的数据
        Order data = current.next.data;

        // 跳过要删除的节点
        current.next = current.next.next;

        size--;

        return data;
    }

    // 获取链表长度
    public int size() {
        return size;
    }

    // 判断链表是否为空
    public boolean isEmpty() {
        return size == 0;
    }
}
