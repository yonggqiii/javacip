class c7615994 {

    public String[] list() {
        if (JavaCIPUnknownScope.isURL) {
            if (!JavaCIPUnknownScope.isDirectory())
                return null;
            try {
                URLConnection c = JavaCIPUnknownScope.url.openConnection();
                InputStream is = c.getInputStream();
                boolean foundEnd = false;
                Vector files = new Vector();
                while (!foundEnd) {
                    byte[] b = new byte[is.available()];
                    String s = new String(b);
                    if (s.toLowerCase().indexOf("</html>") != -1)
                        foundEnd = true;
                    while (s.indexOf("a href") != -1) {
                        int ndx = s.indexOf("a href") + 8;
                        String f = s.substring(ndx, s.indexOf("\"", ndx));
                        s = s.substring(s.indexOf("\"", ndx) + 1);
                        Location check = new Location(JavaCIPUnknownScope.getAbsolutePath(), f);
                        if (check.exists()) {
                            files.add(check.getName());
                        }
                    }
                }
                return (String[]) files.toArray(new String[0]);
            } catch (IORuntimeException e) {
                return null;
            }
        }
        return JavaCIPUnknownScope.file.list();
    }
}
