
/**
 * Write a description of class tester here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;

public class tester
{
    public void testSliceString(){
        VigenereBreaker vb = new VigenereBreaker();
        System.out.println(vb.sliceString("abcdefghijklm", 0, 3));
        System.out.println(vb.sliceString("abcdefghijklm", 1, 3));
        System.out.println(vb.sliceString("abcdefghijklm", 2, 3));
        System.out.println(vb.sliceString("abcdefghijklm", 0, 4));
        System.out.println(vb.sliceString("abcdefghijklm", 1, 4));
        System.out.println(vb.sliceString("abcdefghijklm", 2, 4));
        System.out.println(vb.sliceString("abcdefghijklm", 3, 4));
        System.out.println(vb.sliceString("abcdefghijklm", 0, 5));
        System.out.println(vb.sliceString("abcdefghijklm", 1, 5));
        System.out.println(vb.sliceString("abcdefghijklm", 2, 5));
        System.out.println(vb.sliceString("abcdefghijklm", 3, 5));
        System.out.println(vb.sliceString("abcdefghijklm", 4, 5));
    }
    
    public void testTryKeylength(){
        FileResource fr = new FileResource("./testCases/athens_keyFlute.txt");
        String encrypted = fr.asString(); 
        VigenereBreaker vb = new VigenereBreaker();
        int[]keys = vb.tryKeyLength(encrypted, 5, 'e');
        for(int k = 0; k < keys.length; k++){
            System.out.println(keys[k]);
        }
    }
    
    public void testVigenereCipher(){
        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere();
    }
    
    public void quiz1(){
        // Quiz 1
        VigenereBreaker v = new VigenereBreaker();
        FileResource fr = new FileResource("./testCases/secretmessage1.txt");
        String message = fr.asString();
        int [] key = v.tryKeyLength(message, 4, 'e');
        for (Integer i: key)
            System.out.print(i + " ");
        System.out.println();
        // Quiz 2
        VigenereCipher vc = new VigenereCipher(key);
        String decrypt = vc.decrypt(message);
        System.out.println(decrypt);
    }
    
    public void testVigenereCipher2(){
        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere();
    }
    
    public void quiz2(){
        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere2();
    }
}
