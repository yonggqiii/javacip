class c21492244 {

    private String getData(String myurl) throws RuntimeException {
        URL url = new URL(myurl);
        JavaCIPUnknownScope.uc = (HttpURLConnection) url.openConnection();
        JavaCIPUnknownScope.uc.setRequestProperty("Cookie", NetLoadAccount.getPhpsessioncookie());
        JavaCIPUnknownScope.br = new BufferedReader(new InputStreamReader(JavaCIPUnknownScope.uc.getInputStream()));
        String temp = "", k = "";
        while ((temp = JavaCIPUnknownScope.br.readLine()) != null) {
            k += temp;
        }
        JavaCIPUnknownScope.br.close();
        return k;
    }
}
