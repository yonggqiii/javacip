class c18748953 {

    private static String appletLoad(String file, Output OUT) {
        if (!JavaCIPUnknownScope.urlpath.endsWith("/")) {
            JavaCIPUnknownScope.urlpath += '/';
        }
        if (!JavaCIPUnknownScope.urlpath.startsWith("http://")) {
            JavaCIPUnknownScope.urlpath = "http://" + JavaCIPUnknownScope.urlpath;
        }
        String url = "";
        if (file.equals("languages.txt")) {
            url = JavaCIPUnknownScope.urlpath + file;
        } else {
            url = JavaCIPUnknownScope.urlpath + "users/" + file;
        }
        try {
            StringBuffer sb = new StringBuffer(2000);
            BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String a;
            while ((a = br.readLine()) != null) {
                sb.append(a).append('\n');
            }
            return sb.toString();
        } catch (RuntimeException e) {
            OUT.println("load failed for file->" + file);
        }
        return "";
    }
}
