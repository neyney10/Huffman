package Huffman;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;


public class Huffman {

    /**
     * Create Huffman Tree from String using Huffman Nodes
     * @param str as input String or text
     * @return Huffman Node represents the Huffman Tree.
     */
    public static Node huffmanTree(String str) {
        return huffmanTree(getFrequency(str));
    }

    /**
     * Create Huffman Tree from String using Huffman Nodes
     * @param huff as the frequency list from type ArrayList<Node> 
     * @return Huffman Node represents the Huffman Tree.
     */
    public static Node huffmanTree(ArrayList<Node> huff) {
        if(huff.size() == 0)
            return null;

        if(huff.size() == 1)
            return huff.get(0);

        // get minimum
        Iterator<Node> iter = huff.iterator();
        Node min1 = iter.next(), min2 =  iter.next(), t;

        while(iter.hasNext()) {
            t = iter.next();
            if(min1.freq > t.freq) { //
                if(min2.freq > t.freq) { // smaller than both
                    if(min2.freq > min1.freq) // check who is smallest / LARGEST
                        min2 = t;
                        else min1 = t;
                } else min1 = t; //smaller only than min1
            } else { 
                if(min2.freq > t.freq) // smaller only than min2
                    min2 = t;
            }
       }

       huff.remove(min1);
       huff.remove(min2);

       Node newNode = new Node('\0', min1.freq + min2.freq, min1, min2);
       huff.add(newNode);

       return huffmanTree(huff);
    }

     /**
     * Create Huffman frequencies list
     * @param str as input String or text
     * @return List of frequencies for each letter
     */
    public static ArrayList<Node> getFrequency(String str) {

        ArrayList<Node> list = new ArrayList<>();
        char c;
        boolean found = false;

        for(int i = 0 ; i < str.length() ; i++) {
            c = str.charAt(i);
            found = false;

            for (Node t : list) {
                if(t.ch == c) {
                    t.freq++;
                    found = true;
                    break;
                }
            }
            if(!found)
                list.add(new Node(c,1));
            
        }

        return list;
    }


    /**
     * Creates Huffman Dictionary - all the letters with their code and frequencies in a ready-to-go list.
     * @param huffmanTree already made Huffman Tree
     * @return list of huffman nodes
     */
    public static Node[] huffmanDictionary(Node huffmanTree) {
        return huffmanTree.getLeaves();
    }

    /**
     * Encode a text/string, creates a Huffman Tree for it and then encoding.
     * @param str the text/string to encode
     * @return byte array represents the minimal representation of the string in Huffman Code
     */
    public static byte[] huffmanEncode(String str) {
        return huffmanEncode(Huffman.huffmanTree(str), str);
    }

    /**
     * Encode a text/string with a given Huuffman Tree
     * @param str the text/string to encode
     * @return byte array represents the minimal representation of the string in Huffman Code
     */
    public static byte[] huffmanEncode(Node huffmanTree, String str) {
        Node[] dictionary = huffmanDictionary(huffmanTree);
        int totalBits = getDictionaryBitSize(dictionary);
        BitSet encodedString = new BitSet(totalBits);
        
        int k =0;
        char c;

        for(int i = 0 ; i < str.length(); i++) {
            c = str.charAt(i);
            for(Node n : dictionary) {
                if(n.ch == c) {
                    for(int j = 0 ; j < n.codeLength ; j++)
                        encodedString.set(k++, n.code.get(j));
                    break;
                }
            }
        }
        return encodedString.toByteArray();
    }

    /**
     * Decode a huffman-coded string (given as byte array) to a ascii string
     */
    public static String huffmanDecode(Node huffmanTree, byte[] encodedString) {
        StringBuilder decodedString = new StringBuilder();
        Node index = huffmanTree;
        BitSet bits = BitSet.valueOf(encodedString);
        int totalBits = getDictionaryBitSize(huffmanDictionary(huffmanTree));

        for(int i = 0 ; i < totalBits ; i++) {
            if(bits.get(i))
                index = index.right;
            else index = index.left;

            if(index.left == null && index.right == null) {
                decodedString.append(index.ch);
                index = huffmanTree; // reset pos
            }
        }

        return decodedString.toString();
    }

    /**
     * get the total bits from Huffman Dictionary
     */
    public static int getDictionaryBitSize(Node[] huffmanDictionary) {
        int totalBits = 0;
        for(Node n : huffmanDictionary) {
            totalBits += n.codeLength*n.freq;
        }
       
        return totalBits;
    }


}