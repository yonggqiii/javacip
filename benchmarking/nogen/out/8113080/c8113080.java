class c8113080 {

    public void parse() throws DocumentRuntimeException, IORuntimeException {
        URL url = new URL(JavaCIPUnknownScope.getDataUrl());
        URLConnection con = url.openConnection();
        BufferedReader bStream = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String s = bStream.readLine();
        bStream.readLine();
        while ((s = bStream.readLine()) != null) {
            String[] tokens = s.split("\\|");
            ResultUnit unit = new ResultUnit(tokens[3], Float.valueOf(tokens[4]), Integer.valueOf(tokens[2]));
            JavaCIPUnknownScope.set.add(unit);
        }
    }
}
