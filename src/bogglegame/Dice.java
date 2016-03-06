package bogglegame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The Dice class is a collection of the dice
 * that would be used in a game of Boggle.
 *
 * @author dPow
 */
public class Dice {
    
    public static List<List<Character>> getDice(){
        int boardSize = 4;
        List<char[]> dice = new ArrayList<>(Arrays.asList(
                    new char[]{'t', 'o', 'e', 's', 's', 'i'},
                    new char[]{'a', 's', 'p', 'f', 'f', 'k'},
                    new char[]{'n', 'u', 'i', 'h', 'm', 'q'},
                    new char[]{'o', 'b', 'j', 'o', 'a', 'b'},
                    new char[]{'l', 'n', 'h', 'n', 'r', 'z'},
                    new char[]{'a', 'h', 's', 'p', 'c', 'o'},
                    new char[]{'r', 'y', 'v', 'd', 'e', 'l'},
                    new char[]{'i', 'o', 't', 'm', 'u', 'c'},
                    new char[]{'l', 'r', 'e', 'i', 'x', 'd'},
                    new char[]{'t', 'e', 'r', 'w', 'h', 'v'},
                    new char[]{'t', 's', 't', 'i', 'y', 'd'},
                    new char[]{'w', 'n', 'g', 'e', 'e', 'h'},
                    new char[]{'e', 'r', 't', 't', 'y', 'l'},
                    new char[]{'o', 'w', 't', 'o', 'a', 't'},
                    new char[]{'e', 'i', 'u', 'n', 'e', 's'},
                    new char[]{'a', 'e', 'a', 'n', 'e', 'g'}));
        
        //Shuffle the distribution of the dice
        Collections.shuffle(dice);
        List<List<Character>> diceCharacters = new ArrayList<>();
        
        for (int row = 0; row < boardSize; row++){
            List<Character> boardRow = new ArrayList<>();
            for (int col = 0; col < boardSize; col++){
                //Select the next die in the dice list
                int diceNumber = row*boardSize + col;
                //Pick a random letter on the die
                int letterIndex = (int) Math.round(Math.random()*5);
                boardRow.add(dice.get(diceNumber)[letterIndex]);
            }
            diceCharacters.add(boardRow);
        }
        
        return diceCharacters;
    }
    
}
