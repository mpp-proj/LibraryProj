package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainWindow {
	@FXML private Tab tabCheckoutRecords;
	@FXML private Tab tabMembers;
	@FXML private Tab tabBooks;
	@FXML private Tab tabUsers;
	@FXML private TabPane tabPane;	

	public void toggleTabs(ActionEvent event) {
		Button btn = ((Button) event.getSource());
		Tab[] tabs = {tabCheckoutRecords, tabMembers, tabBooks, tabUsers};
		//System.out.println(btn.getId());
		for (Tab t : tabs) {
			//System.out.println(t.getId());
			t.setDisable(true);
			if (t.getId().endsWith(btn.getId().substring(3)))
			{
				t.setDisable(false);
				tabPane.getSelectionModel().select(t);
			}
		}
	}
}
