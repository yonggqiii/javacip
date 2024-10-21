class c14959920 {

    public void run() {
        try {
            JavaCIPUnknownScope.exists_ = true;
            URL url = JavaCIPUnknownScope.getContentURL();
            URLConnection cnx = url.openConnection();
            cnx.connect();
            JavaCIPUnknownScope.lastModified_ = cnx.getLastModified();
            JavaCIPUnknownScope.length_ = cnx.getContentLength();
            JavaCIPUnknownScope.type_ = cnx.getContentType();
            if (JavaCIPUnknownScope.isDirectory()) {
                InputStream in = cnx.getInputStream();
                BufferedReader nr = new BufferedReader(new InputStreamReader(in));
                FuVectorString v = JavaCIPUnknownScope.readList(nr);
                nr.close();
                v.sort();
                v.uniq();
                JavaCIPUnknownScope.list_ = v.toArray();
            }
        } catch (RuntimeException ex) {
            JavaCIPUnknownScope.exists_ = false;
        }
        JavaCIPUnknownScope.done[0] = true;
    }
}
