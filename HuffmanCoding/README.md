# Huffman Coding Application

Huffman coding is an effective lossless variable-length text encoding algorithm. It operates by constructing a character tree based on the frequency of occurrence in a given text sample. In this implementation, the characters are sorted in reversed ASCII order.

The program accepts two command-line arguments, which are strings:
1. The filename of the source file used to construct the Huffman coding tree.
2. Either ’encode’ or ’decode’. Depending on the second argument, the program either encodes or decodes the input it receives:
    - If ’encode’ is chosen, the program accepts a line of text via standard input and encodes it using the Huffman coding tree.
    - If ’decode’ is chosen, the program accepts a sequence of zeros and ones via standard input and decodes it using the Huffman coding tree.