package application;

import java.io.File;
import java.net.URL;

import java.util.ArrayList;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;




public class RBTreeAnimation extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		
		try {
			
			// Creates an empty RBT where generics <K, V> are both Integer types
			RBTree<Integer, Integer> newRBTree = new RBTree<>();					
			BorderPane bPane = new BorderPane();	
			// Sends newRBTree to RBTreeView
			RBTreeView treeView = new RBTreeView(newRBTree);
			bPane.setCenter(treeView);
			
			
			
			// This block sets up bottom row of bPane
			TextField tfKey = new TextField();
			tfKey.setPrefColumnCount(3);
			tfKey.setAlignment(Pos.BASELINE_RIGHT);
			Button rbtInsert = new Button("Insert Node");
			Button rbtClear = new Button("Clear all Nodes");
			Button addRandom = new Button("Add Random Key (1 - 100)");
			HBox bottomHBox = new HBox(7);
			Label inputLabel = new Label("Enter a key: ");
			inputLabel.setTextFill(Color.WHITE);
			bottomHBox.getChildren().addAll(inputLabel, tfKey, rbtInsert, rbtClear, addRandom);
			bottomHBox.setAlignment(Pos.CENTER);
			bPane.setBottom(bottomHBox);
			
			
			
			// This block handles "Insert Node" button
			rbtInsert.setOnAction(e -> {
				int key = Integer.parseInt(tfKey.getText());
				if(newRBTree.search(key)){
					treeView.displayTree();
					treeView.setStatus(key + " is already in the tree");
				} else{
					// For the purposes of the GUI, we are only concerned with 'key'
					newRBTree.insert(key, null);
					treeView.displayTree();
					treeView.setStatus(key + " is inserted in the tree");
				}
			});
			
			
			
			// This block handles "Clear all nodes" button
			rbtClear.setOnAction(e -> {
				newRBTree.clearTree();
				newRBTree.size = 0;
				treeView.displayTree();
			});
			
			
			
			// This block handles "Add Random(1 - 100)" button
			addRandom.setOnAction(e -> {
				int key = (int) Math.ceil(Math.random() * 100);
				if(newRBTree.search(key)){
					treeView.displayTree();
					treeView.setStatus("***" + key + " IS ALREADY IN THE TREE***");
				} else{
					// For the purposes of the GUI, we are only concerned with 'key', not value
					newRBTree.insert(key, null);
					treeView.displayTree();
					treeView.setStatus(key + " is inserted in the tree");
				}
			});
			
			
			
			// This block sets up top row of bPane
			Button preorder = new Button("Preorder Traversal");
			Button inorder = new Button("Inorder Traversal");
			Button postorder = new Button("Postorder Traversal");
			Button breadthFirst = new Button("Breadth-First");
			HBox topHBox = new HBox(7);
			topHBox.getChildren().addAll(preorder, inorder, postorder, breadthFirst);
			topHBox.setAlignment(Pos.CENTER);
			bPane.setTop(topHBox);
			
			
			
			// This block handles "Preorder" button
			preorder.setOnAction(e -> {
				
				if(newRBTree.size != 0){
					String keysString = (newRBTree.traversal("pre"));
					treeView.showTraversal("Preorder Traversal: ", keysString);
				}
				else{
					treeView.showTraversal("EMPTY ", "LIST");
				}
			});
			
			
			
			// This block handles "Inorder" button
			inorder.setOnAction(e -> {
				
				if(newRBTree.size != 0){
					String keysString = (newRBTree.traversal("ino"));
					treeView.showTraversal("Inorder Traversal: ", keysString);
				}
				else{
					treeView.showTraversal("EMPTY ", "LIST");
				}
			});
			
			
			
			// This block handles "Postorder" button
			postorder.setOnAction(e -> {
				
				if(newRBTree.size != 0){
					String keysString = (newRBTree.traversal("pos"));
					treeView.showTraversal("Postorder Traversal: ", keysString);
				}
				else{
					treeView.showTraversal("EMPTY ", "LIST");
				}
			});
			
			
			
			// This block handles "Breadth-First" button
			breadthFirst.setOnAction(e -> {
				
				if(newRBTree.size != 0){
					String keysString = (newRBTree.traversal("brd"));
					treeView.showTraversal("Breadth-First: ", keysString);
				}
				else{
					treeView.showTraversal("EMPTY ", "LIST");
				}
			});
			
			
			
			/*
			 *	This block plays music(sandstorm) UNCE UNCE UNCE
			 *	Media usually accepts a URL(internet) but .toURI()
			 *		 
			*/
			String mp3Path = "C:/Users/James/Desktop/sandstorm.mp3";
			Media media = new Media(new File(mp3Path).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(media);
			MediaView mediaView = new MediaView(mediaPlayer);		
			mediaPlayer.setAutoPlay(true);
			
			
			
			/*
			 *	This block adds the background image 
			*/
			String imagePath = ("C:/Users/James/Desktop/redblacktree1.png");
			BackgroundImage background = new BackgroundImage(new Image(new File(imagePath).toURI().toString()), null, null, null, null);
			bPane.setBackground(new Background(background));
			
			
			
			Scene scene = new Scene(bPane, 1500, 850);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Red-Black Tree GUI");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
}
