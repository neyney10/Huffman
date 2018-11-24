package Huffman;

import java.io.Serializable;
import java.util.Arrays;
import java.util.BitSet;

public class Node implements Serializable{
    static final long serialVersionUID = 213543123;
    char ch;
    int freq;
    Node left, right;
    BitSet code;
    int codeLength;

    public Node(char c, int f) {
        this.ch = c;
        this.freq = f;
    }

    public Node(char c, int f, Node l, Node r) {
        this.ch = c;
        this.freq = f;
        this.left = l;
        this.right = r;
    }

    public void printAll() {
        System.out.println("Total nodes in the tree: "+printNode(this));
        
    }
    

    private int printNode(Node node) {
        if(node == null)
            return 0;

        System.out.println(node);
        int l = printNode(node.left);
        int r = printNode(node.right);

        return l+r+1;
    }

    public Node[] getLeaves() {
        return getLeaf(this, new BitSet(1), 0);
    }

    private Node[] getLeaf(Node node, BitSet code, int depth) {
        if(node == null)
            return new Node[0];
        
        if(node.left == null && node.right == null) {
            // sets code
            node.code = code;//code.clone();
            node.codeLength = depth;

            return new Node[] {node};
        }
           
        BitSet codeLeft = addBit(code, false, depth);
        BitSet codeRight = addBit(code, true, depth);
        Node[] l = getLeaf(node.left, codeLeft, depth+1);
        Node[] r = getLeaf(node.right, codeRight, depth+1);

        return merge(l,r);
    }

    private BitSet addBit(BitSet bits,boolean newBit, int range) {
        BitSet b = new BitSet(range+1);
        for(int i = 0 ; i< range ; i++) 
            b.set(i, bits.get(i));
        
        b.set(range, newBit);
        return b;
    }

    private Node[] merge(Node[] l , Node[] r) {
        Node[] both = new Node[l.length + r.length];
        int k=0;

        for(Node n : l)
            both[k++] = n;

        for(Node n : r)
            both[k++] = n;

        return both;
    }


    @Override
    public String toString() {
        String bitcode = (this.code == null)? "?" : Arrays.toString(code.toByteArray());;
        return "(Char: "+this.ch + " : " + this.freq + " , bitcode: "+ bitcode + ")";
    }

    
}