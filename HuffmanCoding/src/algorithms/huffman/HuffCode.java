package algorithms.huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class HuffCode {
    private static ArrayList<HuffmanNode> nodes;

    private static void buildHuffmanNodes(File file) throws FileNotFoundException, IOException {
        FileReader fReader = new FileReader(file);

        int character = fReader.read();
        if (character == -1) {
            fReader.close();
            throw new IOException("File is empty.");
        }

        nodes = new ArrayList<>(10);

        while (character != -1) {
            HuffmanNode newNode = new HuffmanNode();
            newNode.setCharacter((char) character);
            int indexOfNode = nodes.indexOf(newNode);

            if (indexOfNode == -1) {
                newNode.setFrequency(1);
                nodes.add(newNode);
            } else {
                nodes.get(indexOfNode).incrementFrequency();
            }

            character = fReader.read();
        }

        fReader.close();
    }

    private static PriorityQueue<HuffmanNode> priorityQueue;

    private static void buildPriorityQueue() {
        priorityQueue = new PriorityQueue<>();
        int size = nodes.size();
        for (int i = 0; i < size; i++) {
            priorityQueue.add(nodes.get(i));
        }
    }

    private static HuffmanCodingTree huffmanCodingTree;

    private static void buildHuffmanCodingTree() {
        int queueSize = priorityQueue.size();
        while (queueSize > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();

            HuffmanNode newNode = new HuffmanNode();
            newNode.setLeft(left);
            newNode.setRight(right);
            newNode.setFrequency(left.getFrequency() + right.getFrequency());

            priorityQueue.add(newNode);
            queueSize--;
        }

        huffmanCodingTree = new HuffmanCodingTree(priorityQueue.poll());
    }

    private static String huffmanCode;
    private static int maxHuffmanCodeLength;
    private static ArrayList<HuffmanLookup> lookupList;

    private static void traverseHuffmanTree(HuffmanNode node) {
        if (node == null)
            return;

        if (node.getCharacter() != null) {
            lookupList.add(new HuffmanLookup(node.getCharacter(), huffmanCode));
            if (huffmanCode.length() > maxHuffmanCodeLength)
                maxHuffmanCodeLength = huffmanCode.length();
            return;
        }

        huffmanCode += "1";
        traverseHuffmanTree(node.getLeft());
        huffmanCode = huffmanCode.substring(0, huffmanCode.length() - 1);

        huffmanCode += "0";
        traverseHuffmanTree(node.getRight());
        huffmanCode = huffmanCode.substring(0, huffmanCode.length() - 1);
    }

    private static void buildLookupList() {
        huffmanCode = "";
        maxHuffmanCodeLength = 0;
        int size = nodes.size();
        lookupList = new ArrayList<>(size);
        traverseHuffmanTree(huffmanCodingTree.getRoot());

        // Sort lookup list from shorter codes (a.k.a. more frequently encountered ones)
        // to longer ones for optimal performance when encoding and decoding.
        for (int i = 1; i < size; i++) {
            HuffmanLookup key = lookupList.get(i);
            int j = i - 1;

            while (j >= 0 && key.getCode().length() < lookupList.get(j).getCode().length()) {
                lookupList.set(j + 1, lookupList.get(j));
                j--;
            }

            lookupList.set(j + 1, key);
        }
    }

    private static String encode(String string) throws IllegalArgumentException {
        String encodedString = "";
        int lookupListSize = lookupList.size();
        boolean foundEncoding;
        HuffmanLookup huffmanLookup;

        for (char character : string.toCharArray()) {
            foundEncoding = false;
            for (int i = 0; i < lookupListSize; i++) {
                huffmanLookup = lookupList.get(i);
                if (huffmanLookup.getCharacter() == character) {
                    encodedString += huffmanLookup.getCode();
                    foundEncoding = true;
                    break;
                }
            }
            if (!foundEncoding)
                throw new IllegalArgumentException("Did not find an encoding for the following character: "
                        + Character.toString(character));
        }
        return encodedString;
    }

    private static String decode(String encodedString) throws IllegalArgumentException {
        String decodedString = "";
        int lookupListSize = lookupList.size();
        HuffmanLookup huffmanLookup;
        boolean foundCharacter = false;

        int startIndex = 0;
        int endIndex = 1;
        int encodedStringLength = encodedString.length();
        while (startIndex < encodedString.length() && endIndex <= encodedStringLength) {
            String substring = encodedString.substring(startIndex, endIndex);

            if (substring.length() > maxHuffmanCodeLength)
                throw new IllegalArgumentException("Did not find a matching character for the following code: "
                        + substring);

            for (int i = 0; i < lookupListSize; i++) {
                huffmanLookup = lookupList.get(i);
                if (huffmanLookup.getCode().equals(substring)) {
                    decodedString += Character.toString(huffmanLookup.getCharacter());
                    foundCharacter = true;
                    break;
                }
            }

            if (foundCharacter) {
                startIndex = endIndex;
                endIndex = startIndex + 1;
                foundCharacter = false;
            } else {
                endIndex++;
            }
        }

        if (startIndex < encodedStringLength)
            throw new IllegalArgumentException("Did not find a matching character for the following code: "
                    + encodedString.substring(startIndex, encodedStringLength));

        return decodedString;
    }

    public static void main(String[] args) {
        // #region validate args
        if (args.length < 2) {
            System.out.println(
                    "Too few arguments passed. Two arguments needed (file name AND 'encode' OR 'decode' option).");
            return;
        }
        if (args.length > 2) {
            System.out.println(
                    "Too many arguments passed. Two arguments needed (file name AND 'encode' OR 'decode' option).");
            return;
        }

        File haikuFile = new File(args[0]);

        if (!haikuFile.exists()) {
            System.out.println("File does not exist: " + args[0]);
            return;
        }
        if (!haikuFile.canRead()) {
            System.out.println("Cannot read file: " + args[0]);
            return;
        }

        String mode = args[1];

        if (!mode.equalsIgnoreCase("decode") && !mode.equalsIgnoreCase("encode")) {
            System.out.println("Second argument expected to be either 'decode' OR 'encode'.");
            return;
        }
        // #endregion

        try {
            buildHuffmanNodes(haikuFile);
        } catch (FileNotFoundException exception) {
            System.out.println("File does not exist: " + args[0]);
            return;
        } catch (IOException exception) {
            System.out.println("Error while trying to read file: " + args[0]);
            return;
        }

        buildPriorityQueue();
        buildHuffmanCodingTree();
        buildLookupList();

        Scanner standardInput = new Scanner(System.in);
        if (mode.equalsIgnoreCase("encode")) {
            try {
                System.out.println(encode(standardInput.nextLine()));
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        } else {
            try {
                System.out.println(decode(standardInput.nextLine()));
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
        standardInput.close();
    }
}
