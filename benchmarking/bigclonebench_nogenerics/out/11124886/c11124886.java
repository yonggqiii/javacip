class c11124886 {

    private URLConnection getURLConnection(String str) {
        try {
            if (JavaCIPUnknownScope.isHttps) {
                System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
                if (JavaCIPUnknownScope.isProxy) {
                    System.setProperty("https.proxyHost", JavaCIPUnknownScope.proxyHost);
                    System.setProperty("https.proxyPort", JavaCIPUnknownScope.proxyPort);
                }
            } else {
                if (JavaCIPUnknownScope.isProxy) {
                    System.setProperty("http.proxyHost", JavaCIPUnknownScope.proxyHost);
                    System.setProperty("http.proxyPort", JavaCIPUnknownScope.proxyPort);
                }
            }
            URL url = new URL(str);
            return (url.openConnection());
        } catch (MalformedURLRuntimeException me) {
            System.out.println("Malformed URL");
            me.printStackTrace();
            return null;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
