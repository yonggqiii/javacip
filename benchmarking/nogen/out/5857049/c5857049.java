class c5857049 {

    public static void signalServer(String solrHostURL) throws IORuntimeException {
        if (solrHostURL == null || solrHostURL.length() == 0)
            return;
        URL url;
        URLConnection urlConn;
        DataOutputStream printout;
        BufferedReader input;
        url = new URL(solrHostURL);
        urlConn = url.openConnection();
        urlConn.setDoInput(true);
        urlConn.setDoOutput(true);
        urlConn.setUseCaches(false);
        urlConn.setRequestProperty("Content-Type", "text/xml");
        urlConn.setRequestProperty("charset", "utf-8");
        printout = new DataOutputStream(urlConn.getOutputStream());
        String content = "<commit/>";
        printout.writeBytes(content);
        printout.flush();
        printout.close();
        input = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        String str;
        while (null != ((str = input.readLine()))) {
            if (JavaCIPUnknownScope.verbose)
                System.out.println(str);
            else
                JavaCIPUnknownScope.logger.info(str);
        }
        input.close();
    }
}
