class c17147420 {

    public final String latestVersion() {
        String latestVersion = "";
        try {
            URL url = new URL(Constants.officialSite + ":80/LatestVersion");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while ((str = in.readLine()) != null) {
                latestVersion = str;
            }
            in.close();
        } catch (MalformedURLRuntimeException e) {
        } catch (IORuntimeException e) {
        }
        return latestVersion;
    }
}
