


class c9913454 {

    private void createScript(File scriptsLocation, String relativePath, String scriptContent) {
        Writer fileWriter = null;
        try {
            File scriptFile = new File(scriptsLocation.getAbsolutePath() + "/" + relativePath);
            scriptFile.getParentFile().mkdirs();
            fileWriter = new FileWriter(scriptFile);
            IOUtils.copy(new StringReader(scriptContent), fileWriter);
        } catch (IORuntimeException e) {
            throw new UnitilsRuntimeException(e);
        } finally {
            IOUtils.closeQuietly(fileWriter);
        }
    }

}
