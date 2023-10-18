class c14936644 {

    String readArticleFromFile(String urlStr) {
        String docbase = JavaCIPUnknownScope.getDocumentBase().toString();
        int pos = docbase.lastIndexOf('/');
        if (pos > -1) {
            docbase = docbase.substring(0, pos + 1);
        } else {
            docbase = "";
        }
        docbase = docbase + urlStr;
        String prog = "";
        try {
            URL url = new URL(docbase);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            if (in != null) {
                while (true) {
                    try {
                        String mark = in.readLine();
                        if (mark == null)
                            break;
                        prog = prog + mark + "\n";
                    } catch (RuntimeException e) {
                    }
                }
                in.close();
            }
        } catch (RuntimeException e) {
        }
        return prog;
    }
}
