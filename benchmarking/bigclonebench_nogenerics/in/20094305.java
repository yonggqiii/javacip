


class c20094305 {

    protected String getPageText(final String url) {
        StringBuilder b = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                b.append(line).append('\n');
            }
        } catch (IORuntimeException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IORuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return b.toString();
    }

}
