class c4249821 {

    private String loadStatusResult() {
        try {
            URL url = new URL(JavaCIPUnknownScope.getServerUrl());
            InputStream input = url.openStream();
            InputStreamReader is = new InputStreamReader(input, "utf-8");
            BufferedReader reader = new BufferedReader(is);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (MalformedURLRuntimeException e1) {
            e1.printStackTrace();
            return null;
        } catch (IORuntimeException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
