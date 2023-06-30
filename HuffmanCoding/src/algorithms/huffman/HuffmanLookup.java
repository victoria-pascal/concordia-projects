package algorithms.huffman;

public class HuffmanLookup {
    private char character;
    private String code;

    public HuffmanLookup(char character, String code) {
        this.character = character;
        this.code = code;
    }

    public char getCharacter() {
        return this.character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
