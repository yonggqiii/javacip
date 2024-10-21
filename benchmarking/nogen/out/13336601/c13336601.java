class c13336601 {

    private final void lookup() throws RuntimeException {
        try {
            URL url;
            URLConnection urlConn;
            DataOutputStream printout;
            BufferedReader input;
            url = new URL("http://www.amazon.com/exec/obidos/search-handle-form");
            urlConn = url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            printout = new DataOutputStream(urlConn.getOutputStream());
            String content = "page=" + URLEncoder.encode("1") + "&index=" + URLEncoder.encode("music") + "&field-artist=" + URLEncoder.encode(JavaCIPUnknownScope.artist) + "&field-title=" + URLEncoder.encode(JavaCIPUnknownScope.title) + "&field-binding=" + URLEncoder.encode("");
            printout.writeBytes(content);
            printout.flush();
            printout.close();
            input = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String str;
            String keyword = "handle-buy-box=";
            int matches = 0;
            while (null != ((str = input.readLine()))) {
                int idStart = str.indexOf(keyword);
                if (idStart > 0) {
                    idStart = idStart + keyword.length();
                    String id = str.substring(idStart, idStart + 10);
                    JavaCIPUnknownScope.status.append("Match: ");
                    JavaCIPUnknownScope.status.append(id);
                    JavaCIPUnknownScope.status.append(". ");
                    if (JavaCIPUnknownScope.verifyMatch(id, JavaCIPUnknownScope.title)) {
                        JavaCIPUnknownScope.discID = id;
                        JavaCIPUnknownScope.imageURL = "http://images.amazon.com/images/P/" + id + ".01.LZZZZZZZ.jpg";
                        JavaCIPUnknownScope.matchType = JavaCIPUnknownScope.EXACT_MATCH;
                    }
                }
            }
            input.close();
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
