import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

// HashMap, MinHeap, Priority Queue
// Console
public class Huffman implements Serializable{
    private final boolean DEBUG = false;
    private transient String inputString;
    private transient String encodedString;
    private transient String decodedString;
    private transient PriorityQueue<HuffmanTreeNode> priorityQueue;
    private HashMap<Character, String> char2huff;
    private HashMap<String, Character> huff2char;
    private HashMap<Character, Long> charFrequency;
    Huffman(String inputString){
        this.encodedString = "";
        this.decodedString = "";
        this.inputString = inputString;
        this.char2huff = new HashMap<>();
        this.huff2char = new HashMap<>();
        charFrequency = calculateCharacterFrequency();
        Comparator<HuffmanTreeNode> cmp = new Comparator<HuffmanTreeNode>() {
            @Override
            public int compare(HuffmanTreeNode huffmanNode1, HuffmanTreeNode huffmanNode2) {
                /* If any of the 2 characters aren't in the frequency hashmap return 0 */
                if (huffmanNode1.getFrequency() == null || huffmanNode2.getFrequency() == null){
                    return 0;
                }
                if (huffmanNode1.getFrequency() > huffmanNode2.getFrequency()) {
                    if (DEBUG)System.out.println(huffmanNode1.getCharacter() + " is more than " + huffmanNode2.getCharacter());
                    return 1;
                } else {
                    if (DEBUG)System.out.println(huffmanNode1.getCharacter() + " is less than " + huffmanNode2.getCharacter());
                    return -1;
                }
            }
        };
        priorityQueue = new PriorityQueue<HuffmanTreeNode>(charFrequency.size(), cmp);
        generateCodes(generateTree());
        encode();
    }

    Huffman(String inputEncodedString, String inputSerFile) throws FileNotFoundException, ClassNotFoundException, IOException{
        deserializeMaps(inputSerFile);
        this.encodedString = inputEncodedString;
        decode();
    }

    /**
     * Generates a Huffman tree based on the character frequencies.
     * 
     * @return The root node of the Huffman tree.
     */
    private HuffmanTreeNode generateTree(){
        generateMinheap();
        HuffmanTreeNode root = new HuffmanTreeNode();
        HuffmanTreeNode left, right;
        while (!priorityQueue.isEmpty()){
            left = priorityQueue.poll();
            if (priorityQueue.peek() != null){
                right = priorityQueue.poll();
                root = new HuffmanTreeNode(left.getFrequency() + right.getFrequency(), left, right);
            } else{
                root = new HuffmanTreeNode(left.getFrequency(), left, null);
            }
            if (priorityQueue.peek() != null){
                priorityQueue.offer(root);
            }
        }
        return root;
    }

    /**
     * Calculates the frequency of each character in the input text.
     * 
     * @return A HashMap containing characters as keys and their corresponding frequencies as values.
     */
    private HashMap<Character, Long> calculateCharacterFrequency(){
        HashMap<Character, Long> charFrequency = new HashMap<>();
        for (int i = 0; i < inputString.length(); i++){
            if(charFrequency.containsKey(inputString.charAt(i))) {
                charFrequency.put(inputString.charAt(i), charFrequency.get(inputString.charAt(i)) + 1L);
            }   else {
                charFrequency.put(inputString.charAt(i), 1L);
            }
        }

        return charFrequency;
    }

    /**
     * Generates a min-heap of Huffman tree nodes based on character frequencies.
     */
    private void generateMinheap(){
        for (Character character: charFrequency.keySet()){
            Long frequency = charFrequency.get(character);
            HuffmanTreeNode node = new HuffmanTreeNode(character, frequency, null, null);
            priorityQueue.offer(node);
        }
    }

    /**
     * Generates Huffman codes recursively for each character in the Huffman tree.
     * 
     * @param node The current node being traversed.
     * @param code The Huffman code generated so far for the current node.
     */
    private void generateCodesRecursive(HuffmanTreeNode node, String code){
        if (node != null){
            if (!isLeafNode(node)){
                generateCodesRecursive(node.getLeft(), code + "0");
                generateCodesRecursive(node.getRight(), code + "1");
            }
            else {
                char2huff.put(node.getCharacter(), code);
                huff2char.put(code, node.getCharacter());
            }
        }
    }

    /**
     * Generates Huffman codes for each character in the Huffman tree.
     * 
     * @param root The root node of the Huffman tree.
     */
    private void generateCodes(HuffmanTreeNode root){
        String code = "";
        HuffmanTreeNode node = root;
        generateCodesRecursive(node, code);
    }

    /**
     * Checks if a given node is a leaf node in the Huffman tree.
     * 
     * @param node The node to be checked.
     * @return {@code true} if the node is a leaf node, {@code false} otherwise.
     */
    private boolean isLeafNode(HuffmanTreeNode node){
        return node.getLeft() == null && node.getRight() == null;
    }

    /**
     * Encodes the original string
     * 
     * @return The encoded string.
     */
    private String encode(){
		Character character;
		for(int i = 0; i < inputString.length(); i++){
			character = inputString.charAt(i);
            encodedString += char2huff.get(character);
		}
		return encodedString;
	}
	
    /**
     * Decodes the encoded string
     * 
     * @return The decoded string.
     */
	private String decode(){
		String readHuffmanCode = "";
		
		for(int i = 0; i < encodedString.length(); i++){
			readHuffmanCode += encodedString.charAt(i);
			if (huff2char.containsKey(readHuffmanCode)){
				decodedString += huff2char.get(readHuffmanCode);
				readHuffmanCode = "";
			}
		}
        if(decodedString != null)decodedString = decodedString.substring(4);
		return decodedString;
	}

    /**
     * Gets the value of how much the text is compressed compared to the original one percentage-wise.
     * 
     * @return The compression rate in percentage.
     */
    public double getCompressionPercentage(){
        Long currentLength = inputString.length() * 8L;
        int paddingLength = encodedString.length() % 8;
        return (double) (encodedString.length() + paddingLength + 8) / currentLength * 100;
    }

    private void deserializeMaps(String inputFile) throws FileNotFoundException, ClassNotFoundException, IOException{
        Huffman huffman = (Huffman) FileIO.Deserialize(inputFile);
        this.char2huff = huffman.char2huff;
        this.huff2char = huffman.huff2char;
    }

    public String getEncodedString() {
        return encodedString;
    }

    public String getDecodedString() {
        return decodedString;
    }
}
