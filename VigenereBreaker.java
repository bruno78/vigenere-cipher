import java.util.*;
import edu.duke.*;

public class VigenereBreaker {

    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder slicedString = new StringBuilder("");
        for (int k = whichSlice; k < message.length(); k += totalSlices) {
            slicedString.append(message.substring(k, k+1));
        }
        return slicedString.toString();
    }
    
    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        //use caesar cracker
        CaesarCracker cc = new CaesarCracker(mostCommon);
        for (int k = 0; k < klength; k++){
            String seq = sliceString(encrypted, k, klength);
            int num = cc.getKey(seq);
            key[k] = num;
        }
        return key;
    }

    public void breakVigenere () {
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        int[]keys = tryKeyLength(encrypted, 5, 'e');
        VigenereCipher vc = new VigenereCipher(keys);
        System.out.println(vc.decrypt(encrypted));
    }
    
    /* Method for Unknown length  */
    
    
}
