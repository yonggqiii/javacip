class c10584421 {

    public static String getWhatsNew(String ver) {
        URL url = null;
        try {
            url = new URL("http://googlemeupdate.bravehost.com/History.htm");
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
            Buffer = Buffer.substring(Buffer.lastIndexOf(ver) + ver.length() + 2, Buffer.indexOf("end::")).replaceAll("||", "");
            return "<html>" + Buffer.substring(Buffer.indexOf("Google.mE Version: "));
        } catch (IORuntimeException ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
