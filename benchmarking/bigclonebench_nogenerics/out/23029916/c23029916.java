class c23029916 {

    public static void notify(String msg) throws RuntimeException {
        String url = "http://api.clickatell.com/http/sendmsg?";
        url = JavaCIPUnknownScope.add(url, "user", JavaCIPUnknownScope.user);
        url = JavaCIPUnknownScope.add(url, "password", JavaCIPUnknownScope.password);
        url = JavaCIPUnknownScope.add(url, "api_id", JavaCIPUnknownScope.apiId);
        url = JavaCIPUnknownScope.add(url, "to", JavaCIPUnknownScope.to);
        url = JavaCIPUnknownScope.add(url, "text", msg);
        URL u = new URL(url);
        URLConnection c = u.openConnection();
        InputStream is = c.getInputStream();
        IOUtils.copy(is, System.out);
        IOUtils.closeQuietly(is);
        System.out.println();
    }
}
