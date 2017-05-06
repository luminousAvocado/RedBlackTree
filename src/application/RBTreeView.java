package application;

import java.sql.ResultSet;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;



public class RBTreeView extends Pane{

	
	private RBTree newTree = new RBTree<>();
	private double radius = 20; //Tree node radius
	private double vGap = 80; //Gap between two levels in a tree
	
	
	
	RBTreeView(RBTree newTree){
		this.newTree = newTree;
		setStatus("Tree is empty");
	}
	
	
	
	// Sets the block of text with each node insert
	public void setStatus(String msg){
		Text text = new Text(20, 20, msg);
		text.setFill(Color.WHITE);
		text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		getChildren().add(text);
		
	}
	
	
	
	public void displayTree(){
		// Clear the pane
		this.getChildren().clear();
		
		if(newTree.getRoot() != null){
			// Display tree recursively
			displayTree(newTree.getRoot(), getWidth() / 2, vGap, getWidth() / 5);
		}
	}
	
	
	
	// Display a subtree rooted at position (x,y)
	// Is 'root' actually referring to root node or just current node? clarify (**root refers to current**)
	public void displayTree(Node<Integer, String> root, double x, double y, double hGap){
		
		
		if(root.left != null){
			Line leftLine = new Line(x - hGap, y + vGap, x, y);
			leftLine.setStroke(Color.WHITE);
			getChildren().add(leftLine); // Draw a line to the left node
			displayTree(root.left, x - hGap, y + vGap, hGap / 2); // Draw the left subtree recursively
		}
		
		if(root.right != null){
			Line rightLine = new Line(x + hGap, y + vGap, x, y);
			rightLine.setStroke(Color.WHITE);
			getChildren().add(rightLine); // Draw a line to the right node
			displayTree(root.right, x + hGap, y + vGap, hGap / 2); // Draw the right subtree recursively
		}
		
		
		//Display a node
		Circle circle = new Circle(x, y, radius);
		circle.setStroke(Color.WHITE);
		circle.setStrokeWidth(2);
		
		if(root.getColor() == 'B'){
			circle.setFill(Color.BLACK);
		}
		else if(root.getColor() == 'R'){
			circle.setFill(Color.RED);
		}
		
		
		//Displays text within a circle node, if-else is for centering text
		Text keyText = new Text();
		if(root.getKey() == null){
			keyText = new Text(x - 12, y + 4, root.getKey() + "");
			keyText.setFill(Color.WHITE);
		}
		else{
			keyText = new Text(x - 6.5, y + 4, root.getKey() + "");
			keyText.setFill(Color.WHITE);
		}
		

		getChildren().addAll(circle, keyText);
	}
	
	
	
	// Display traversal nodes
	public void showTraversal(String traversalType, String keysString){
		
		getChildren().clear();
		displayTree();
		Text results = new Text(20, 50, traversalType + keysString);
		results.setFill(Color.WHITE);
		results.setFont(Font.font("Veranda", FontWeight.BOLD, 24));
		getChildren().add(results);
	}
}
