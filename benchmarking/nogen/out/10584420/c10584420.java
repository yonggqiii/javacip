class c10584420 {

    public static String checkUpdate() {
        URL url = null;
        try {
            url = new URL("http://googlemeupdate.bravehost.com/");
        } catch (MalformedURLRuntimeException ex) {
            ex.printStackTrace();
        }
        InputStream html = null;
        try {
            html = url.openStream();
            int c = 0;
            String Buffer = "";
            String Code = "";
            while (c != -1) {
                try {
                    c = html.read();
                } catch (IORuntimeException ex) {
                }
                Buffer = Buffer + (char) c;
            }
            return Buffer.substring(Buffer.lastIndexOf("Google.mE Version: ") + 19, Buffer.indexOf("||"));
        } catch (IORuntimeException ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
