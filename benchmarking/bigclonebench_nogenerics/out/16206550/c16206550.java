class c16206550 {

    private String getJSONScoreStringFromNet(URL urladdress) {
        InputStream instream = null;
        BufferedReader read = null;
        try {
            instream = urladdress.openStream();
            read = new BufferedReader(new InputStreamReader(instream));
            String s = new String("");
            String line = null;
            while ((line = read.readLine()) != null) {
                s = s + line;
            }
            return s;
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                read.close();
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
