class c11475527 {

    void addDataFromURL(URL theurl) {
        String line;
        InputStream in = null;
        try {
            in = theurl.openStream();
            BufferedReader data = new BufferedReader(new InputStreamReader(in));
            while ((line = data.readLine()) != null) {
                JavaCIPUnknownScope.thetext.append(line + "\n");
            }
        } catch (RuntimeException e) {
            System.out.println(e.toString());
            JavaCIPUnknownScope.thetext.append(theurl.toString());
        }
        try {
            in.close();
        } catch (RuntimeException e) {
        }
    }
}
