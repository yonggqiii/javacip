class c14650352 {

    boolean checkIfUserExists(String username) throws IORuntimeException {
        try {
            URL url = new URL(JavaCIPUnknownScope.WS_URL + "/user/" + URLEncoder.encode(username, "UTF-8") + "/profile.xml");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            is.close();
            return true;
        } catch (FileNotFoundRuntimeException e) {
            return false;
        }
    }
}
