


class c19948815 {

    public static int validate(String url) {
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) (new URL(url)).openConnection();
        } catch (MalformedURLRuntimeException ex) {
            return -1;
        } catch (IORuntimeException ex) {
            return -2;
        }
        try {
            if (con != null && con.getResponseCode() != 200) {
                return con.getResponseCode();
            } else if (con == null) {
                return -3;
            }
        } catch (IORuntimeException ex) {
            return -4;
        }
        return 1;
    }

}
