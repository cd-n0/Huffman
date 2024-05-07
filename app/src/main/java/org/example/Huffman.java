package org.example;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

// HashMap, MinHeap, Priority Queue
// Console
public class Huffman implements Serializable{
    private final boolean DEBUG = true;
    private transient String text;
    private transient PriorityQueue<HuffmanTreeNode> priorityQueue;
    private HashMap<Character, String> char2huff;
    private HashMap<String, Character> huff2char;
    private HashMap<Character, Long> charFrequency;
    Huffman(String text){
        this.text = text;
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
        System.out.println(char2huff);
    }

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

    private HashMap<Character, Long> calculateCharacterFrequency(){
        HashMap<Character, Long> charFrequency = new HashMap<>();
        for (int i = 0; i < text.length(); i++){
            if(charFrequency.containsKey(text.charAt(i))) {
                charFrequency.put(text.charAt(i), charFrequency.get(text.charAt(i)) + 1L);
            }   else {
                charFrequency.put(text.charAt(i), 1L);
            }
        }

        return charFrequency;
    }

    private void generateMinheap(){
        for (Character character: charFrequency.keySet()){
            Long frequency = charFrequency.get(character);
            HuffmanTreeNode node = new HuffmanTreeNode(character, frequency, null, null);
            priorityQueue.offer(node);
        }
    }

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

    private void generateCodes(HuffmanTreeNode root){
        String code = "";
        HuffmanTreeNode node = root;
        generateCodesRecursive(node, code);
    }

    private boolean isLeafNode(HuffmanTreeNode node){
        return node.getLeft() == null && node.getRight() == null;
    }
}
