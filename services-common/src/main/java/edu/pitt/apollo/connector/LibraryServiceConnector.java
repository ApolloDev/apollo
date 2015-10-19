package edu.pitt.apollo.connector;

import edu.pitt.apollo.interfaces.LibraryServiceInterface;

/**
 * Created by jdl50 on 8/4/15.
 */
public abstract class LibraryServiceConnector extends ServiceConnector implements LibraryServiceInterface {

    public LibraryServiceConnector(String url) {
        super(url);
    }
}
