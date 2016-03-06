package bogglegame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * A Dictionary is an object containing a list of words
 * and the corresponding prefixes. Prefixes are defined
 * as all the letter combinations that make up a word
 * excluding one or more letters from the end of the word.
 * e.g. "dictiona" and "di" and "dictionar" are all prefixes
 * of the word "dictionary"
 *
 * @author dPow 3-5-16
 */
public class Dictionary {
    private Set<String> words;
    private Set<String> prefixes;
    
    public Dictionary(){
        words = new HashSet<>();
        InputStream wordsFile = this.getClass().getResourceAsStream("/boggledictionary/AcceptableWords.txt");
        boolean keepReading = true;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(wordsFile))){
            while (keepReading){
                String word = br.readLine();
                if (word == null){
                    keepReading = false;
                    break;
                }
                words.add(word);
            }
            //No need to close BufferedReader; it is in try-with-resources
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        prefixes = new HashSet<>();
        for (String word : words){
            //For every character combination up to the last letter of
            //the actual word
            for (int i = 1; i < word.length(); i++){
                String prefix = word.substring(0, i);
                prefixes.add(prefix);
            }
        }
    }
    
    public boolean contains(String word){
        return words.contains(word);
    }
    
    public boolean containsPrefix(String prefix){
        return prefixes.contains(prefix);
    }
        
    public Set<String> getWords(){
        return words;
    }
    
    public Set<String> getPrefixes(){
        return prefixes;
    }
    
}
