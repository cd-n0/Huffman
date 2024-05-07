package org.example;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

// HashMap, MinHeap, Priority Queue
// Console
public class Huffman implements Serializable{
    private final boolean DEBUG = true;
    private transient String inputString;
    private transient PriorityQueue<HuffmanTreeNode> priorityQueue;
    private HashMap<Character, String> char2huff;
    private HashMap<String, Character> huff2char;
    private HashMap<Character, Long> charFrequency;
    Huffman(String inputString){
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
        String encodedText = encode();
        System.out.println(encodedText);
        System.out.println(decode(encodedText));
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
            } else{
                break;
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
    public String encode(){
        String encodedText = "";
		Character character;
		for(int i = 0; i < inputString.length(); i++){
			character = inputString.charAt(i);
            encodedText = encodedText + char2huff.get(character);
		}
		return encodedText;
	}
	
    /**
     * Decodes the encoded string
     * 
     * @param encodedString The encoded string.
     * @return The decoded string.
     */
	public String decode(String encodedString){
        String decodedText = "";
		String readHuffmanCode = "";
		
		for(int i=0; i < encodedString.length(); i++){
			readHuffmanCode+= encodedString.charAt(i);
			if (huff2char.containsKey(readHuffmanCode)){
				decodedText = decodedText + huff2char.get(readHuffmanCode);
				readHuffmanCode = "";
			}
		}
		return decodedText;
	}
}
