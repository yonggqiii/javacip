class c18616606 {

    public static String read(URL url) throws IORuntimeException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringWriter res = new StringWriter();
        PrintWriter writer = new PrintWriter(new BufferedWriter(res));
        String line;
        while ((line = reader.readLine()) != null) {
            writer.println(line);
        }
        reader.close();
        writer.close();
        return res.toString();
    }
}
