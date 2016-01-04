package edu.pitt.apollo.dataservice.accessors;

import edu.pitt.apollo.dataservice.types.FileContentForRun;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import java.io.Closeable;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author nem41
 */
public class DatabaseAccessorForIterableFileContent extends DatabaseAccessor implements Iterator<FileContentForRun>, AutoCloseable {

	private static final String RUN_ID_COLUMN_NAME = "id";
	private static final String FILE_CONTENT_COLUMN_NAME = "text_content";
	private static final String FILE_NAME_ID = "name";
	private Connection conn;
	private ResultSet resultSet;

	public DatabaseAccessorForIterableFileContent(Authentication authentication) throws ApolloDatabaseException {
		super(authentication);
	}

	public void retrieveAllFilesForRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, List<String> optionalFileNamesToMatch) throws ApolloDatabaseException {
		// reset the connection so that no connection is left open longer than it needs to be
		try {
			close();
			conn = dbUtils.getConnection();
		} catch (IOException | SQLException ex) {
			throw new ApolloDatabaseException(ex.getMessage());
		}

		PreparedStatement pstmt = dbUtils.getDataContentForBatchSimulations(runId, optionalFileNamesToMatch, conn);
		try {
			resultSet = pstmt.executeQuery();
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(ex.getMessage());
		}
	}

	@Override
	public boolean hasNext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public FileContentForRun next() {

		FileContentForRun fileContent = new FileContentForRun();
		try {
			if (resultSet.next()) {

				fileContent.setRunId(resultSet.getInt(RUN_ID_COLUMN_NAME));
				fileContent.setFileName(resultSet.getString(FILE_NAME_ID));
				fileContent.setFileContent(resultSet.getString(FILE_CONTENT_COLUMN_NAME));

				return fileContent;
			} else {
				return null;
			}
		} catch (SQLException ex) {
			throw new RuntimeException("SQLException getting data from result set row: " + ex.getMessage());
		}
	}

	@Override
	public void close() throws IOException {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException ex) {
			throw new IOException("SQLException closing connection or result set: " + ex.getMessage());
		}
	}
}
