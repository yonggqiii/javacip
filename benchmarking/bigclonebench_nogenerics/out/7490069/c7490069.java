class c7490069 {

    public void run() {
        if (JavaCIPUnknownScope.isRunning)
            return;
        JavaCIPUnknownScope.isRunning = true;
        Core core = Core.getInstance();
        URL url = null;
        InputStream input = null;
        DataInputStream datastream;
        try {
            url = new URL(Constants.UpdateCheckUrl);
        } catch (MalformedURLRuntimeException e) {
            if (!JavaCIPUnknownScope.firstRun)
                core.showMessage(1, core.getString("error"), core.getString("errorUpdateCheck"));
            JavaCIPUnknownScope.isRunning = false;
            return;
        }
        try {
            input = url.openStream();
        } catch (IORuntimeException e) {
            if (!JavaCIPUnknownScope.firstRun)
                core.showMessage(1, core.getString("error"), core.getString("errorUpdateCheck"));
            JavaCIPUnknownScope.isRunning = false;
            return;
        }
        datastream = new DataInputStream(new BufferedInputStream(input));
        String line = null;
        try {
            line = datastream.readLine();
        } catch (IORuntimeException e) {
            e.printStackTrace();
            if (!JavaCIPUnknownScope.firstRun)
                core.showMessage(1, core.getString("error"), core.getString("errorUpdateCheck"));
            JavaCIPUnknownScope.isRunning = false;
            return;
        }
        if (line == null) {
            if (!JavaCIPUnknownScope.firstRun)
                core.showMessage(1, core.getString("error"), core.getString("errorUpdateCheck"));
            JavaCIPUnknownScope.isRunning = false;
            return;
        }
        if (line.trim().equalsIgnoreCase(Constants.version)) {
            if (!JavaCIPUnknownScope.firstRun)
                core.showMessage(0, core.getString("checkUpdateButton"), core.getString("versionMatch"));
        } else {
            core.showMessage(1, core.getString("checkUpdateButton"), core.getString("errorNewerVersion") + ": " + line);
        }
        JavaCIPUnknownScope.isRunning = false;
    }
}
