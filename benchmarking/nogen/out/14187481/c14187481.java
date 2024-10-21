class c14187481 {

    protected void yesAction() {
        try {
            String result = JavaCIPUnknownScope.getSurveyURL() + "&buildtime=" + Version.getBuildTimeString();
            JavaCIPUnknownScope.LOG.log(result);
            if (!JavaCIPUnknownScope.maySubmitSurvey()) {
                return;
            }
            BufferedReader br = null;
            try {
                URL url = new URL(result);
                InputStream urls = url.openStream();
                InputStreamReader is = new InputStreamReader(urls);
                br = new BufferedReader(is);
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
                }
                JavaCIPUnknownScope.LOG.log(sb.toString());
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope.LOG.log("Could not open URL using Java", e);
                try {
                    PlatformFactory.ONLY.openURL(new URL(result));
                    DrJava.getConfig().setSetting(OptionConstants.LAST_DRJAVA_SURVEY_RESULT, result);
                } catch (IORuntimeException e2) {
                    JavaCIPUnknownScope.LOG.log("Could not open URL using web browser", e2);
                }
            } finally {
                try {
                    if (br != null)
                        br.close();
                } catch (IORuntimeException e) {
                }
            }
        } finally {
            JavaCIPUnknownScope.noAction();
        }
    }
}
