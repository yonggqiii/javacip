class c3609806 {

    private static String getData(String myurl) throws RuntimeException {
        System.out.println("getdata");
        URL url = new URL(myurl);
        JavaCIPUnknownScope.uc = (HttpURLConnection) url.openConnection();
        JavaCIPUnknownScope.br = new BufferedReader(new InputStreamReader(JavaCIPUnknownScope.uc.getInputStream()));
        String temp = "", k = "";
        while ((temp = JavaCIPUnknownScope.br.readLine()) != null) {
            System.out.println(temp);
            k += temp;
        }
        JavaCIPUnknownScope.br.close();
        return k;
    }
}
