package bogglegame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * 
 * @author dPow 3-5-16
 */
public class Main extends Application{
    Board board;            //Boggle board
    HBox buttonBox;         //Button functionality
    BorderPane pane;        //Pane holding the board and buttons
    Button playButton;      //Scores user's input
    Button resetButton;     //Resets the Boggle board
    Scene scene;            //Scene for the stage

    @Override
    public void start(Stage stage) throws Exception {
        int startWidth = 500;
        int startHeight = 600;
        
        board = new Board(startWidth, startHeight);
        buttonBox = new HBox();
        
        pane = new BorderPane();
        pane.setCenter(board);
        pane.setBottom(buttonBox);
        
        //Make the board and buttons
        playButton = new Button("Play");
        playButton.setOnAction((ActionEvent e) -> {
            board.play();
        });
        resetButton = new Button("Reset");
        resetButton.setOnAction((ActionEvent e) -> {
            board = new Board(startWidth, startHeight);
            pane.setCenter(board);
        });
        buttonBox.getChildren().addAll(playButton, resetButton);
        
        scene = new Scene(pane, startWidth, startHeight);
        stage.setScene(scene);
        stage.setTitle("Boggle");
        stage.show();
    }
    
    @Override
    public void stop(){
        System.exit(0);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
