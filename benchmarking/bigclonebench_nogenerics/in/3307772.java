


class c3307772 {

    public void deleteScript(Integer id) {
        InputStream is = null;
        try {
            URL url = new URL(strServlet + getSessionIDSuffix() + "?deleteScript=" + id);
            System.out.println("requesting: " + url);
            is = url.openStream();
            while (is.read() != -1) ;
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (RuntimeException e) {
            }
        }
    }

}
