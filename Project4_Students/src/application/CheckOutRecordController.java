package application;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import business.Book;
import business.CheckOutEntry;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CheckOutRecordController {
	@FXML private ComboBox<Book> checkOutBook;
	@FXML private ComboBox<LibraryMember> checkOutMember;
	@FXML private TextField checkOutAvailable;
	@FXML private TextField checkOutDuration;
	@FXML private Button checkOutSave;
	@FXML private Button checkOutCancel;
	
	@FXML
	public void initialize() {
		DataAccess da = new DataAccessFacade();
		HashMap<String,Book> books = da.readBooksMap();
		for(Map.Entry<String, Book> e : books.entrySet()){                  
			checkOutBook.getItems().add(e.getValue());
		}
		
		HashMap<String,LibraryMember> members = da.readMemberMap();
		for(Map.Entry<String, LibraryMember> e : members.entrySet()){                  
			checkOutMember.getItems().add(e.getValue());
		}
		
		checkOutBook.valueProperty().addListener(new ChangeListener<Book>() {
			@Override
			public void changed(ObservableValue<? extends Book> observable, Book oldValue, Book newValue) {
				checkOutAvailable.setText(String.valueOf(newValue.isAvailable()));
				checkOutDuration.setText(String.valueOf(newValue.getMaxCheckoutLength()));
			}   
		});
    }
	
	public void SaveCheckOut(ActionEvent evt)
	{
		Book b = (Book)checkOutBook.getSelectionModel().getSelectedItem();
		LibraryMember member = (LibraryMember)checkOutMember.getSelectionModel().getSelectedItem();
		CheckOutEntry entry = new CheckOutEntry(b, b.getNextAvailableCopy(), member, LocalDate.now());
		DataAccess da = new DataAccessFacade();
		da.saveNewCheckoutRecord(entry);
		
		for(CheckOutEntry ent: da.readCheckoutRecordMap().values())
		{
			System.out.println("isbn: " +ent.getBook().getIsbn() +" title: "+ent.getBook().getTitle() + " Member name: " + ent.getMember().getFirstName());
		}
	}
}
