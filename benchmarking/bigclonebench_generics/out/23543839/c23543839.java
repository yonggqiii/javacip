class c23543839 {

    protected synchronized Class findClass(String className) {
        JavaCIPUnknownScope.LOG.info("FIND class:" + className);
        String urlName = className.replace('.', '/');
        byte[] buf;
        Class currentClass;
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            int i = className.lastIndexOf('.');
            if (i >= 0)
                sm.checkPackageDefinition(className.substring(0, i));
        }
        buf = JavaCIPUnknownScope.cache.get(urlName);
        if (buf != null) {
            JavaCIPUnknownScope.LOG.info("Get class from cache:" + className);
            currentClass = JavaCIPUnknownScope.defineClass(className, buf, 0, buf.length, (CodeSource) null);
            return currentClass;
        }
        try {
            URL url = new URL(JavaCIPUnknownScope.urlBase, urlName + ".class");
            JavaCIPUnknownScope.LOG.info("Loading " + url);
            InputStream is = url.openConnection().getInputStream();
            buf = JavaCIPUnknownScope.getClassBytes(is);
            currentClass = JavaCIPUnknownScope.defineClass(className, buf, 0, buf.length, (CodeSource) null);
            return currentClass;
        } catch (MalformedURLRuntimeException mE) {
            JavaCIPUnknownScope.LOG.warn("Bad url detected", mE);
            return null;
        } catch (IORuntimeException e) {
            buf = JavaCIPUnknownScope.downloadClass(className);
            if (buf != null) {
                return JavaCIPUnknownScope.defineClass(className, buf, 0, buf.length);
            } else {
                JavaCIPUnknownScope.LOG.warn("no class found: " + className);
                return null;
            }
        }
    }
}
