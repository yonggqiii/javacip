class c4895903 {

    public List load(String sourceType, Object source, String param) throws RuntimeException {
        if (JavaCIPUnknownScope.log.isTraceEnabled()) {
            JavaCIPUnknownScope.log.trace("load " + source);
        }
        String location = null;
        String type = null;
        Object inputsource = null;
        if (source instanceof DynamicMBean) {
            sourceType = "MbeansDescriptorsDynamicMBeanSource";
            inputsource = source;
        } else if (source instanceof URL) {
            URL url = (URL) source;
            location = url.toString();
            type = param;
            inputsource = url.openStream();
            if (sourceType == null) {
                sourceType = JavaCIPUnknownScope.sourceTypeFromExt(location);
            }
        } else if (source instanceof File) {
            location = ((File) source).getAbsolutePath();
            inputsource = new FileInputStream((File) source);
            type = param;
            if (sourceType == null) {
                sourceType = JavaCIPUnknownScope.sourceTypeFromExt(location);
            }
        } else if (source instanceof InputStream) {
            type = param;
            inputsource = source;
        } else if (source instanceof Class) {
            location = ((Class) source).getName();
            type = param;
            inputsource = source;
            if (sourceType == null) {
                sourceType = "MbeansDescriptorsIntrospectionSource";
            }
        }
        if (sourceType == null) {
            sourceType = "MbeansDescriptorsDigesterSource";
        }
        ModelerSource ds = JavaCIPUnknownScope.getModelerSource(sourceType);
        List mbeans = ds.loadDescriptors(this, location, type, inputsource);
        return mbeans;
    }
}
