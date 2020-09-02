package gui;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;

	@FXML
	private MenuItem menuItemDepartment;

	@FXML
	private MenuItem menuItemAbout;

	@FXML
	public void menuItemSellerOnAction() {
		System.out.println("menuItemSellerOnAction");
	}

	@FXML
	public void menuItemDepartmentOnAction() {
		System.out.println("menuItemDepartmentOnAction");
	}

	@FXML
	public void menuItemAboutOnAction() {
		loadView("/gui/About.fxml");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}
	
	public void loadView(String path) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		try {
			VBox vBox = loader.load();
			Scene mainScene = Main.getMainScene();
			VBox mainVbBox = (VBox)((ScrollPane) mainScene.getRoot()).getContent();
			Node mainMenu = mainVbBox.getChildren().get(0);
			mainVbBox.getChildren().clear();
			mainVbBox.getChildren().add(mainMenu);
			mainVbBox.getChildren().addAll(vBox);
		} catch (IOException e) {
			Alerts.showAlert("IOException", null, e.getMessage(), AlertType.ERROR);
		}
	}

}
