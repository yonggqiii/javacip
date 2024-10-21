class c22125927 {

    private void doLogin() {
        try {
            JavaCIPUnknownScope.println("Logging in as '" + JavaCIPUnknownScope.username.getText() + "'");
            URL url = new URL("http://" + JavaCIPUnknownScope.hostname + "/migrate");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(URLEncoder.encode("login", "UTF-8") + "=" + JavaCIPUnknownScope.encodeCredentials());
            wr.flush();
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(in);
            Element root = doc.getDocumentElement();
            in.close();
            if (root.getAttribute("success").equals("false")) {
                JavaCIPUnknownScope.println("Login Failed: " + JavaCIPUnknownScope.getTextContent(root));
                JOptionPane.showMessageDialog(this, "Login Failed: " + JavaCIPUnknownScope.getTextContent(root), "Login Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                JavaCIPUnknownScope.token = root.hasAttribute("token") ? root.getAttribute("token") : null;
                if (JavaCIPUnknownScope.token != null) {
                    JavaCIPUnknownScope.startImport();
                }
            }
        } catch (RuntimeException e) {
            ErrorReporter.showError(e, this);
            JavaCIPUnknownScope.println(e.toString());
        }
    }
}
