


class c17136338 {

    protected void checkWeavingJar() throws IORuntimeException {
        OutputStream out = null;
        try {
            final File weaving = new File(getWeavingPath());
            if (!weaving.exists()) {
                new File(getWeavingFolder()).mkdir();
                weaving.createNewFile();
                final Path src = new Path("weaving/openfrwk-weaving.jar");
                final InputStream in = FileLocator.openStream(getBundle(), src, false);
                out = new FileOutputStream(getWeavingPath(), true);
                IOUtils.copy(in, out);
                Logger.log(Logger.INFO, "Put weaving jar at location " + weaving);
            } else {
                Logger.getLog().info("File openfrwk-weaving.jar already exists at " + weaving);
            }
        } catch (final SecurityRuntimeException e) {
            Logger.log(Logger.ERROR, "[SECURITY EXCEPTION] Not enough privilegies to create " + "folder and copy NexOpen weaving jar at location " + getWeavingFolder());
            Logger.logRuntimeException(e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

}
