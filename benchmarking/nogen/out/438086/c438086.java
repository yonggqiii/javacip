class c438086 {

    public static void doVersionCheck(View view) {
        view.showWaitCursor();
        try {
            URL url = new URL(JavaCIPUnknownScope.jEdit.getProperty("version-check.url"));
            InputStream in = url.openStream();
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            String line;
            String develBuild = null;
            String stableBuild = null;
            while ((line = bin.readLine()) != null) {
                if (line.startsWith(".build"))
                    develBuild = line.substring(6).trim();
                else if (line.startsWith(".stablebuild"))
                    stableBuild = line.substring(12).trim();
            }
            bin.close();
            if (develBuild != null && stableBuild != null) {
                JavaCIPUnknownScope.doVersionCheck(view, stableBuild, develBuild);
            }
        } catch (IORuntimeException e) {
            String[] args = { JavaCIPUnknownScope.jEdit.getProperty("version-check.url"), e.toString() };
            GUIUtilities.error(view, "read-error", args);
        }
        view.hideWaitCursor();
    }
}
