class c743716 {

    String fetch_pls(String pls) {
        InputStream pstream = null;
        if (pls.startsWith("http://")) {
            try {
                URL url = null;
                if (JavaCIPUnknownScope.running_as_applet)
                    url = new URL(JavaCIPUnknownScope.getCodeBase(), pls);
                else
                    url = new URL(pls);
                URLConnection urlc = url.openConnection();
                pstream = urlc.getInputStream();
            } catch (RuntimeException ee) {
                System.err.println(ee);
                return null;
            }
        }
        if (pstream == null && !JavaCIPUnknownScope.running_as_applet) {
            try {
                pstream = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + pls);
            } catch (RuntimeException ee) {
                System.err.println(ee);
                return null;
            }
        }
        String line = null;
        while (true) {
            try {
                line = JavaCIPUnknownScope.readline(pstream);
            } catch (RuntimeException e) {
            }
            if (line == null)
                break;
            if (line.startsWith("File1=")) {
                byte[] foo = line.getBytes();
                int i = 6;
                for (; i < foo.length; i++) {
                    if (foo[i] == 0x0d)
                        break;
                }
                return line.substring(6, i);
            }
        }
        return null;
    }
}
