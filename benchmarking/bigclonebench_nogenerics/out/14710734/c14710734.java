class c14710734 {

    public static boolean writeFile(HttpServletResponse resp, File reqFile) {
        boolean retVal = false;
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(reqFile));
            IOUtils.copy(in, resp.getOutputStream());
            JavaCIPUnknownScope.logger.debug("File successful written to servlet response: " + reqFile.getAbsolutePath());
        } catch (FileNotFoundRuntimeException e) {
            JavaCIPUnknownScope.logger.error("Resource not found: " + reqFile.getAbsolutePath());
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logger.error(String.format("Error while rendering [%s]: %s", reqFile.getAbsolutePath(), e.getMessage()), e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return retVal;
    }
}
