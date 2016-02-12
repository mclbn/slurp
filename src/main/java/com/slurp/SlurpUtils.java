package com.slurp;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.*;

import java.io.File;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlurpUtils {
    private static SlurpUtils instance = new SlurpUtils();
    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;

    private SlurpUtils() {

    }

    public static SlurpUtils getInstance() { return instance; }

    public void initUtils(final IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
    }

    public IBurpExtenderCallbacks getCallbacks() {
        return this.callbacks;
    }

    public IExtensionHelpers getHelpers() {
        return this.helpers;
    }

    public String getUriFromRequest(IHttpRequestResponse msg) {
        Pattern pattern;
        Matcher m;
        byte [] req = msg.getRequest();
        String reqAsString = this.helpers.bytesToString(req);

        pattern = Pattern.compile(".* (.*) .*");
        m = pattern.matcher(reqAsString);
        if (m.find()) {
            String finalString = msg.getHttpService().getProtocol().concat("://").concat(msg.getHttpService().getHost()).concat(":").concat(Integer.toString(msg.getHttpService().getPort())).concat(m.group(1));
            return finalString;
        }
        return "";
    }

    public int getCodeFromResponse(byte [] msg) {
        Pattern pattern;
        Matcher m;

        if (msg != null) {
            String respAsString = this.helpers.bytesToString(msg);

            pattern = Pattern.compile("HTTP/\\d\\.\\d (\\d\\d\\d).*");
            m = pattern.matcher(respAsString);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }
        return 0;
    }

    public String getUrnFromRequest(byte [] msg) {
        Pattern pattern;
        Matcher m;

        if (msg != null) {
            String reqAsString = this.helpers.bytesToString(msg);

            pattern = Pattern.compile(".* (.*) .*");
            m = pattern.matcher(reqAsString);
            if (m.find()) {
                return m.group(1);
            }
        }
        return null;
    }

    public static String getBaseDir(java.net.URL url) {
        String uriStr = url.toString();
        String exploded[];

        exploded = uriStr.split("&");

        File file = new File(exploded[0]);
        return file.getParent().concat("/");
    }

    public boolean isInSitemap(java.net.URL url) {
        IHttpRequestResponse[] siteMap;
        IRequestInfo entryInfo;

        siteMap = this.callbacks.getSiteMap(null);
        for (IHttpRequestResponse entry : siteMap) {
            entryInfo = this.helpers.analyzeRequest(entry);

            if (entryInfo.getUrl().equals(url)) {
                return true;
            }
        }
        return false;
    }

    public String generateHash(int length) {
        SecureRandom random = new SecureRandom();
        char [] characterSet = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        char[] result = new char[length];

        for (int i = 0; i < result.length; i++) {
            int randomCharIndex = random.nextInt(characterSet.length);

            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }

    public static String getResponseBody(IHttpRequestResponse msg) {
        IResponseInfo resInfo = SlurpUtils.getInstance().getHelpers().analyzeResponse(msg.getResponse());
        return SlurpUtils.getInstance().getHelpers().bytesToString(msg.getResponse()).substring(resInfo.getBodyOffset());
    }

    public static String getRequestBody(IHttpRequestResponse msg) {
        IRequestInfo reqInfo = SlurpUtils.getInstance().getHelpers().analyzeRequest(msg.getRequest());
        return SlurpUtils.getInstance().getHelpers().bytesToString(msg.getRequest()).substring(reqInfo.getBodyOffset());
    }
}
