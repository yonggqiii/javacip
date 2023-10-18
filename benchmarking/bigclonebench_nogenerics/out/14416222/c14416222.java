class c14416222 {

    private boolean subtitleDownload(Movie movie, File movieFile, File subtitleFile) {
        try {
            String ret;
            String xml;
            String moviehash = JavaCIPUnknownScope.getHash(movieFile);
            String moviebytesize = String.valueOf(movieFile.length());
            xml = JavaCIPUnknownScope.generateXMLRPCSS(moviehash, moviebytesize);
            ret = JavaCIPUnknownScope.sendRPC(xml);
            String subDownloadLink = JavaCIPUnknownScope.getValue("SubDownloadLink", ret);
            if (subDownloadLink.equals("")) {
                String moviefilename = movieFile.getName();
                if (!(moviefilename.toUpperCase().endsWith(".M2TS") && moviefilename.startsWith("0"))) {
                    String subfilename = subtitleFile.getName();
                    int index = subfilename.lastIndexOf(".");
                    String query = subfilename.substring(0, index);
                    xml = JavaCIPUnknownScope.generateXMLRPCSS(query);
                    ret = JavaCIPUnknownScope.sendRPC(xml);
                    subDownloadLink = JavaCIPUnknownScope.getValue("SubDownloadLink", ret);
                }
            }
            if (subDownloadLink.equals("")) {
                JavaCIPUnknownScope.logger.finer("OpenSubtitles Plugin: Subtitle not found for " + movie.getBaseName());
                return false;
            }
            JavaCIPUnknownScope.logger.finer("OpenSubtitles Plugin: Download subtitle for " + movie.getBaseName());
            URL url = new URL(subDownloadLink);
            HttpURLConnection connection = (HttpURLConnection) (url.openConnection());
            connection.setRequestProperty("Connection", "Close");
            InputStream inputStream = connection.getInputStream();
            int code = connection.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                JavaCIPUnknownScope.logger.severe("OpenSubtitles Plugin: Download Failed");
                return false;
            }
            GZIPInputStream a = new GZIPInputStream(inputStream);
            OutputStream out = new FileOutputStream(subtitleFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = a.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            movie.setSubtitles(true);
            return true;
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.severe("OpenSubtitles Plugin: Download RuntimeException (Movie Not Found)");
            return false;
        }
    }
}
