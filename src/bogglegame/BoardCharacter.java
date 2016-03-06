package bogglegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The character on the Boggle board. Complete
 * with the character represented and a boolean
 * array showing where neighbor characters are.
 
 * Includes a method for converting a matrix of
 * characters into BoardCharacters, and another
 * method for constructing all possible words on
 * a given Boggle board given the list of
 * BoardCharacters returned from the aforementioned
 * method.
 * 
 * @author DP
 */
public class BoardCharacter {
    public Character character;
    public boolean[] surroundingCharacters; //0 = top
                                            //1 = right
                                            //2 = bottom
                                            //3 = left
    
    /**
     * Sets the needed information for each board's character piece.
     * 
     * @param character The character on the board
     * @param surroundingCharacters Which sides have characters next to them
     */
    public BoardCharacter(Character character, boolean[] surroundingCharacters){
        if (surroundingCharacters.length != 4){
            throw new IllegalArgumentException("surroundingCharacters array "
                    + "must have a length of 4.");
        }
        this.character = character;
        this.surroundingCharacters = surroundingCharacters;
    }
    
    /**
     * Creates a list of BoardCharacters given a matrix of
     * Characters that represents a Boggle board.
     * 
     * @param board The matrix of characters that represents a board.
     */
    public static List<BoardCharacter> createBoardCharacters(
            List<List<Character>> board){
        //0 = if there is a character to the top
        //1 = if there is a character to the right
        //2 = if there is a character to the bottom
        //3 = if there is a character to the left
        //If top/bottom and right/left, then it is
        //assumed that there is a character diagonally
        //in that direction.
        boolean[] surroundingCharacters;
        
        List<BoardCharacter> boardCharacterList = new ArrayList<>();
        
        //For every character
        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.get(row).size(); col++) {
                surroundingCharacters = new boolean[4];
                if (row > 0){
                    //top
                    surroundingCharacters[0] = true;
                }
                if (col < board.get(row).size() - 1){
                    //right
                    surroundingCharacters[1] = true;
                }
                if (row < board.size() - 1){
                    //bottom
                    surroundingCharacters[2] = true;
                }
                if (col > 0){
                    //left
                    surroundingCharacters[3] = true;
                }
                boardCharacterList.add(new BoardCharacter(board.get(row).get(col),
                        surroundingCharacters));
            }
        }
        
        return boardCharacterList;
    }
    
    /**
     * Builds a word in a boggle-style board (a board that has
     * any number of rows and all columns filled with letters,
     * i.e. no empty character positions in a row) based on
     * the characters around it.
     * Functions by taking a BoardCharacter that has
     * the surroundingCharacter array already filled out.
     * 
     * This method needs to be called for each character in
     * the board. It is designed to run in a for-loop that
     * iterates each BoardCharacter from the boardCharacterList
     * returned by the createBoardCharacters method.
     * 
     * Using the surroundingCharacter array, this method
     * searches all possible combinations of characters that
     * could form a word by creating a "branch" in each
     * direction that has a character next to it. That branch
     * then makes more branches searching in all possible directions.
     * 
     * This repeats until a branch runs out of options, meaning
     * that any letter appended to the current string of characters
     * would not exist in the dictionary's prefixes (list of
     * characters that are not words but that are 1 or more letters
     * away from being a word, where the letters that are missing are
     * only at the end of the word, e.g. synthes would be a prefix of
     * synthesis). The prefix check is necessary to prevent endless
     * word searching.
     * 
     * At any time, if a word created by the character combinations
     * exists in the dictionary's list of full, complete words, then
     * that word is added to the output set (set to prevent duplicates).
     * The search does not end there, however, so that longer words can
     * still be found.
     * 
     * It prevents the usage of the same character twice.
     * 
     * @param ch
     *              The current character being analyzed for
     *              the surrounding characters.
     * @param boardCharacterList 
     *              List of characters and the corresponding
     *              information. Used to find the characters
     *              around the current character (ch).
     * @param legalWords
     *              The set of legal words corresponding to the
     *              current board's letters.
     * @param dict
     *              The dictionary used to compare the words to.
     * @param boardWidth
     *              The width of the current Boggle board.
     * @param previousWord
     *              The previous word created from the
     *              the previous iteration. If null, makes
     *              a new word; if not null, appends the
     *              current character to the string to continue
     *              making a word.
     * @param usedCharacters
     *              A list of BoardCharacters that have already
     *              been used in the current word construction.
     *              This prevents the use of the same character
     *              twice in one word.
     */
    public static void buildWords(BoardCharacter ch,
            List<BoardCharacter> boardCharacterList,
            Set<String> legalWords, Dictionary dict, int boardWidth,
            String previousWord, List<BoardCharacter> usedCharacters){
        
        //Word is the string to be checked if it is a
        //word or not. It is made by appending the current
        //character to all the previous characters.
        String word = null;
        if (previousWord == null){
            //Start the word with the beginning character.
            word = ch.character.toString();
            usedCharacters = new ArrayList<>();
        } else{
            //Append the current character to the word being constructed
            word = previousWord + ch.character.toString();
        }
        //Add the current character to the usedCharacters list
        usedCharacters.add(ch);
        
        //Recursively create all possible character combinations made
        //from combining characters in different directions on the board.
        //Only checks character combinations that exists in the
        //dictionary's prefixes.
        
        //Character exists to the:
        //top
        if (ch.surroundingCharacters[0]){
            int charIndex = boardCharacterList.indexOf(ch) - boardWidth;
            checkNewBoardChar(charIndex, word, boardCharacterList, legalWords, dict,
                    boardWidth, previousWord, usedCharacters);
        }
        //right
        if (ch.surroundingCharacters[1]){
            int charIndex = boardCharacterList.indexOf(ch) + 1;
            checkNewBoardChar(charIndex, word, boardCharacterList, legalWords, dict,
                    boardWidth, previousWord, usedCharacters);
        }
        //bottom
        if (ch.surroundingCharacters[2]){
            int charIndex = boardCharacterList.indexOf(ch) + boardWidth;
            checkNewBoardChar(charIndex, word, boardCharacterList, legalWords, dict,
                    boardWidth, previousWord, usedCharacters);
        }
        //left
        if (ch.surroundingCharacters[3]){
            int charIndex = boardCharacterList.indexOf(ch) - 1;
            checkNewBoardChar(charIndex, word, boardCharacterList, legalWords, dict,
                    boardWidth, previousWord, usedCharacters);
        }
        //diagonally top-right
        if (ch.surroundingCharacters[0] && ch.surroundingCharacters[1]){
            int charIndex = boardCharacterList.indexOf(ch) - boardWidth + 1;
            checkNewBoardChar(charIndex, word, boardCharacterList, legalWords, dict,
                    boardWidth, previousWord, usedCharacters);
        }
        //diagonally top-left
        if (ch.surroundingCharacters[0] && ch.surroundingCharacters[3]){
            int charIndex = boardCharacterList.indexOf(ch) - boardWidth - 1;
            checkNewBoardChar(charIndex, word, boardCharacterList, legalWords, dict,
                    boardWidth, previousWord, usedCharacters);
        }
        //diagonally bottom-right
        if (ch.surroundingCharacters[2] && ch.surroundingCharacters[1]){
            int charIndex = boardCharacterList.indexOf(ch) + boardWidth + 1;
            checkNewBoardChar(charIndex, word, boardCharacterList, legalWords, dict,
                    boardWidth, previousWord, usedCharacters);
        }
        //diagonally bottom-left
        if (ch.surroundingCharacters[2] && ch.surroundingCharacters[3]){
            int charIndex = boardCharacterList.indexOf(ch) + boardWidth - 1;
            checkNewBoardChar(charIndex, word, boardCharacterList, legalWords, dict,
                    boardWidth, previousWord, usedCharacters);
        }
        if (dict.contains(word)){
            //Only add words that are in the dictionary.
            //Do not add prefixes.
            legalWords.add(word);
        }
    }
    
    /**
     * Part of the recursive call of buildWords. This
     * initiates each new call to buildWords, except
     * with a different character.
     * 
     * Basically, its only function is to:
     * 1) get the new boardCharacter (at the charIndex
     * decided from the buildWords method)
     * 2) make a new usedCharacter list that includes
     * the newly-used character (note: it is very
     * important that the newUsedCharacter list is new
     * and not the same object reference as the
     * usedCharacter list in buildWords. This is in order
     * to prevent overwriting the usedCharacter list, which
     * allows the other directions in buildWords to compute
     * without having characters incorrectly marked as used)
     * 3) call buildWords using the above two changes.
     * 
     * @param charIndex
     *          Index of the new character to append to word
     * @param word
     *          The word currently being constructed
     * @param boardCharacterList
     *          List of boardCharacters
     * @param legalWords
     *          List of legal words
     * @param dict
     *          Dictionary used
     * @param boardWidth
     *          Width of the current board
     * @param previousWord
     *          Previous word constructed in the last call to buildWords
     * @param usedCharacters
     *          The list of previously used boardCharacters
     */
    private static void checkNewBoardChar(int charIndex, String word,
            List<BoardCharacter> boardCharacterList,
            Set<String> legalWords, Dictionary dict, int boardWidth,
            String previousWord, List<BoardCharacter> usedCharacters){
        
        BoardCharacter newChar = boardCharacterList.get(charIndex);
        if (dict.containsPrefix(word + newChar.character.toString()) && 
                !usedCharacters.contains(newChar)){
            //Make a new list of usedCharacters such that the
            //original list is not tampered with. This allows the
            //other directions of characters to still use the new
            //character checked here.
            List<BoardCharacter> newUsedCharacters =
                    new ArrayList<>(usedCharacters);
            newUsedCharacters.add(newChar);
            BoardCharacter.buildWords(newChar, boardCharacterList, legalWords,
                    dict, boardWidth, word, newUsedCharacters);
        }
    }
    
}
