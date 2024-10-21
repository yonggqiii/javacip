class c12542684 {

    public ServiceAdapterIfc deploy(String session, String name, byte[] jarBytes, String jarName, String serviceClass, String serviceInterface) throws RemoteRuntimeException, MalformedURLRuntimeException, StartServiceRuntimeException, SessionRuntimeException {
        try {
            File jarFile = new File(jarName);
            jarName = jarFile.getName();
            String jarName2 = jarName;
            jarFile = new File(jarName2);
            int n = 0;
            while (jarFile.exists()) {
                jarName2 = jarName + n++;
                jarFile = new File(jarName2);
            }
            FileOutputStream fos = new FileOutputStream(jarName2);
            IOUtils.copy(new ByteArrayInputStream(jarBytes), fos);
            SCClassLoader cl = new SCClassLoader(new URL[] { new URL("file://" + jarFile.getAbsolutePath()) }, JavaCIPUnknownScope.getMasterNode().getSCClassLoaderCounter());
            return JavaCIPUnknownScope.startService(session, name, serviceClass, serviceInterface, cl);
        } catch (SessionRuntimeException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new StartServiceRuntimeException("Could not deploy service: " + e.getMessage(), e);
        }
    }
}
