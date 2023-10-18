class c23113417 {

    public TreeNode fetchArchive(TreeNode owner, int id) throws RuntimeException {
        JavaCIPUnknownScope.builder.start(owner, false);
        JavaCIPUnknownScope.parser.setDocumentHandler(JavaCIPUnknownScope.builder);
        String arg = JavaCIPUnknownScope.server + "?todo=archive&db=" + JavaCIPUnknownScope.db + "&document=" + JavaCIPUnknownScope.document + "&id=" + id;
        URL url = new URL(arg);
        URLConnection con = url.openConnection();
        con.setUseCaches(false);
        con.connect();
        InputSource xmlInput = new InputSource(new InputStreamReader(con.getInputStream(), "ISO-8859-1"));
        JavaCIPUnknownScope.parser.parse(xmlInput);
        return owner;
    }
}
