package algorithms.huffman;

public class PriorityQueue<E extends Comparable<E>> {
    class Node {
        private E element;
        private Node next;

        public Node(E element) {
            this.element = element;
        }

        public Node(E element, Node next) {
            this.element = element;
            this.next = next;
        }
    }

    private Node head;

    public boolean add(E newElement) {
        if (head == null) {
            head = new Node(newElement);
            return true;
        }

        if (head.element.compareTo(newElement) > 0) {
            head = new Node(newElement, head);
            return true;
        }

        Node n = head;
        while (n.next != null && n.next.element.compareTo(newElement) < 0) {
            n = n.next;
        }
        n.next = new Node(newElement, n.next);
        return true;
    }

    public E peek() {
        return head == null ? null : head.element;
    }

    public E poll() {
        if (head == null)
            return null;

        E element = head.element;
        head = head.next;
        return element;
    }

    public boolean contains(E element) {
        Node n = head;
        while (n != null) {
            if (n.element.equals(element))
                return true;
            n = n.next;
        }
        return false;
    }

    public int size() {
        int count = 0;
        Node n = head;
        while (n != null) {
            count++;
            n = n.next;
        }
        return count;
    }
}
