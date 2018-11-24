
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Huffman.*;


public class main {
    
    public static void main(String[] args) {
        // get string to encode
        byte[] byteData = readFromFile("Huffman/examples/huffmanBitsString.txt");
        String s = new String(byteData);

        // create a huffman tree (just to see the results, the encode does it automaticaly)
        Node h = Huffman.huffmanTree(s);
        //h.printAll();
        Node[] leaves = h.getLeaves();
        //System.out.println(Arrays.toString(leaves));

        //byte[] codebits = Huffman.huffmanEncode(s);
        byte[] codebits = Huffman.huffmanEncode(h,s);
        //String decodedText = Huffman.huffmanDecode(h, codebits);
        //System.out.println("text:" +decodedText);
        saveToFile("Huffman/examples/huffmanBitsTree.txt",codebits);
        saveToFile("Huffman/examples/huffmanBitsString.txt",s.getBytes()); // just for testing
        saveHuffmanTree("Huffman/examples/huffmanTree.txt", h);
        Node tree = readHuffmanTree("Huffman/examples/huffmanTree.txt");
        tree.printAll();
   
    }

    public static void saveToFile(String filename,byte[] bytes) {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(bytes);
            //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
         } catch (Exception e) {

         }
    }

    public static byte[] readFromFile(String filename) {
        byte[] bytes = new byte[0];
        try (FileInputStream fis = new FileInputStream(filename)) {
           int filesize = fis.available();
           bytes = new byte[filesize];
           fis.read(bytes);
        } catch (Exception e) {

        }
        
        return bytes;
    }

    /**
     * Not EFFECIENT, need to lower the size of Node object.
     */
    public static void saveHuffmanTree(String filename ,Node huffmanTree) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(huffmanTree);
        } catch (Exception e) {

        }
    }

    public static Node readHuffmanTree(String filename) {
        Node tree = null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            tree = (Node) ois.readObject();
        } catch (Exception e) {

        }
        if(tree == null)
            return new Node(' ',0);
        return tree;
    }
}