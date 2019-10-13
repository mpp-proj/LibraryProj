package application;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import business.Author;
import business.AuthorRecord;
import business.BaseController;
import business.Book;
import business.BookCopy;
import business.ControllerInterface;
import business.LoginException;
import business.CheckOutEntry;
import business.LibraryMember;
import business.MemberRecords;
import business.SystemController;
import business.BookRecords;

public class MainWindow {

	private DataAccessFacade daf;
	private HashMap<String, Book> bookMap;
	Tab[] tabs = new Tab[4];

	@FXML
	private Tab tabCheckoutRecords;
	@FXML
	private Tab tabMembers;
	@FXML
	private Tab tabBooks;
	@FXML
	private Tab tabUsers;
	@FXML
	private TabPane tabPane;

	@FXML
	private Button btnBooks;
	@FXML
	private Button btnCheckoutRecords;
	@FXML
	private Button btnMembers;
	@FXML
	private Button btnUsers;

	@FXML
	private TableView<BookData> tvBook;
	@FXML
	private TableView<BookData> tvBookCopy;

	@FXML
	private TableView<BookRecords> tblCheckOutRecords;
	@FXML
	private TableColumn<BookRecords, String> clmISBN;
	@FXML
	private TableColumn<BookRecords, String> clmCopyNum;
	@FXML
	private TableColumn<BookRecords, String> clmTitle;
	@FXML
	private TableColumn<BookRecords, String> clmMemberId;
	@FXML
	private TableColumn<BookRecords, String> clmMemberName;
	@FXML
	private TableColumn<BookRecords, String> clmCheckoutDate;
	@FXML
	private TableColumn<BookRecords, String> clmDueDate;
	@FXML
	private TableColumn<BookRecords, String> clmHasReturned;
	@FXML
	private TableColumn<BookRecords, String> clmReturnedDate;
	@FXML
	private TableColumn<BookRecords, String> clmFineAmount;

	/***********************************************************************/
	// member table

	@FXML
	private Button btnAddCopy;
	@FXML
	private TableColumn<BookData, String> colBTitle;
	@FXML
	private TableColumn<BookData, String> colBAuth;
	@FXML
	private TableColumn<BookData, String> colBIsdn;
	@FXML
	private TableColumn<BookData, String> colBAvNm;
	@FXML
	private TableColumn<BookData, String> colBMaxD;

	@FXML
	private TableColumn<BookData, String> colBCId;
	@FXML
	private TableColumn<BookData, String> colBCIA;

	private ObservableList<BookData> data;
	@FXML
	private TableView<MemberRecords> tblMembers;
	@FXML
	private TableColumn<MemberRecords, String> clmMemberID;
	@FXML
	private TableColumn<MemberRecords, String> clmFirstName;
	@FXML
	private TableColumn<MemberRecords, String> clmLastName;
	@FXML
	private TableColumn<MemberRecords, String> clmPhone;
	@FXML
	private TableColumn<MemberRecords, String> clmStreet;
	@FXML
	private TableColumn<MemberRecords, String> clmCity;
	@FXML
	private TableColumn<MemberRecords, String> clmState;
	@FXML
	private TableColumn<MemberRecords, String> clmZip;
	
	/***********************************************************************************************/
	// Author
	@FXML
	private TableView<AuthorRecord> tblAuthor;
	@FXML
	private TableColumn<AuthorRecord, String> clmAuthFName;
	@FXML
	private TableColumn<AuthorRecord, String> clmAuthLName;
	@FXML
	private TableColumn<AuthorRecord, String> clmAuthTel;
	@FXML
	private TableColumn<AuthorRecord, String> clmAuthBio;
	@FXML
	private TableColumn<AuthorRecord, String> clmAuthStreet;
	@FXML
	private TableColumn<AuthorRecord, String> clmAuthCity;
	@FXML
	private TableColumn<AuthorRecord, String> clmAuthState;
	@FXML
	private TableColumn<AuthorRecord, String> clmAuthZip;
	
	@FXML
	private Button btnNew;
	@FXML
	private Button btnReload;
	@FXML
	private Button btnEdit;
//	@FXML
//	private Button btnDelete;
	@FXML
	private Button btnReturn;
	@FXML
	private TextField txtSearchCheckOutRecords;
	@FXML
	private Button btnSearchCheckOut;
	@FXML 
	private Button btnPrintRecords;
	
	@FXML
	private TextField txtAuthBio;
	@FXML
	private TextField txtAuthFName;
	@FXML
	private TextField txtAuthLName;
	@FXML
	private TextField txtAuthPhone;

	ObservableList<BookRecords> recordData = FXCollections.observableArrayList();
	ObservableList<BookRecords> searchData = FXCollections.observableArrayList();

	public void initialize() {
		clmISBN.setCellValueFactory(new PropertyValueFactory<BookRecords, String>("isbn"));
		clmCopyNum.setCellValueFactory(new PropertyValueFactory<BookRecords, String>("copyNum"));
		clmTitle.setCellValueFactory(new PropertyValueFactory<BookRecords, String>("title"));
		clmMemberId.setCellValueFactory(new PropertyValueFactory<BookRecords, String>("memberId"));
		clmMemberName.setCellValueFactory(new PropertyValueFactory<BookRecords, String>("name"));
		clmCheckoutDate.setCellValueFactory(new PropertyValueFactory<BookRecords, String>("outDate"));
		clmDueDate.setCellValueFactory(new PropertyValueFactory<BookRecords, String>("dueDate"));
		clmHasReturned.setCellValueFactory(new PropertyValueFactory<BookRecords, String>("hasReturned"));
		clmReturnedDate.setCellValueFactory(new PropertyValueFactory<BookRecords, String>("returnedDate"));
		clmFineAmount.setCellValueFactory(new PropertyValueFactory<BookRecords, String>("fineAmount"));

		clmMemberID.setCellValueFactory(new PropertyValueFactory<MemberRecords, String>("memberId"));
		clmFirstName.setCellValueFactory(new PropertyValueFactory<MemberRecords, String>("fname"));
		clmLastName.setCellValueFactory(new PropertyValueFactory<MemberRecords, String>("lname"));
		clmPhone.setCellValueFactory(new PropertyValueFactory<MemberRecords, String>("tel"));
		clmStreet.setCellValueFactory(new PropertyValueFactory<MemberRecords, String>("street"));
		clmCity.setCellValueFactory(new PropertyValueFactory<MemberRecords, String>("city"));
		clmState.setCellValueFactory(new PropertyValueFactory<MemberRecords, String>("state"));
		clmZip.setCellValueFactory(new PropertyValueFactory<MemberRecords, String>("zip"));
		
		clmAuthBio.setCellValueFactory(new PropertyValueFactory<AuthorRecord, String>("bio"));
		clmAuthFName.setCellValueFactory(new PropertyValueFactory<AuthorRecord, String>("fname"));
		clmAuthLName.setCellValueFactory(new PropertyValueFactory<AuthorRecord, String>("lname"));
		clmAuthTel.setCellValueFactory(new PropertyValueFactory<AuthorRecord, String>("tel"));
		clmAuthStreet.setCellValueFactory(new PropertyValueFactory<AuthorRecord, String>("street"));
		clmAuthCity.setCellValueFactory(new PropertyValueFactory<AuthorRecord, String>("city"));
		clmAuthState.setCellValueFactory(new PropertyValueFactory<AuthorRecord, String>("state"));
		clmAuthZip.setCellValueFactory(new PropertyValueFactory<AuthorRecord, String>("zip"));
		
		

		tabs[0] = tabCheckoutRecords;
		tabs[1] = tabMembers;
		tabs[2] = tabBooks;
		tabs[3] = tabUsers;
		colBTitle.setCellValueFactory(new PropertyValueFactory<BookData, String>("title"));
		colBAuth.setCellValueFactory(new PropertyValueFactory<BookData, String>("authors"));
		colBIsdn.setCellValueFactory(new PropertyValueFactory<BookData, String>("isbn"));
		colBAvNm.setCellValueFactory(new PropertyValueFactory<BookData, String>("number"));
		colBMaxD.setCellValueFactory(new PropertyValueFactory<BookData, String>("maxDay"));

		colBCId.setCellValueFactory(new PropertyValueFactory<BookData, String>("copyNum"));
		colBCIA.setCellValueFactory(new PropertyValueFactory<BookData, String>("isAvailable"));
		reloadBook();
		TableViewLoad();
		toggleAuth();
	}
	
	public void reloadBook() {
		tvBook.setItems(listBooks());
	}
	private void toggleAuth() {
		switch (SystemController.currentAuth) {
		case LIBRARIAN:
			btnMembers.setDisable(true);
			btnBooks.setDisable(true);
			btnUsers.setDisable(true);
			btnCheckoutRecords.setDisable(false);
			btnEdit.setDisable(true);
			break;
		case ADMIN:
			btnMembers.setDisable(false);
			btnBooks.setDisable(false);
			btnUsers.setDisable(false);
			btnCheckoutRecords.setDisable(true);
			btnEdit.setVisible(true);
			btnReturn.setVisible(false);
			btnReturn.setDisable(true);
			break;
		case BOTH:
			btnMembers.setDisable(false);
			btnBooks.setDisable(false);
			btnUsers.setDisable(false);
			btnCheckoutRecords.setDisable(false);
			btnReturn.setDisable(false);
			break;
		}
	}

	public void toggleTabs(ActionEvent event) {
		Button btn = ((Button) event.getSource());
		// System.out.println(btn.getId());
		for (Tab t : tabs) {
			// System.out.println(t.getId());
			t.setDisable(true);
			if (t.getId().endsWith(btn.getId().substring(3))) {
				t.setDisable(false);
				tabPane.getSelectionModel().select(t);
				if (btn.getId().equals("btnBooks"))
					reloadBook();
			}
		}
	}

	public ObservableList<BookData> listBooks() {
		daf = new DataAccessFacade();
		data = FXCollections.observableArrayList();
		bookMap = new HashMap<String, Book>();
		bookMap = daf.readBooksMap();
		List<BookData> bdList = new ArrayList<BookData>();
		bookMap.forEach((s, b) -> bdList.add(new BookData(b)));
		bdList.forEach((e) -> data.add(e));
		return data;
	}
	public void addBook(ActionEvent event) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/AddBook.fxml"));
			Scene scene = new Scene(root, 400, 450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stg = new Stage();
			stg.setTitle("Add Book");
			stg.setMaximized(false);
			stg.setScene(scene);
			stg.show();
		} catch (Exception ex) {

		}
	}

	public void editBook(ActionEvent event) throws Exception {
		try {

			BookData bd = tvBook.getSelectionModel().getSelectedItem();
			String selectedBookIsbn = bd.getIsbn();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/AddBook.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 400, 450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stg = new Stage();
			stg.setTitle("Edit Book");
			stg.setResizable(false);
			stg.setScene(scene);

			AddBook controller = loader.<AddBook>getController();
			controller.initData(selectedBookIsbn);
			stg.show();

		} catch (Exception ex) {

		}
	}

	public void getCopies(MouseEvent evt) throws Exception {

		ObservableList<BookData> copyList = FXCollections.observableArrayList();
		BookData tmpBd;
		BookData bd = tvBook.getSelectionModel().getSelectedItem();

		Book book = daf.readBooksMap().get(bd.getIsbn());

		BookCopy[] copies = book.getCopies();

		for (BookCopy bc : copies) {
			tmpBd = new BookData();
			tmpBd.setCopyNum(String.valueOf(bc.getCopyNum()));
			tmpBd.setIsAvailable(String.valueOf(bc.isAvailable()));
			copyList.add(tmpBd);
		}
		tvBookCopy.setItems(copyList);

	}

	public void addCopy(ActionEvent evt) {
		daf = new DataAccessFacade();
		BookData bd = tvBook.getSelectionModel().getSelectedItem();
		Book book = daf.readBooksMap().get(bd.getIsbn());
		book.addCopy();
		daf.updateBook(book);
	}
	
	public void TableViewLoad() {
		tblCheckOutRecords.getItems().clear();
		tblCheckOutRecords.setItems(getTicketData());

		tblMembers.getItems().clear();
		tblMembers.setItems(getMemberData());
		
		tblAuthor.getItems().clear();
		tblAuthor.setItems(getAuthorData());
	}
	

	public ObservableList<BookRecords> getTicketData() {
		BookRecords br = new BookRecords();
		DataAccess da = new DataAccessFacade();
		HashMap<String, CheckOutEntry> records = da.readCheckoutRecordMap();
		for (CheckOutEntry co : records.values()) {
			br.setCopyNum(String.valueOf(co.getCopy().getCopyNum()));
			br.setDueDate(String.valueOf(co.getDueDate()));
			br.setHasReturned(String.valueOf(co.getHasReturned()));
			br.setIsbn(co.getBook().getIsbn());
			br.setMemberId(co.getMember().getMemberId());
			br.setName(co.getMember().getFirstName() + " " + co.getMember().getLastName());
			br.setOutDate(String.valueOf(co.getOutDate()));
			br.setTitle(co.getBook().getTitle());
			br.setReturnedDate(String.valueOf(co.getReturnedDate()));
			br.setFineAmount(String.valueOf(co.getFineAmount()));
			recordData.add(br);
			br = new BookRecords();
		}
		return recordData;
	}

	public ObservableList<MemberRecords> getMemberData() {
		ObservableList<MemberRecords> memberData = FXCollections.observableArrayList();
		MemberRecords mr = new MemberRecords();
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> records = da.readMemberMap();
		for (LibraryMember co : records.values()) {
			mr.setMemberId(String.valueOf(co.getMemberId()));
			mr.setFname(co.getFirstName());
			mr.setLname(co.getLastName());
			mr.setTel(co.getTelephone());
			mr.setStreet(co.getAddress().getState());
			mr.setCity(co.getAddress().getCity());
			mr.setState(co.getAddress().getState());
			mr.setZip(co.getAddress().getZip());
			memberData.add(mr);
			mr = new MemberRecords();
		}
		return memberData;
	}
	
	public ObservableList<AuthorRecord> getAuthorData() {
		ObservableList<AuthorRecord> authorData = FXCollections.observableArrayList();
		AuthorRecord mr = new AuthorRecord();
		DataAccess da = new DataAccessFacade();
		HashMap<String, Author> records = da.readAuthorMap();
		for (Author co : records.values()) {
			mr.setBio(String.valueOf(co.getBio()));
			mr.setFname(co.getFirstName());
			mr.setLname(co.getLastName());
			mr.setTel(co.getTelephone());
			mr.setStreet(co.getAddress().getState());
			mr.setCity(co.getAddress().getCity());
			mr.setState(co.getAddress().getState());
			mr.setZip(co.getAddress().getZip());
			authorData.add(mr);
			mr = new AuthorRecord();
		}
		return authorData;
	}

	public void openEditWindow(ActionEvent evt) {
		for (Tab t : tabs) {
			if (t.isSelected()) {
				try {
					Button b = (Button) evt.getSource();
					String flag = b.getId().equals("btnNew") ? "new" : "edit";
					String tabName = t.getId();
					openWindow(tabName, flag);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	}

	private void openWindow(String windowName, String flag) throws IOException {
		Parent root;
		Scene scene;
		int width = 400, height = 400;
		String source = "";
		String recordId = null;
		BaseController controller = new CheckOutRecordController();
		switch (windowName) {
		case "tabCheckoutRecords":
			source = "/application/CheckOutRecord.fxml";
			width = 410;
			height = 300;
			break;
		case "tabMembers":
			source = "/ui/Members.fxml";
			width = 400;
			height = 500;
			break;
		case "tabBooks":
			source = "/application/AddBook.fxml";
			height = 450;
			width = 400;
			break;
		default:
			source = "/ui/Authors.fxml";
			width = 400;
			height = 500;
			break;
		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource(source));

		if (flag.equals("edit")) {
			if (windowName.equalsIgnoreCase("tabCheckoutRecords")) {
				BookRecords br = tblCheckOutRecords.getSelectionModel().getSelectedItem();
				if (br.getHasReturned().equals("true")) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Info");
					alert.setHeaderText("Returned");
					alert.setContentText("The book has already returned.");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
					return;
				}
				recordId = br.getIsbn() + br.getCopyNum() + br.getMemberId();
			} else if (windowName.equalsIgnoreCase("tabMembers")) {
				MemberRecords mr = tblMembers.getSelectionModel().getSelectedItem();
				recordId = mr.getMemberId();
			} else if (windowName.equalsIgnoreCase("tabBooks")) {
				BookData bd = tvBook.getSelectionModel().getSelectedItem();
				recordId =  bd.getIsbn();
			} else {
				AuthorRecord bd = tblAuthor.getSelectionModel().getSelectedItem();
				recordId =  bd.getFname() + bd.getLname();
			}
		}
		root = loader.load();
		scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage stg = new Stage(StageStyle.DECORATED);
		stg.setTitle("Library System 1.0");
		stg.setResizable(false);
		stg.setScene(scene);
		switch (windowName) {
		case "tabCheckoutRecords":
			controller = loader.<CheckOutRecordController>getController();
			break;
		case "tabMembers":
			controller = loader.<MemberController>getController();
			break;
		case "tabBooks":
			controller = loader.<AddBook>getController();
			break;
		case "tabUsers":
			controller = loader.<AuthorController>getController();
			break;
		}
		controller.initData(recordId);
		stg.show();
	}

	public void findMemberInCheckoutRecords(ActionEvent evt) {
		String memberID = txtSearchCheckOutRecords.getText();
		if(memberID == null || memberID.equals(""))
		{
			TableViewLoad();
			return;
		}
		searchData.clear();
		BookRecords br = new BookRecords();
		DataAccess da = new DataAccessFacade();
		HashMap<String, CheckOutEntry> records = da.readCheckoutRecordMap();
		for (CheckOutEntry co : records.values()) {
			if (co.getMember().getMemberId().equals(memberID)) {
				br.setCopyNum(String.valueOf(co.getCopy().getCopyNum()));
				br.setDueDate(String.valueOf(co.getDueDate()));
				br.setHasReturned(String.valueOf(co.getHasReturned()));
				br.setIsbn(co.getBook().getIsbn());
				br.setMemberId(co.getMember().getMemberId());
				br.setName(co.getMember().getFirstName() + " " + co.getMember().getLastName());
				br.setOutDate(String.valueOf(co.getOutDate()));
				br.setTitle(co.getBook().getTitle());
				br.setReturnedDate(String.valueOf(co.getReturnedDate()));
				br.setFineAmount(String.valueOf(co.getFineAmount()));
				searchData.add(br);
				br = new BookRecords();
			}
		}
		tblCheckOutRecords.getItems().clear();
		tblCheckOutRecords.setItems(searchData);
	}
	
	public void printRecords(ActionEvent av)
	{
		for(BookRecords br: searchData)
			System.out.println(br);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setHeaderText("Successfully printe");
		alert.setContentText("Printing checkout record has been done. Please check your console.");
		alert.showAndWait().ifPresent(rs -> {
			if (rs == ButtonType.OK) {
				System.out.println("Pressed Ok.");
			}
		});
	}
}
