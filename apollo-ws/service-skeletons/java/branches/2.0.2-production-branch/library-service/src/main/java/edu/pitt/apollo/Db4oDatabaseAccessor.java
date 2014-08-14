package edu.pitt.apollo;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.ConfigScope;
import com.db4o.config.EmbeddedConfiguration;
import edu.pitt.apollo.libraryservice.types.ApolloIndexableItemMap;
import edu.pitt.apollo.libraryservice.types.CatalogEntryForApolloLibraryItemMap;
import edu.pitt.apollo.types.v2_0_2.ApolloIndexableItem;
import edu.pitt.apollo.types.v2_0_2.ApolloIndexableItemTypeEnum;
import edu.pitt.apollo.types.v2_0_2.CatalogEntryForApolloLibraryItem;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Aug 13, 2014
 Time: 2:34:55 PM
 Class: Db4oDatabaseAccessor
 */
public class Db4oDatabaseAccessor {
	
	private final ObjectContainer db4o;
	private static final int DB4O_ACTIVATION_DEPTH = 100;
	
	private ApolloIndexableItemMap apolloIndexableItemMap;
	private CatalogEntryForApolloLibraryItemMap catalogEntryMap;
	
	public Db4oDatabaseAccessor(String db4oDatabasePath) {
		EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
		configuration.file().generateUUIDs(ConfigScope.GLOBALLY);
		db4o = Db4oEmbedded.openFile(configuration, db4oDatabasePath);
		
		createOrLoadApolloIndexableItemMap();
		createOrLoadCatalogEntryMap();
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		closeDb40();
	}
	
	private static ByteArrayOutputStream getJsonBytes(Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
			mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			mapper.writeValue(baos, obj);
			
			return baos;
		} catch (IOException ex) {
			System.err.println("IO Exception JSON encoding and getting bytes from RunSimulationMessage");
			return null;
		}
	}
	
	private static String getObjectMD5Hash(Object object) {
		return DigestUtils.md5Hex(getJsonBytes(object).toString());
	}
	
	private boolean checkIfDatabaseContainsInstancesOfClass(Class clazz) {
		
		ObjectSet result = db4o.query(clazz);
		if (result.size() == 0) {
			return false;
		} else if (result.size() == 1) {
			return true;
		} else {
			throw new ExceptionInInitializerError("More than one ApolloIndexableItemMap exists in the database");
		}
	}
	
	private void createApolloIndexableItemMapAndStoreToDatabase() {
		
		ApolloIndexableItemMap map = new ApolloIndexableItemMap();
		db4o.store(map);
		db4o.commit();
	}
	
	private void createCatalogEntryMapAndStoreToDatabase() {
		
		CatalogEntryForApolloLibraryItemMap map = new CatalogEntryForApolloLibraryItemMap();
		db4o.store(map);
		db4o.commit();
	}
	
	private void createOrLoadApolloIndexableItemMap() {
		
		boolean databaseContainsApolloIndexableItemMap = checkIfDatabaseContainsInstancesOfClass(ApolloIndexableItem.class);
		if (!databaseContainsApolloIndexableItemMap) {
			createApolloIndexableItemMapAndStoreToDatabase();
		}
		
		ObjectSet result = db4o.query(ApolloIndexableItemMap.class);
		apolloIndexableItemMap = (ApolloIndexableItemMap) result.next();
		db4o.activate(apolloIndexableItemMap, DB4O_ACTIVATION_DEPTH);
	}
	
	private void createOrLoadCatalogEntryMap() {
		
		boolean databaseContainsCatalogEntryMap = checkIfDatabaseContainsInstancesOfClass(CatalogEntryForApolloLibraryItemMap.class);
		if (!databaseContainsCatalogEntryMap) {
			createCatalogEntryMapAndStoreToDatabase();
		}
		
		ObjectSet result = db4o.query(CatalogEntryForApolloLibraryItemMap.class);
		catalogEntryMap = (CatalogEntryForApolloLibraryItemMap) result.next();
		db4o.activate(catalogEntryMap, DB4O_ACTIVATION_DEPTH);
	}
	
	private boolean checkApolloIndexableItemMap(String itemHash) {
		return apolloIndexableItemMap.containsKey(itemHash);
	}
	
	private boolean checkCatalogEntryMap(String entryHash) {
		return apolloIndexableItemMap.containsKey(entryHash);
	}
	
	private void addApolloIndexableItemToMap(String itemHash, ApolloIndexableItem item) {
		apolloIndexableItemMap.put(itemHash, item);
		db4o.store(apolloIndexableItemMap);
		db4o.commit();
	}
	
	private void addCatalogEntryForApolloLibraryItemToMap(String entryHash, CatalogEntryForApolloLibraryItem entry) {
		catalogEntryMap.put(entryHash, entry);
		db4o.store(catalogEntryMap);
		db4o.commit();
	}
	
	private void removeApolloIndexableItemFromMap(String itemHash) {
		apolloIndexableItemMap.remove(itemHash);
		db4o.store(apolloIndexableItemMap);
		db4o.commit();
	}
	
	private void removeCatalogEntryForApolloLibraryItemFromMap(String entryHash) {
		catalogEntryMap.remove(entryHash);
		db4o.store(catalogEntryMap);
		db4o.commit();
	}
	
	public synchronized ApolloIndexableItem getApolloIndexableItemFromMap(String itemHash) {
		return apolloIndexableItemMap.get(itemHash);
	}
	
	public synchronized CatalogEntryForApolloLibraryItem getCatalogEntryForApolloLibraryItemFromMap(String entryHash) {
		return catalogEntryMap.get(entryHash);
	}
	
	public synchronized void removeApolloLibraryItem(String entryHash) {
		CatalogEntryForApolloLibraryItem catalogEntry = getCatalogEntryForApolloLibraryItemFromMap(entryHash);
		if (catalogEntry == null) {
			return;
		}
		
		String apolloIndexableItemHash = catalogEntry.getItemUuid();
		ApolloIndexableItem apolloIndexableItem = getApolloIndexableItemFromMap(apolloIndexableItemHash);
		if (apolloIndexableItem != null) {
			removeApolloIndexableItemFromMap(apolloIndexableItemHash);
		}
		
		removeCatalogEntryForApolloLibraryItemFromMap(entryHash);
	}
	
	public synchronized String addApolloLibraryItem(ApolloIndexableItem apolloIndexableItem,
			String itemDescription,
			String itemSource,
			String itemType,
			List<String> itemIndexingLabels) throws DatatypeConfigurationException {
		
		String apolloIndexableItemHash = getObjectMD5Hash(apolloIndexableItem);
		boolean apolloIndexableItemExistsInMap = checkApolloIndexableItemMap(apolloIndexableItemHash);
		if (!apolloIndexableItemExistsInMap) {
			// need to store the ApolloIndexableItem
			addApolloIndexableItemToMap(apolloIndexableItemHash, apolloIndexableItem);
		}
		GregorianCalendar c = new GregorianCalendar();
		XMLGregorianCalendar date;
		date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		
		CatalogEntryForApolloLibraryItem catalogEntry = new CatalogEntryForApolloLibraryItem();
		catalogEntry.setItemDescription(itemDescription);
		catalogEntry.setItemSource(itemSource);
		catalogEntry.setItemType(ApolloIndexableItemTypeEnum.fromValue(itemType + "Type"));
		catalogEntry.setItemUuid(apolloIndexableItemHash);
		catalogEntry.getItemIndexingLabels().addAll(itemIndexingLabels);
		
		String catalogEntryHash = getObjectMD5Hash(catalogEntry);
		boolean entryAlreadyExistsInMap = checkCatalogEntryMap(catalogEntryHash);
		if (!entryAlreadyExistsInMap) {
			catalogEntry.setItemCreationTime(date);
			addCatalogEntryForApolloLibraryItemToMap(catalogEntryHash, catalogEntry);			
		}
		
		return catalogEntryHash;
	}
	
	public synchronized List<String> getUuidsCreatedSinceDateTime(XMLGregorianCalendar creationDateTime) {
		List<String> resultList = new ArrayList<String>();
		
		for (String uuid : catalogEntryMap.keySet()) {
			CatalogEntryForApolloLibraryItem item = getCatalogEntryForApolloLibraryItemFromMap(uuid);
			int c = item.getItemCreationTime().compare(creationDateTime);
			if ((c == DatatypeConstants.EQUAL) || (c == DatatypeConstants.GREATER)) {
				resultList.add(uuid);
			}
		}
		
		return resultList;
	}
	
	public synchronized List<String> getUuidsGivenType(String type) {
		List<String> resultList = new ArrayList<String>();
		
		for (String uuid : catalogEntryMap.keySet()) {
			CatalogEntryForApolloLibraryItem item = getCatalogEntryForApolloLibraryItemFromMap(uuid);
			if (item.getItemType().value().equals(type + "Type")) {
				resultList.add(uuid);
			}
		}
		
		return resultList;
	}
	
	public void closeDb40() {
		db4o.close();
	}
}
