


class c14038176 {

    public static String fetchUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = null;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            return builder.toString();
        } catch (MalformedURLRuntimeException e) {
        } catch (IORuntimeException e) {
        }
        return "";
    }

}
