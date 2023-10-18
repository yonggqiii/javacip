class c13657103 {

    private void forBundle(BundleManipulator manip) {
        ByteArrayOutputStream bout = null;
        try {
            bout = new ByteArrayOutputStream();
            ZipOutputStream zout = new ZipOutputStream(bout);
            Bundle bundle = JavaCIPUnknownScope.getBundle();
            Enumeration<URL> files = bundle.findEntries("/", "*.vm", false);
            if (files != null) {
                while (files.hasMoreElements()) {
                    URL url = files.nextElement();
                    String name = url.getFile();
                    if (name.startsWith("/")) {
                        name = name.substring(1);
                    }
                    if (manip.includeEntry(name)) {
                        zout.putNextEntry(new ZipEntry(name));
                        IOUtils.copy(url.openStream(), zout);
                    }
                }
            }
            manip.finish(bundle, zout);
            Manifest mf = new Manifest(bundle.getEntry("META-INF/MANIFEST.MF").openStream());
            zout.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));
            mf.write(zout);
            zout.close();
            File tmpFile = File.createTempFile(JavaCIPUnknownScope.TEMPLATES_SYMBOLIC_NAME, ".jar");
            FileUtils.writeByteArrayToFile(tmpFile, bout.toByteArray());
            if (JavaCIPUnknownScope.pluginAccessor.getPlugin(JavaCIPUnknownScope.TEMPLATES_SYMBOLIC_NAME) != null) {
                JavaCIPUnknownScope.pluginController.uninstall(JavaCIPUnknownScope.pluginAccessor.getPlugin(JavaCIPUnknownScope.TEMPLATES_SYMBOLIC_NAME));
            } else if (JavaCIPUnknownScope.pluginAccessor.getPlugin(JavaCIPUnknownScope.TEMPLATES_PLUGIN_KEY) != null) {
                JavaCIPUnknownScope.pluginController.uninstall(JavaCIPUnknownScope.pluginAccessor.getPlugin(JavaCIPUnknownScope.TEMPLATES_PLUGIN_KEY));
            }
            JavaCIPUnknownScope.pluginController.installPlugin(new JarPluginArtifact(tmpFile));
            ServiceReference ref = JavaCIPUnknownScope.bundleContext.getServiceReference(PackageAdmin.class.getName());
            ((PackageAdmin) JavaCIPUnknownScope.bundleContext.getService(ref)).refreshPackages(null);
            tmpFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bout);
        }
    }
}
