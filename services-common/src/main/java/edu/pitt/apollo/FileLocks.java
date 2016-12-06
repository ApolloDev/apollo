package edu.pitt.apollo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileLocks {

	public enum FileLockAction {
		LOCK, UNLOCK,

	}

	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	Lock write = readWriteLock.writeLock();

	public FileLocks() {

	}

	public void lock() {
		write.lock();
	}

	public void unlock() {
		write.unlock();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {


	}

}
