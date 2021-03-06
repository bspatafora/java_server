package javaserver.core.helpers;

import java.util.HashMap;
import java.util.Map;

public class URL {
    private static final HashMap<String, String> percentCodes;
    static {
        percentCodes = new HashMap<>();
        percentCodes.put("%20", " ");
        percentCodes.put("%3C", "<");
        percentCodes.put("%2C", ",");
        percentCodes.put("%3E", ">");
        percentCodes.put("%3D", "=");
        percentCodes.put("%3B", ";");
        percentCodes.put("%2B", "+");
        percentCodes.put("%26", "&");
        percentCodes.put("%40", "@");
        percentCodes.put("%23", "#");
        percentCodes.put("%24", "\\$");
        percentCodes.put("%5B", "[");
        percentCodes.put("%5D", "]");
        percentCodes.put("%3A", ":");
        percentCodes.put("%22", "\"");
        percentCodes.put("%3F", "?");
    }

    public static String percentCode(String string) {
        for (Map.Entry<String, String> entry : percentCodes.entrySet()) {
            string = string.replaceAll(entry.getKey(), entry.getValue());
        }
        return string;
    }

    public static HashMap<String, String> urlParameters(String url) {
        HashMap<String, String> urlParameters = new HashMap<>();
        String[] urlTokens = url.split("[?=&]");

        int nameValuePair = 2;
        for (int nameIndex = 1; nameIndex < urlTokens.length; nameIndex += nameValuePair) {
            int valueIndex = nameIndex + 1;
            urlParameters.put(urlTokens[nameIndex], urlTokens[valueIndex]);
        }

        return urlParameters;
    }
}
