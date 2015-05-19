package edu.pitt.apollo;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by jdl50 on 5/18/15.
 */
public class Md5Utils {

    JsonUtils jsonUtils = new JsonUtils();

    public String getMd5FromString(String string) {
        return DigestUtils.md5Hex(string);
    }

    protected String getSaltForPassword() {
        Random random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public String getMd5(Object object) throws Md5UtilsException {

        try {
            return DigestUtils.md5Hex(jsonUtils.getJsonBytes(object).toString());
        } catch (Exception ex) {
            throw new Md5UtilsException("Exception getting MD5 hash: " + ex.getMessage());
        }
    }



}
