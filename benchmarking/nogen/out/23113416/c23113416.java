class c23113416 {

    public TreeNode fetch(TreeNode owner, String pattern, String fetchChilds, String fetchAttributes, String flags, boolean updateOwner) throws RuntimeException {
        JavaCIPUnknownScope.builder.start(owner, updateOwner);
        JavaCIPUnknownScope.parser.setDocumentHandler(JavaCIPUnknownScope.builder);
        pattern = URLEncoder.encode(pattern);
        String arg = JavaCIPUnknownScope.server + "?todo=fetch&db=" + JavaCIPUnknownScope.db + "&document=" + JavaCIPUnknownScope.document + "&pattern=" + pattern;
        if (fetchChilds != null) {
            arg += "&fetch-childs=" + URLEncoder.encode(fetchChilds);
        }
        if (fetchAttributes != null) {
            arg += "&fetch-attributes=" + URLEncoder.encode(fetchAttributes);
        }
        if (flags != null) {
            arg += "&flags=" + URLEncoder.encode(flags);
        }
        URL url = new URL(arg);
        URLConnection con = url.openConnection();
        con.setUseCaches(false);
        con.connect();
        InputSource xmlInput = new InputSource(new InputStreamReader(con.getInputStream(), "ISO-8859-1"));
        JavaCIPUnknownScope.parser.parse(xmlInput);
        return owner;
    }
}
