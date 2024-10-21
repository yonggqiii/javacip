class c10113882 {

    private void createSaveServiceProps() throws MojoExecutionRuntimeException {
        JavaCIPUnknownScope.saveServiceProps = new File(JavaCIPUnknownScope.workDir, "saveservice.properties");
        try {
            FileWriter out = new FileWriter(JavaCIPUnknownScope.saveServiceProps);
            IOUtils.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream("saveservice.properties"), out);
            out.flush();
            out.close();
            System.setProperty("saveservice_properties", File.separator + "target" + File.separator + "jmeter" + File.separator + "saveservice.properties");
        } catch (IORuntimeException e) {
            throw new MojoExecutionRuntimeException("Could not create temporary saveservice.properties", e);
        }
    }
}
