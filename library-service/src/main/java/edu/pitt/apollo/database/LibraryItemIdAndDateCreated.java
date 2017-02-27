package edu.pitt.apollo.database;

import java.sql.Timestamp;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 3, 2014
 * Time: 3:08:59 PM
 * Class: LibraryItemIdAndDateCreated
 */
public class LibraryItemIdAndDateCreated {

	private int id;
	private Timestamp dateCreated;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the dateCreated
	 */
	public Timestamp getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	
}
