class c9319440 {

    public static String load(String id) {
        String xml = "";
        if (id.length() < 5)
            return "";
        try {
            JavaCIPUnknownScope.working = true;
            URL url = new URL("http://pastebin.com/download.php?i=" + id);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            xml = "";
            String str;
            while ((str = reader.readLine()) != null) {
                xml += str;
            }
            reader.close();
            JavaCIPUnknownScope.working = false;
            return xml.toString();
        } catch (IORuntimeException ex) {
            JOptionPane.showMessageDialog(null, " Load error");
        }
        JavaCIPUnknownScope.working = false;
        return xml;
    }
}
