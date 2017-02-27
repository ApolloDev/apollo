package edu.pitt.apollo.apollolocationservicesdk;

import edu.pitt.apollo.apollolocationservicesdk.connectors.ApolloLocationServiceCachedConnector;
import edu.pitt.apollo.apollolocationservicesdk.connectors.ApolloLocationServiceWebConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mas400 on 7/12/16.
 */
public class ApolloLocationServiceCachedConnectorTest {
    private ApolloLocationServiceCachedConnector cachedConnector;

    @Before
    public void setUp() throws Exception {
        List<String> codes = new ArrayList<>();
        codes.add("1169");
        codes.add("5981");
        codes.add("7877");
        codes.add("6028");
        codes.add("6454");
        codes.add("6480");
        codes.add("6566");
        codes.add("5418");
        codes.add("6710");
        codes.add("6623");
        codes.add("5526");
        codes.add("7450");
        codes.add("6083");
        codes.add("6758");
        codes.add("7773");
        codes.add("8005");
        codes.add("6720");
        codes.add("5856");
        codes.add("6861");
        codes.add("5442");
        codes.add("6482");
        codes.add("7959");
        codes.add("5740");
        codes.add("8195");
        codes.add("8114");
        codes.add("5366");
        codes.add("6197");
        codes.add("7345");
        codes.add("5000");
        codes.add("1375");
        codes.add("1216");
        codes.add("1217");
        codes.add("1218");
        codes.add("1219");
        codes.add("1220");
        codes.add("1221");
        codes.add("1222");
        codes.add("1224");
        codes.add("1241");
        codes.add("1242");
        codes.add("1244");
        codes.add("1246");
        codes.add("1247");
        codes.add("1254");
        codes.add("1282");
        codes.add("1283");
        codes.add("1286");
        codes.add("1292");
        codes.add("1294");
        codes.add("1298");
        codes.add("1304");
        codes.add("1306");
        codes.add("2034");
        codes.add("2209");
        codes.add("1186");
        codes.add("1187");
        codes.add("1190");
        codes.add("1189");
        codes.add("1195");
        codes.add("1192");
        codes.add("1193");
        codes.add("1191");
        codes.add("1188");
        codes.add("1207");
        codes.add("1184");
        codes.add("1206");
        codes.add("1185");
        codes.add("1194");
        codes.add("1210");
        codes.add("1208");
        codes.add("1209");
        codes.add("1183");
        codes.add("1211");
        codes.add("1182");
        codes.add("1196");
        codes.add("1197");
        codes.add("1198");
        codes.add("1199");
        codes.add("1200");
        codes.add("1201");
        codes.add("1202");
        codes.add("1203");
        codes.add("1204");
        codes.add("1205");
        codes.add("1212");
        codes.add("1213");
        codes.add("1225");
        codes.add("1228");
        codes.add("1229");
        codes.add("1231");
        codes.add("1236");
        codes.add("1238");
        codes.add("1245");
        codes.add("1248");
        codes.add("1255");
        codes.add("1261");
        codes.add("1262");
        codes.add("1263");
        codes.add("1264");
        codes.add("1265");
        codes.add("1266");
        codes.add("1267");
        codes.add("1268");
        codes.add("1269");
        codes.add("1270");
        codes.add("1271");
        codes.add("1272");
        codes.add("1273");
        codes.add("1274");
        codes.add("1275");
        codes.add("1226");
        codes.add("1227");
        codes.add("1230");
        codes.add("1232");
        codes.add("1234");
        codes.add("1235");
        codes.add("1237");
        codes.add("1239");
        codes.add("1240");
        codes.add("1223");
        codes.add("1233");
        codes.add("1257");
        codes.add("1249");
        codes.add("1250");
        codes.add("1251");
        codes.add("1253");
        codes.add("1256");
        codes.add("1258");
        codes.add("1259");
        codes.add("1260");
        codes.add("1243");
        codes.add("1252");
        codes.add("1276");
        codes.add("1278");
        codes.add("1279");
        codes.add("1287");
        codes.add("1291");
        codes.add("1293");
        codes.add("1296");
        codes.add("1297");
        codes.add("1299");
        codes.add("1300");
        codes.add("1301");
        codes.add("1302");
        codes.add("1303");
        cachedConnector = new ApolloLocationServiceCachedConnector(codes, new LeastFrequentlyUsedCache(100), new ApolloLocationServiceWebConnector());
    }

    @Test
    public void testCacheConnector() throws Exception {
        String geoJson = cachedConnector.getLocationByID("1169");

    }

    @After
    public void tearDown() throws Exception {

    }

}