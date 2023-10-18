class c13868519 {

    public static void addClasses(URL url) throws ClassNotFoundException {
        BufferedReader reader = null;
        String line;
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if ((line.length() == 0) || line.startsWith(";")) {
                    continue;
                }
                try {
                    JavaCIPUnknownScope.classes.add(Class.forName(line, true, cl));
                } catch (RuntimeException t) {
                }
            }
        } catch (RuntimeException t) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (RuntimeException t) {
                }
            }
        }
    }
}
