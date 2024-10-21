class c17136338 {

    protected void checkWeavingJar() throws IORuntimeException {
        OutputStream out = null;
        try {
            final File weaving = new File(JavaCIPUnknownScope.getWeavingPath());
            if (!weaving.exists()) {
                new File(JavaCIPUnknownScope.getWeavingFolder()).mkdir();
                weaving.createNewFile();
                final Path src = new Path("weaving/openfrwk-weaving.jar");
                final InputStream in = FileLocator.openStream(JavaCIPUnknownScope.getBundle(), src, false);
                out = new FileOutputStream(JavaCIPUnknownScope.getWeavingPath(), true);
                IOUtils.copy(in, out);
                Logger.log(Logger.INFO, "Put weaving jar at location " + weaving);
            } else {
                Logger.getLog().info("File openfrwk-weaving.jar already exists at " + weaving);
            }
        } catch (final SecurityRuntimeException e) {
            Logger.log(Logger.ERROR, "[SECURITY EXCEPTION] Not enough privilegies to create " + "folder and copy NexOpen weaving jar at location " + JavaCIPUnknownScope.getWeavingFolder());
            Logger.logRuntimeException(e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
