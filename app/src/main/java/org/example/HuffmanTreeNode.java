package org.example;

class HuffmanTreeNode{
    private Character character;
    private Long frequency;
    private HuffmanTreeNode left;
    private HuffmanTreeNode right;

    HuffmanTreeNode(Character character, Long frequency, HuffmanTreeNode left, HuffmanTreeNode right){
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    HuffmanTreeNode(Long frequency, HuffmanTreeNode left, HuffmanTreeNode right){
        this.character = '\0';
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    HuffmanTreeNode(){
        this.character = '\0';
        this.frequency = 0L;
        this.left = null;
        this.right = null;
    }

    public Character getCharacter(){
        return this.character;
    }

    public Long getFrequency(){
        return this.frequency;
    }

    public HuffmanTreeNode getLeft() {
        return left;
    }

    public HuffmanTreeNode getRight() {
        return right;
    }
}
