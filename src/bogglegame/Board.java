package bogglegame;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A Boggle board comprised of random letters.
 *
 * @author DP
 */
public class Board {
    private static final int SIZE = 4;
    public List<List<Character>> characters;
    private Dictionary dictionary;
    
    public Board(){
        dictionary = new Dictionary();
        initiateBoard();
    }
    
    public void initiateBoard(){
        
    }
    
    public Set<String> getLegalWords(Board board){
        Set<String> legalWords = new TreeSet<>();
        List<BoardCharacter> boardCharacters = BoardCharacter.createBoardCharacters(board.characters);
        for (BoardCharacter character : boardCharacters){
            BoardCharacter.buildWords(character, boardCharacters, legalWords, dictionary, SIZE, null, null);
        }
        return legalWords;
    }
    
}
