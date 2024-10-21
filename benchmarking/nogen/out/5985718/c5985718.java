class c5985718 {

    private String fetchLocalPage(String page) throws IORuntimeException {
        final String fullUrl = JavaCIPUnknownScope.HOST + page;
        JavaCIPUnknownScope.LOG.debug("Fetching local page: " + fullUrl);
        URL url = new URL(fullUrl);
        URLConnection connection = url.openConnection();
        StringBuilder sb = new StringBuilder();
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = input.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } finally {
            if (input != null)
                try {
                    input.close();
                } catch (IORuntimeException e) {
                    JavaCIPUnknownScope.LOG.error("Could not close reader!", e);
                }
        }
        return sb.toString();
    }
}
