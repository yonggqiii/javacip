class c13653107 {

    public static void loadConfig(DeviceEntry defaultDevice, EmulatorContext emulatorContext) {
        Config.defaultDevice = defaultDevice;
        Config.emulatorContext = emulatorContext;
        File configFile = new File(JavaCIPUnknownScope.getConfigPath(), "config2.xml");
        try {
            if (configFile.exists()) {
                JavaCIPUnknownScope.loadConfigFile("config2.xml");
            } else {
                configFile = new File(JavaCIPUnknownScope.getConfigPath(), "config.xml");
                if (configFile.exists()) {
                    JavaCIPUnknownScope.loadConfigFile("config.xml");
                    for (Enumeration e = JavaCIPUnknownScope.getDeviceEntries().elements(); e.hasMoreElements(); ) {
                        DeviceEntry entry = (DeviceEntry) e.nextElement();
                        if (!entry.canRemove()) {
                            continue;
                        }
                        JavaCIPUnknownScope.removeDeviceEntry(entry);
                        File src = new File(JavaCIPUnknownScope.getConfigPath(), entry.getFileName());
                        File dst = File.createTempFile("dev", ".jar", JavaCIPUnknownScope.getConfigPath());
                        IOUtils.copyFile(src, dst);
                        entry.setFileName(dst.getName());
                        JavaCIPUnknownScope.addDeviceEntry(entry);
                    }
                } else {
                    JavaCIPUnknownScope.createDefaultConfigXml();
                }
                JavaCIPUnknownScope.saveConfig();
            }
        } catch (IORuntimeException ex) {
            Logger.error(ex);
            JavaCIPUnknownScope.createDefaultConfigXml();
        } finally {
            if (JavaCIPUnknownScope.configXml == null) {
                JavaCIPUnknownScope.createDefaultConfigXml();
            }
        }
        JavaCIPUnknownScope.urlsMRU.read(JavaCIPUnknownScope.configXml.getChildOrNew("files").getChildOrNew("recent"));
        JavaCIPUnknownScope.initSystemProperties();
    }
}
