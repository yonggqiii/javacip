class c14377058 {

    public String process(URL url) throws IORuntimeException {
        String line, results = "";
        InputStream is = url.openStream();
        BufferedReader dis = new BufferedReader(new InputStreamReader(is));
        while ((line = dis.readLine()) != null) {
            results += line + "\n";
        }
        System.out.println(results);
        return results;
    }
}
