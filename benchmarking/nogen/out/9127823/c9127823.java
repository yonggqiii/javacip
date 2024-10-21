class c9127823 {

    public static Status checkUpdate() {
        Status updateStatus = Status.FAILURE;
        URL url;
        InputStream is;
        InputStreamReader isr;
        BufferedReader r;
        String line;
        try {
            url = new URL(JavaCIPUnknownScope.updateURL);
            is = url.openStream();
            isr = new InputStreamReader(is);
            r = new BufferedReader(isr);
            String variable, value;
            while ((line = r.readLine()) != null) {
                if (!line.equals("") && line.charAt(0) != '/') {
                    variable = line.substring(0, line.indexOf('='));
                    value = line.substring(line.indexOf('=') + 1);
                    if (variable.equals("Latest Version")) {
                        variable = value;
                        value = variable.substring(0, variable.indexOf(" "));
                        variable = variable.substring(variable.indexOf(" ") + 1);
                        JavaCIPUnknownScope.latestGameVersion = value;
                        JavaCIPUnknownScope.latestModifier = variable;
                        if (Float.parseFloat(value) > Float.parseFloat(JavaCIPUnknownScope.gameVersion))
                            updateStatus = Status.NOT_CURRENT;
                        else
                            updateStatus = Status.CURRENT;
                    } else if (variable.equals("Download URL"))
                        JavaCIPUnknownScope.downloadURL = value;
                }
            }
            return updateStatus;
        } catch (MalformedURLRuntimeException e) {
            return Status.URL_NOT_FOUND;
        } catch (IORuntimeException e) {
            return Status.FAILURE;
        }
    }
}
