package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckOutEntry implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Book book;
	private BookCopy copy;
	private LibraryMember member;
	private LocalDate dueDate;
	private boolean hasReturned;
	private LocalDate outDate;

	public CheckOutEntry(Book b, BookCopy bc, LibraryMember member, LocalDate outDate) {
		this.book = b;
		this.copy = bc;
		this.member = member;
		this.outDate = outDate;
		this.dueDate = LocalDate.now().plusDays(b.getMaxCheckoutLength());
		this.hasReturned = false;
	}

	public Book getBook() {
		return book;
	}

	public BookCopy getCopy() {
		return copy;
	}

	public LibraryMember getMember() {
		return member;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setHasReturned(boolean val) {
		this.hasReturned = val;
	}

	public boolean getHasReturned() {
		return this.hasReturned;
	}
	public LocalDate getOutDate()
	{
		return this.outDate;
	}
}
