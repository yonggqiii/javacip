class c17533362 {

    private long getLastModified(Set resourcePaths, Map jarPaths) throws RuntimeException {
        long lastModified = 0;
        Iterator paths = resourcePaths.iterator();
        while (paths.hasNext()) {
            String path = (String) paths.next();
            URL url = JavaCIPUnknownScope.context.getServletContext().getResource(path);
            if (url == null) {
                JavaCIPUnknownScope.log.debug("Null url " + path);
                break;
            }
            long lastM = url.openConnection().getLastModified();
            if (lastM > lastModified)
                lastModified = lastM;
            if (JavaCIPUnknownScope.log.isDebugEnabled()) {
                JavaCIPUnknownScope.log.debug("Last modified " + path + " " + lastM);
            }
        }
        if (jarPaths != null) {
            paths = jarPaths.values().iterator();
            while (paths.hasNext()) {
                File jarFile = (File) paths.next();
                long lastM = jarFile.lastModified();
                if (lastM > lastModified)
                    lastModified = lastM;
                if (JavaCIPUnknownScope.log.isDebugEnabled()) {
                    JavaCIPUnknownScope.log.debug("Last modified " + jarFile.getAbsolutePath() + " " + lastM);
                }
            }
        }
        return lastModified;
    }
}
