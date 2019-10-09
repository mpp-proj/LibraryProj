package dataaccess;

import java.util.HashMap;

import business.Book;
import business.CheckOutEntry;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public HashMap<String, CheckOutEntry> readCheckoutRecordMap();
	public void saveNewMember(LibraryMember member); 
	public void saveNewCheckoutRecord(CheckOutEntry entry);
	
}
