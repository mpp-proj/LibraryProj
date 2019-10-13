package dataaccess;

import java.util.HashMap;

import business.Book;
import business.CheckOutEntry;
import business.Author;
import business.LibraryMember;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public HashMap<String, CheckOutEntry> readCheckoutRecordMap();
	public void saveNewMember(LibraryMember member);
	public void saveNewBook (Book book);
	public void updateBook(Book book);
	public void saveBook(Book book);
	public HashMap<String, Author> readAuthorMap();
	public void saveNewAuthor(Author author);
	public void saveNewCheckoutRecord(CheckOutEntry entry);
	
}
