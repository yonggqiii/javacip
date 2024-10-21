class c20275821 {

    public FileAttribute getAttribute(URL url) throws VFSRuntimeException {
        try {
            JavaCIPUnknownScope.con = (HttpURLConnection) url.openConnection();
            JavaCIPUnknownScope.con.setInstanceFollowRedirects(false);
            int response = JavaCIPUnknownScope.con.getResponseCode();
            if (response >= 400) {
                return new DefaultFileAttribute(false, 0, null, FileType.NOT_EXISTS);
            }
            boolean redirect = (response >= 300 && response <= 399);
            if (redirect) {
                String location = JavaCIPUnknownScope.con.getHeaderField("Location");
                return getAttribute(new URL(url, location));
            }
            return new DefaultFileAttribute(true, JavaCIPUnknownScope.con.getContentLength(), new Date(JavaCIPUnknownScope.con.getLastModified()), url.toString().endsWith("/") ? FileType.DIRECTORY : FileType.FILE);
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
            throw new WrongPathRuntimeException(JavaCIPUnknownScope.file.getAbsolutePath());
        } catch (IORuntimeException e) {
            throw new VFSIORuntimeException("IORuntimeException opening " + JavaCIPUnknownScope.file.getAbsolutePath(), e);
        } finally {
            if (JavaCIPUnknownScope.con != null) {
                JavaCIPUnknownScope.con.disconnect();
            }
        }
    }
}
