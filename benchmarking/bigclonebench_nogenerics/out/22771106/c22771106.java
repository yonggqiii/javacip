class c22771106 {

    private void triggerBuild(Properties props, String project, int rev) throws IORuntimeException {
        boolean doBld = Boolean.parseBoolean(props.getProperty(project + ".bld"));
        String url = props.getProperty(project + ".url");
        if (!doBld || project == null || project.length() == 0) {
            System.out.println("BuildLauncher: Not configured to build '" + project + "'");
            return;
        } else if (url == null) {
            throw new IORuntimeException("Tried to launch build for project '" + project + "' but " + project + ".url property is not defined!");
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        System.out.println(fmt.format(new Date()) + ": Triggering a build via: " + url);
        BufferedReader r = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        while (r.readLine() != null) ;
        System.out.println(fmt.format(new Date()) + ": Build triggered!");
        JavaCIPUnknownScope.LATEST_BUILD.put(project, rev);
        r.close();
        System.out.println(fmt.format(new Date()) + ": triggerBuild() done!");
    }
}
