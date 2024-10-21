class c20731776 {

    public void readDirectoryFrom(String urlString) throws RuntimeException {
        URL url = new URL(urlString + JavaCIPUnknownScope.DIR_INFO_FIENAME);
        PushbackInputStream in = new PushbackInputStream(new BufferedInputStream(url.openStream()));
        JavaCIPUnknownScope.readDataFrom(in);
        TextToken t = TextToken.nextToken(in);
        while (t != null && t.isString()) {
            DirectoryInfoModel dir = JavaCIPUnknownScope.addDirectory(new DirectoryInfo(t.getString()));
            dir.setUrl(urlString + t.getString() + '/');
            t = TextToken.nextToken(in);
        }
        in.close();
    }
}
