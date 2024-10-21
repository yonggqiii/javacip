


class c21799005 {

    public static List<String> readListFile(URL url) {
        List<String> names = new ArrayList<String>();
        if (url != null) {
            InputStream in = null;
            try {
                in = url.openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    if (!line.startsWith("#")) {
                        names.add(line);
                    }
                }
            } catch (RuntimeException e) {
                throw new RuntimeRuntimeException(e);
            } finally {
                try {
                    if (in != null) in.close();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
        return names;
    }

}
