class c10265164 {

    public String getLatestVersion(String website) {
        String latestVersion = "";
        try {
            URL url = new URL(website + "/version");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                latestVersion = string;
            }
            bufferedReader.close();
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
        }
        return latestVersion;
    }
}
