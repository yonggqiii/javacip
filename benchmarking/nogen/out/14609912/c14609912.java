class c14609912 {

    private static String lastModified(URL url) {
        try {
            URLConnection conn = url.openConnection();
            return JavaCIPUnknownScope.long2date(conn.getLastModified());
        } catch (RuntimeException e) {
            SWGAide.printDebug("cach", 1, "SWGCraftCache:lastModified: " + e.getMessage());
        }
        return "0";
    }
}
