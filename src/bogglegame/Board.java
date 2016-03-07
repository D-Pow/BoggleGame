package bogglegame;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * A Boggle characters comprised of random letters.
 *
 * @author dPow
 */
public class Board extends Pane{
    private static final int SIZE = 4;
    private Dictionary dictionary;
    public List<List<Character>> characters;
    private Set<String> legalWords;
    private TextArea textArea;
    private Set<String> correctGuesses;
    private Set<String> incorrectGuesses;
    
    /**
     * Instantiates a new playing board complete with
     * a grid showing the character choices and a text
     * area for user input.
     * 
     * @param startWidth The width of the board
     * @param startHeight The height of the board
     */
    public Board(int startWidth, int startHeight){
        dictionary = new Dictionary();
        characters = Dice.getDice();
        legalWords = getLegalWords(characters);
        
        this.setWidth(startWidth);
        this.setHeight(startHeight);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(SIZE*6);
        grid.setVgap(SIZE*3);
        
        for (int row = 0; row < characters.size(); row++){
            for (int col = 0; col < characters.get(row).size(); col++){
                Text character = new Text();
                Font font = new Font("vernanda", SIZE*15);
                character.setFont(font);
                String c = characters.get(row).get(col).toString();
                //If the character is a 'q', then add a 'u' after it
                if (c.equals("q")){
                    c = "qu";
                }
                character.setText(c);
                grid.add(character, col, row);
            }
        }
        
        grid.setPadding(new Insets(0, 0, 0, this.getWidth()/4));
        
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPrefSize(startWidth/2, startHeight/8);
        textArea.setLayoutX(startWidth/4);
        textArea.setLayoutY(startHeight - startHeight/3);
        
        this.getChildren().addAll(grid, textArea);
    }
    
    /**
     * Takes the user's input and:
     * 1) scores the guessed words (excluding repeats)
     * 2) segregates words based on correct or incorrect guesses
     * 3) replaces the textArea content with the score, correct
     *    and incorrect guesses, and the missed words that were
     *    possible to make given the characters on the board.
     */
    public void play(){
        String input = textArea.getText();
        input = input.toLowerCase();
        String[] userGuesses = input.split("\\W+");
        int score = scoreUserInput(userGuesses);
        textArea.clear();
        
        String results = String.format("Score: %d%n%n", score);
        results += "Correct Guesses: \n";
        for (String correctGuess : correctGuesses){
            results += correctGuess + " ";
        }
        results += "\n\nIncorrect Guesses: \n";
        for (String incorrectGuess : incorrectGuesses){
            results += incorrectGuess + " ";
        }
        results += "\n\nMissed Words: \n";
        for (String missedWord : legalWords){
            if (!correctGuesses.contains(missedWord)){
                results += missedWord + " ";
            }
        }
        
        textArea.setText(results);
        textArea.setEditable(false);
    }
    
    /**
     * Returns all possible character combinations on the board that
     * make correct words according to the supplied dictionary file.
     * 
     * @param characters The characters given by the current Boggle board
     * @return validWords The set of words that would be valid according
     *                    to the supplied dictionary
     */
    public Set<String> getLegalWords(List<List<Character>> characters){
        Set<String> validWords = new TreeSet<>();
        List<BoardCharacter> boardCharacters = BoardCharacter.createBoardCharacters(characters);
        for (BoardCharacter character : boardCharacters){
            BoardCharacter.buildWords(character, boardCharacters, validWords, dictionary, SIZE, null, null);
        }
        return validWords;
    }
    
    /**
     * Takes the user's input and splits it into correct
     * and incorrect guesses while also scoring the
     * correct guesses.
     * 
     * @param guesses Array of the user's guesses
     * @return score
     */
    public int scoreUserInput(String[] guesses){
        correctGuesses = new TreeSet<>();
        incorrectGuesses = new TreeSet<>();
        int score = 0;
        
        for (String word : guesses){
            if (legalWords.contains(word)) {
                correctGuesses.add(word);
                score += scoreWord(word);
            } else {
                incorrectGuesses.add(word);
            }
        }
        
        return score;
    }
    
    /**
     * Scores a word based on its length.
     * 
     * @param word The word to be scored
     * @return The score value for the word
     */
    public int scoreWord(String word){
        int len = word.length();
        if (len >= 8) {
            return 11;
        } else if (len == 7) {
            return 5;
        } else if (len == 6) {
            return 3;
        } else if (len == 5) {
            return 2;
        } else if (len == 4 || len == 3) {
            return 1;
        }
        return 0;
    }
    
}
