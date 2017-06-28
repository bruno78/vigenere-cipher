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
    
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> dictionary = new HashSet<String>();
        for(String line : fr.lines()){
            dictionary.add(line.toLowerCase());
        }
        return dictionary;
    }
    
    public int countWords(String message, HashSet<String> dictionary){
        String[] words = message.split("\\W+");
        int count = 0;
        for ( int k = 0; k < words.length; k++){
            if(dictionary.contains(words[k])){
                count ++;
            }
        }
        return count;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary){
        VigenereBreaker vb = new VigenereBreaker();
        int maxCount = 0;
        String answer = "";
        for (int k = 0; k < 101; k++){    
            int[] key = vb.tryKeyLength(encrypted, k, 'e');
            VigenereCipher vc = new VigenereCipher(key);
            String message = vc.decrypt(encrypted);
            int currCount = countWords(message, dictionary);
            if (maxCount < currCount){
                maxCount = currCount; 
                answer = message;
            }
        }
        return answer;
    }
}
