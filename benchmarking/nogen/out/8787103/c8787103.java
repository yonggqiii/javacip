class c8787103 {

    private void headinfoThread() {
        try {
            URLConnection urlc = JavaCIPUnknownScope.resource.url.openConnection();
            JavaCIPUnknownScope.resource.setFileSize(urlc.getContentLength());
            JavaCIPUnknownScope.resource.setDate(urlc.getLastModified());
        } catch (IORuntimeException e) {
            System.out.println("Error ResourceConnection, downloading headinfo");
            System.out.println(e);
        }
    }
}
