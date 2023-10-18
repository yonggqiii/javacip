class c3180675 {

    public void copyDependancyFiles() {
        for (String[] depStrings : JavaCIPUnknownScope.getDependancyFiles()) {
            String source = depStrings[0];
            String target = depStrings[1];
            try {
                File sourceFile = PluginManager.getFile(source);
                IOUtils.copyEverything(sourceFile, new File(JavaCIPUnknownScope.WEB_ROOT + target));
            } catch (URISyntaxRuntimeException e) {
                e.printStackTrace();
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
