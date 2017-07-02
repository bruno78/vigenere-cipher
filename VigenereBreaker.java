import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    
    private int keyLength;
    private int wordCount;
    private int totalCount;
    
    public VigenereBreaker(){
        keyLength = 0;
        wordCount = 0;
        totalCount = 0;
    }
    
    public void setKeyLength(int keyLength){
        this.keyLength = keyLength;
    }
    
    public int getKeyLength(){
        return keyLength;
    }
    
    public void setWordCount(int wordCount, int totalCount){
        this.wordCount = wordCount;
        this.totalCount = totalCount;
    }
    
    public String getWordCount(){
        return "The total num of words are " + totalCount + "\n" +
               "Total valid words are " + wordCount;
    }

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
            if(dictionary.contains(words[k].toLowerCase())){
                count ++;
            }
        }
        return count;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary){
        int maxCount = 0;
        int keyLength = 0;
        char mostCommonChar = mostCommonCharIn(dictionary);
        for (int k = 1; k <= 100; k++){    
            int[] key = tryKeyLength(encrypted, k, mostCommonChar);
            VigenereCipher vc = new VigenereCipher(key);
            String message = vc.decrypt(encrypted);
            int currCount = countWords(message, dictionary);
            int totalCount = message.split("\\W+").length;
            if (maxCount < currCount){
                maxCount = currCount; 
                setKeyLength(key.length);
                setWordCount(maxCount, totalCount);
            }
        }
        
        for (int k = 1; k <= 100; k++){
            int[] key = tryKeyLength(encrypted, k, mostCommonChar);
            VigenereCipher vc = new VigenereCipher(key);
            String message = vc.decrypt(encrypted);
            int currCount = countWords(message, dictionary);
            if (maxCount == currCount){
               return message;
            }
        }
        return null;
    }
    
    public void breakVigenere2 () {
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        FileResource dr = new FileResource("./dictionaries/English");
        HashSet<String> dictionary = readDictionary(dr);
        String message = breakForLanguage(encrypted, dictionary);
        System.out.println(message);
        System.out.println("The num of words were " + getWordCount());
        System.out.println("The size of key is " + getKeyLength());
    }
    
    /* Method for unkown language */
    
    private void countLetters(String word, HashMap<Character, Integer> lettersFreq){
        String wordLower = word.toLowerCase();
        for (char c : wordLower.toCharArray()){
            if(!lettersFreq.containsKey(c)){
                lettersFreq.put(c, 1);
            }
            else {
                lettersFreq.put(c, lettersFreq.get(c)+1);
            };
        }     
    }
 
    public char mostCommonCharIn(HashSet<String> dictionary){
        HashMap<Character, Integer> lettersFreq = new HashMap<Character, Integer>();
        int maxCount = 0;
        char answer = '\0';
        for (String word : dictionary){
            countLetters(word, lettersFreq);
        }       
        
        for (char c : lettersFreq.keySet()){
            if (maxCount < lettersFreq.get(c)){
                maxCount = lettersFreq.get(c);
            }
        }
        
        for (char c : lettersFreq.keySet()){
            if (lettersFreq.get(c) == maxCount){
                answer = c;
            }
        }
        return answer;
    }
    
    public void breakForAllLangs(String encrypted, HashMap<String, 
                                                   HashSet<String>> languages){
        int maxCount = 0;
        for(String language : languages.keySet()) {
            HashSet<String> dictionary = new HashSet<String>(languages.get(language));
            String testDecryption = breakForLanguage(encrypted, dictionary);
            if(maxCount < countWords(testDecryption, dictionary)) {
                maxCount = countWords(testDecryption, dictionary);
            }
        }
        for (String language: languages.keySet()){           
            String answer = breakForLanguage(encrypted, languages.get(language));
            int currCount = countWords(answer, languages.get(language));
            if (currCount == maxCount){        
                System.out.println(answer);
                System.out.println(language + " was the language used to decrypt this message!");
            }
        }

    }
    
    public void breakVigenere3() {
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        String[] languages = {"Danish","Dutch","English","French","German","Italian","Portuguese","Spanish"};
        HashMap<String, HashSet<String>> dictionary = new HashMap<String, HashSet<String>>();
        for(int k = 0; k < languages.length; k++){
            FileResource file = new FileResource("./dictionaries/" + languages[k]);
            HashSet<String> languageDictionary = readDictionary(file);
            dictionary.put(languages[k], languageDictionary);
        }
        breakForAllLangs(encrypted, dictionary);
        System.out.println(getWordCount());
        System.out.println("The size of key is " + getKeyLength());
    }
}
