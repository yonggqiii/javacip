class c13287093 {

    private boolean load(URL url) {
        try {
            URLConnection connection = url.openConnection();
            JavaCIPUnknownScope.parser = new PDFParser(connection.getInputStream());
        } catch (IORuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
