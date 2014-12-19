package edu.pitt.apollo.libraryservice.methods;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
//import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.library_service_types.v2_1_0.GetDiffMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetDiffResult;
import edu.pitt.apollo.library_service_types.v2_1_0.LibraryItemContainer;
import edu.pitt.apollo.services_common.v2_1_0.Authentication;
import edu.pitt.apollo.services_common.v2_1_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v2_1_0.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_1_0.ApolloIndexableItem;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 10, 2014
 * Time: 3:13:57 PM
 * Class: GetDiffMethod
 */
public class GetDiffMethod {

	private static String getJsonForItem(ApolloIndexableItem e) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(
				org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		mapper.getSerializationConfig().setDateFormat(sdf);
		mapper.configure(Feature.WRITE_NULL_PROPERTIES, false);
// mapper.defaultPrettyPrintingWriter().configure(SerializationFeature.WRITE_NULL_MAP_VALUES,
// false);
		String jsonStr;
		try {
			jsonStr = mapper.defaultPrettyPrintingWriter()
					.writeValueAsString(e);
		} catch (Exception e1) {
			return "Error: " + e1.getMessage();
		}
		return jsonStr;
	}

	private static List<String> getLinesFromString(String string) {

		String[] lines = string.split("\n");
		List<String> list = Arrays.asList(lines);
		return list;
	}

	public static GetDiffResult getDiff(LibraryDbUtils dbUtils, GetDiffMessage message) {

		GetDiffResult result = new GetDiffResult();
//		MethodCallStatus status = new MethodCallStatus();
//		result.setStatus(status);
//
//		Authentication authentication = message.getAuthentication();
//		String uri = message.getUri();
//		int version1 = Integer.parseInt(message.getVersion1());
//		int version2 = Integer.parseInt(message.getVersion2());
//
//		try {
//			boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.READONLY);
//			if (userAuthorized) {
//				LibraryItemContainer container1 = dbUtils.getLibraryItemContainer(uri, version1);
//				LibraryItemContainer container2 = dbUtils.getLibraryItemContainer(uri, version2);
//
//				ApolloIndexableItem item1 = container1.getLibraryItem();
//				ApolloIndexableItem item2 = container2.getLibraryItem();
//
//				String item1Json = getJsonForItem(item1);
//				String item2Json = getJsonForItem(item2);
//
//				List<String> item1Lines = getLinesFromString(item1Json);
//				List<String> item2Lines = getLinesFromString(item2Json);
//
//// Compute diff. Get the Patch object. Patch is the container for computed deltas.
//				Patch patch = DiffUtils.diff(item1Lines, item2Lines);
//
////			for (Delta delta : patch.getDeltas()) {
////				System.out.println(delta);
////			}
//				result.setDiff(patch.getDeltas().get(0).toString());
//				status.setStatus(MethodCallStatusEnum.COMPLETED);
//			} else {
//				status.setStatus(MethodCallStatusEnum.AUTHENTICATION_FAILURE);
//				status.setMessage("You are not authorized to view diffs of items in the library.");
//			}
////		} catch (ApolloDatabaseException ex) {
////			status.setStatus(MethodCallStatusEnum.FAILED);
////			status.setMessage(ex.getMessage());
//		} catch (IOException ex) {
//			status.setStatus(MethodCallStatusEnum.FAILED);
//			status.setMessage(ex.getMessage());
//		}

		return result;
	}

}
