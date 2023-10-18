class c991473 {

    private void doUpdate(final boolean notifyOnChange) {
        if (!JavaCIPUnknownScope.validServerUrl) {
            return;
        }
        boolean tempBuildClean = true;
        List failedBuilds = new ArrayList();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(JavaCIPUnknownScope.url.openStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                Matcher matcher = JavaCIPUnknownScope.ROW_PARSER_PATTERN.matcher(line);
                if (matcher.matches() && JavaCIPUnknownScope.checkAllProjects) {
                    String project = matcher.group(JavaCIPUnknownScope.GROUP_PROJECT);
                    String status = matcher.group(JavaCIPUnknownScope.GROUP_STATUS);
                    if (status.equals(MessageUtils.getString("ccOutput.status.failed"))) {
                        tempBuildClean = false;
                        failedBuilds.add(project);
                    }
                }
            }
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.serverReachable = false;
            if (notifyOnChange) {
                JavaCIPUnknownScope.monitor.notifyServerUnreachable(MessageUtils.getString("error.readError", new String[] { JavaCIPUnknownScope.url.toString() }));
            }
            return;
        }
        if (notifyOnChange && JavaCIPUnknownScope.buildClean && !tempBuildClean) {
            JavaCIPUnknownScope.monitor.notifyBuildFailure(MessageUtils.getString("message.buildFailed", new Object[] { failedBuilds.get(0) }));
        }
        if (notifyOnChange && !JavaCIPUnknownScope.buildClean && tempBuildClean) {
            JavaCIPUnknownScope.monitor.notifyBuildFixed(MessageUtils.getString("message.allBuildsClean"));
        }
        JavaCIPUnknownScope.buildClean = tempBuildClean;
        JavaCIPUnknownScope.monitor.setStatus(JavaCIPUnknownScope.buildClean);
        JavaCIPUnknownScope.serverReachable = true;
    }
}
