package net.jfabricationgames.genesis_project.game_frame.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public abstract class DialogUtils {
	
	public static void showErrorDialog(String title, String hint, String error, boolean showAndWait) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(hint);
		alert.setContentText(error);
		
		if (showAndWait) {
			alert.showAndWait();
		}
		else {
			alert.show();
		}
	}
	
	public static void showErrorDialog(String title, String hint, String error) {
		showErrorDialog(title, hint, error, true);
	}
	
	public static void showExceptionDialog(String title, String hint, Exception ex, boolean showAndWait) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(hint);
		alert.setContentText(ex.getMessage());
		
		Pane detailsPane = createStackTracePane(ex);
		alert.getDialogPane().setExpandableContent(detailsPane);
		
		/*alert.getDialogPane().expandedProperty().addListener((observer, oldVal, newVal) -> {
			alert.getDialogPane().getScene().getWindow().sizeToScene();
		});*/
		
		if (showAndWait) {
			alert.showAndWait();
		}
		else {
			alert.show();
		}
	}
	
	public static Optional<ButtonType> showConfirmationDialog(String title, String hint, String text) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(hint);
		alert.setContentText(text);
		
		Optional<ButtonType> result = alert.showAndWait();
		return result;
		
		/*if (result.isPresent() && result.get() == ButtonType.OK){
		    // ... user chose OK
		} else {
		    // ... user chose CANCEL or closed the dialog
		}*/
	}
	
	private static Pane createStackTracePane(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		
		Label details = new Label("Stacktrace: ");
		TextArea textArea = new TextArea(sw.toString());
		textArea.setEditable(false);
		textArea.setWrapText(true);
		
		FlowPane contentPane = new FlowPane();
		contentPane.getChildren().addAll(details, textArea);
		
		return contentPane;
	}
	
	public static void showInfoDialog(String title, String hint, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(hint);
		alert.setContentText(text);
		
		alert.show();
	}
}