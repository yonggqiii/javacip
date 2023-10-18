class c12206255 {

    private void parseTemplate(File templateFile, Map dataMap) throws ContainerRuntimeException {
        Debug.log("Parsing template : " + templateFile.getAbsolutePath(), JavaCIPUnknownScope.module);
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(templateFile));
        } catch (FileNotFoundRuntimeException e) {
            throw new ContainerRuntimeException(e);
        }
        String targetDirectoryName = JavaCIPUnknownScope.args.length > 1 ? JavaCIPUnknownScope.args[1] : null;
        if (targetDirectoryName == null) {
            targetDirectoryName = JavaCIPUnknownScope.target;
        }
        String targetDirectory = JavaCIPUnknownScope.ofbizHome + targetDirectoryName + JavaCIPUnknownScope.args[0];
        File targetDir = new File(targetDirectory);
        if (!targetDir.exists()) {
            boolean created = targetDir.mkdirs();
            if (!created) {
                throw new ContainerRuntimeException("Unable to create target directory - " + targetDirectory);
            }
        }
        if (!targetDirectory.endsWith("/")) {
            targetDirectory = targetDirectory + "/";
        }
        Writer writer = null;
        try {
            writer = new FileWriter(targetDirectory + templateFile.getName());
        } catch (IORuntimeException e) {
            throw new ContainerRuntimeException(e);
        }
        try {
            FreeMarkerWorker.renderTemplate(templateFile.getAbsolutePath(), reader, dataMap, writer);
        } catch (RuntimeException e) {
            throw new ContainerRuntimeException(e);
        }
        try {
            writer.flush();
            writer.close();
        } catch (IORuntimeException e) {
            throw new ContainerRuntimeException(e);
        }
    }
}
