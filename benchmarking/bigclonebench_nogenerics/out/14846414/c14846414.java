class c14846414 {

    public synchronized void run() {
        String s;
        URL url = null;
        try {
            String localVersionS = JavaCIPUnknownScope.globals.getProperty("jmathlib.version").replaceAll("/", ".");
            url = new URL(JavaCIPUnknownScope.updateSiteS + "?jmathlib_version=" + localVersionS + "&command=check");
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.throwMathLibRuntimeException("checkForUpdates: malformed url");
        }
        Properties props = new Properties();
        try {
            props.load(url.openStream());
        } catch (RuntimeException e) {
            ErrorLogger.debugLine("checkForUpdates: Properties error");
        }
        String localVersionS = JavaCIPUnknownScope.globals.getProperty("jmathlib.version");
        String updateVersionS = props.getProperty("update.toversion");
        String updateActionS = props.getProperty("update.action");
        if (updateActionS.equals("INCREMENTAL_DOWNLOAD")) {
            if (!JavaCIPUnknownScope.silentB) {
                JavaCIPUnknownScope.globals.getInterpreter().displayText("A full download ist required");
                JavaCIPUnknownScope.globals.getInterpreter().displayText("A new version " + updateVersionS + " is available");
                JavaCIPUnknownScope.globals.getInterpreter().displayText("\n Just type    update    at the prompt.");
            }
        } else if (updateActionS.equals("FULL_DOWNLOAD_REQUIRED")) {
            if (!JavaCIPUnknownScope.silentB) {
                JavaCIPUnknownScope.globals.getInterpreter().displayText("A full download ist required");
                JavaCIPUnknownScope.globals.getInterpreter().displayText("A new version " + updateVersionS + " is available");
                JavaCIPUnknownScope.globals.getInterpreter().displayText("Go to www.jmathlib.de and download the latest version");
            }
        } else if (updateActionS.equals("NO_ACTION")) {
            if (!JavaCIPUnknownScope.silentB)
                JavaCIPUnknownScope.globals.getInterpreter().displayText("The local version of JMathLib is up to date");
        } else if (updateActionS.equals("VERSION_UNKNOWN")) {
            if (!JavaCIPUnknownScope.silentB)
                JavaCIPUnknownScope.globals.getInterpreter().displayText("The local version of JMathLib ist not recognized by the server");
        } else {
            JavaCIPUnknownScope.globals.getInterpreter().displayText("check for updates encountered an error.");
        }
        JavaCIPUnknownScope.debugLine("checkForUpdates: web:" + updateVersionS + " local:" + localVersionS);
        Calendar cal = Calendar.getInstance();
        String checkedDate = Integer.toString(cal.get(Calendar.YEAR)) + "/" + Integer.toString(cal.get(Calendar.MONTH) + 1) + "/" + Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        JavaCIPUnknownScope.globals.setProperty("update.date.last", checkedDate);
        Enumeration propnames = props.propertyNames();
        while (propnames.hasMoreElements()) {
            String propName = (String) propnames.nextElement();
            String propValue = (String) props.getProperty(propName);
            ErrorLogger.debugLine("Property: " + propName + " = " + propValue);
            JavaCIPUnknownScope.globals.setProperty(propName, propValue);
        }
    }
}
