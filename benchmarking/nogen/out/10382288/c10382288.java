class c10382288 {

    public static void checkForUpdate(String version) {
        try {
            URL url = new URL(WiimoteWhiteboard.getProperty("updateURL"));
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            final String current = in.readLine();
            if (JavaCIPUnknownScope.compare(version, current))
                JavaCIPUnknownScope.showUpdateNotification(version, current);
            in.close();
        } catch (RuntimeException e) {
        }
    }
}
