package algorithms.huffman;

public class HuffmanNode implements Comparable<HuffmanNode>, Cloneable {
    private int frequency;
    private Character character;
    private HuffmanNode left;
    private HuffmanNode right;

    public int getFrequency() {
        return this.frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void incrementFrequency() {
        frequency++;
    }

    public Character getCharacter() {
        return this.character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public HuffmanNode getLeft() {
        return this.left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public HuffmanNode getRight() {
        return this.right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || obj.getClass() != getClass())
            return false;

        HuffmanNode n = (HuffmanNode) obj;

        return n.character == this.character;
    }

    @Override
    public int compareTo(HuffmanNode node) {
        if (this.frequency < node.frequency)
            return -1;
        if (this.frequency > node.frequency)
            return 1;
        if (this.character == null || node.character == null)
            return -1;
        return node.character - this.character;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        HuffmanNode copy = new HuffmanNode();
        copy.frequency = this.frequency;
        if (this.character != null)
            copy.character = Character.valueOf(this.character);
        if (this.left != null)
            copy.left = (HuffmanNode) this.left.clone();
        if (this.right != null)
            copy.right = (HuffmanNode) this.right.clone();

        return copy;
    }
}
