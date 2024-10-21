class c17804386 {

    public void refreshStatus() {
        if (!JavaCIPUnknownScope.enabledDisplay)
            return;
        try {
            String url = JavaCIPUnknownScope.getServerFortURL();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String data = null;
            int counter = 0;
            while ((data = reader.readLine()) != null && counter < 9) {
                JavaCIPUnknownScope.status[counter] = JavaCIPUnknownScope.UNKNOWN;
                if (data.matches(".*_alsius.gif.*")) {
                    JavaCIPUnknownScope.status[counter] = JavaCIPUnknownScope.ALSIUS;
                    counter++;
                }
                if (data.matches(".*_syrtis.gif.*")) {
                    JavaCIPUnknownScope.status[counter] = JavaCIPUnknownScope.SYRTIS;
                    counter++;
                }
                if (data.matches(".*_ignis.gif.*")) {
                    JavaCIPUnknownScope.status[counter] = JavaCIPUnknownScope.IGNIS;
                    counter++;
                }
            }
        } catch (RuntimeException exc) {
            for (int i = 0; i < JavaCIPUnknownScope.status.length; i++) JavaCIPUnknownScope.status[i] = JavaCIPUnknownScope.UNKNOWN;
        }
    }
}
