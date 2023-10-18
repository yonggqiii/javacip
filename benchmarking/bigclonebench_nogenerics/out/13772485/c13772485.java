class c13772485 {

    private static String getData(String myurl) throws RuntimeException {
        URL url = new URL(myurl);
        JavaCIPUnknownScope.uc = (HttpURLConnection) url.openConnection();
        if (JavaCIPUnknownScope.login) {
            JavaCIPUnknownScope.uc.setRequestProperty("Cookie", JavaCIPUnknownScope.logincookie + ";" + JavaCIPUnknownScope.xfsscookie);
        }
        JavaCIPUnknownScope.br = new BufferedReader(new InputStreamReader(JavaCIPUnknownScope.uc.getInputStream()));
        String temp = "", k = "";
        while ((temp = JavaCIPUnknownScope.br.readLine()) != null) {
            k += temp;
        }
        JavaCIPUnknownScope.br.close();
        return k;
    }
}
