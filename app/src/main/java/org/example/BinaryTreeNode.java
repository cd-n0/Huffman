package org.example;

class BinaryTreeNode {
    private char character;
	private BinaryTreeNode left, right;
    BinaryTreeNode(char character){
        this.character = character;
        this.left = null;
        this.right = null;
    }
    BinaryTreeNode(char character, BinaryTreeNode left, BinaryTreeNode right){
        this.character = character;
        this.left = left;
        this.right = right;
    }
    BinaryTreeNode(BinaryTreeNode left, BinaryTreeNode right){
        this.character = '\0';
        this.left = left;
        this.right = right;
    }
    BinaryTreeNode(){
        this.character = '\0';
        this.left = null;
        this.right = null;
    }
    public char getCharacter() {
		return character;
	}
	public BinaryTreeNode getLeft() {
		return left;
	}
	public BinaryTreeNode getRight() {
		return right;
	}
}
