package org.example.adsproject;
import guru.nidi.graphviz.engine.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;
public class HelloApplication extends Application {
    private BTree bTree;
    private int branchingFactor;
    private ImageView graphView;
    @Override
    public void start(Stage stage) {
        branchingFactor = getBranchingFactor();
        if (branchingFactor < 2) {
            showAlert("Invalid Branching Factor", "Branching factor (t) must be at least 2.");
            System.exit(1);
        }
        bTree = new BTree(branchingFactor);
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        Label label = new Label("B-Tree Operations (t = " + branchingFactor + ")");
        TextField inputField = new TextField();
        inputField.setPromptText("Enter number");
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button searchButton = new Button("Search");
        Button minButton = new Button("Get Minimum");
        Button maxButton = new Button("Get Maximum");
        Button predButton = new Button("Get Predecessor");
        Button succButton = new Button("Get Successor");
        Button inorderButton = new Button("Inorder Traversal");
        graphView = new ImageView();
        graphView.setFitWidth(400);
        graphView.setPreserveRatio(true);
        HBox buttonBox = new HBox(10, addButton, deleteButton, searchButton, minButton, maxButton, predButton, succButton, inorderButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        addButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(inputField.getText());
                if (bTree.search(key)) {
                    showAlert("Duplicate Key", "Key " + key + " already exists in the B-Tree.");
                    inputField.clear();
                    return;
                }
                bTree.insert(key);
                updateGraph();
                inputField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid number.");
            }
        });
        deleteButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(inputField.getText());
                bTree.delete(key);
                updateGraph();
                inputField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid number.");
            }
        });
        searchButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(inputField.getText());
                boolean found = bTree.search(key);
                if (found) {
                    showAlert("Search Result", "Key " + key + " was found in the B-Tree.");
                } else {
                    showAlert("Search Result", "Key " + key + " was NOT found in the B-Tree.");
                }
                inputField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid number.");
            }
        });
        minButton.setOnAction(e -> {
            try {
                int min = bTree.getMin();
                showAlert("Minimum Key", "The smallest key in the B-Tree is: " + min);
            } catch (IllegalStateException ex) {
                showAlert("Error", ex.getMessage());
            }
        });
        maxButton.setOnAction(e -> {
            try {
                int max = bTree.getMax();
                showAlert("Maximum Key", "The largest key in the B-Tree is: " + max);
            } catch (IllegalStateException ex) {
                showAlert("Error", ex.getMessage());
            }
        });
        predButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(inputField.getText());
                if (!bTree.search(key)) {
                    showAlert("Predecessor", "Key " + key + " does not exist in the B-Tree.");
                    return;
                }
                Integer pred = bTree.getPredecessor(key);
                if (pred == null) {
                    showAlert("Predecessor", "No predecessor exists for key " + key + ".");
                } else {
                    showAlert("Predecessor", "The predecessor of " + key + " is: " + pred);
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid number.");
            }
        });
        succButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(inputField.getText());
                if (!bTree.search(key)) {
                    showAlert("Successor", "Key " + key + " does not exist in the B-Tree.");
                    return;
                }
                Integer succ = bTree.getSuccessor(key);
                if (succ == null) {
                    showAlert("Successor", "No successor exists for key " + key + ".");
                } else {
                    showAlert("Successor", "The successor of " + key + " is: " + succ);
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid number.");
            }
        });
        inorderButton.setOnAction(e -> {
            String traversal = bTree.inorderTraversal();
            showAlert("Inorder Traversal", "Inorder Traversal of the B-Tree:\n" + traversal);
        });
        root.getChildren().addAll(label, inputField, buttonBox, graphView);
        Scene scene = new Scene(root, 800, 400);
        stage.setTitle("B-Tree Visualizer");
        stage.setScene(scene);
        stage.show();
        updateGraph();
    }
    private int getBranchingFactor() {
        TextInputDialog dialog = new TextInputDialog("3");
        dialog.setTitle("Branching Factor");
        dialog.setHeaderText("Set the Branching Factor (t)");
        dialog.setContentText("Enter the minimum branching factor (t ≥ 2):");
        return dialog.showAndWait().map(input -> {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid integer.");
                return -1;
            }
        }).orElse(-1);
    }
    private void updateGraph() {
        String dot = bTree.toDOT();
        try {
            File outputFile = File.createTempFile("graph", ".png");
            outputFile.deleteOnExit();
            Graphviz.fromString(dot)
                    .render(Format.PNG)
                    .toFile(outputFile);
            Image image = new Image(outputFile.toURI().toString());
            graphView.setImage(image);
        } catch (Exception e) {
            showAlert("Graph Rendering Error", "Unable to render the graph. Please check the DOT representation.");
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void main(String[] args) {
        launch();
    }
}