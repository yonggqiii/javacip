class c10798829 {

    private boolean handlePart(Part p) throws MessagingRuntimeException, GetterRuntimeException {
        String filename = p.getFileName();
        if (!p.isMimeType("multipart/*")) {
            String disp = p.getDisposition();
            if (disp == null || disp.equalsIgnoreCase(Part.ATTACHMENT)) {
                if (JavaCIPUnknownScope.checkCriteria(p)) {
                    if (filename == null)
                        filename = "Attachment" + JavaCIPUnknownScope.attnum++;
                    if (JavaCIPUnknownScope.result == null) {
                        try {
                            File f = File.createTempFile("amorph_pop3-", ".tmp");
                            f.deleteOnExit();
                            OutputStream os = new BufferedOutputStream(new FileOutputStream(f));
                            InputStream is = p.getInputStream();
                            int c;
                            while ((c = is.read()) != -1) os.write(c);
                            os.close();
                            JavaCIPUnknownScope.result = new FileInputStream(f);
                            System.out.println("saved attachment to file: " + f.getAbsolutePath());
                            return true;
                        } catch (IORuntimeException ex) {
                            throw new GetterRuntimeException(ex, "Failed to save attachment: " + ex);
                        }
                    }
                }
            }
        }
        return false;
    }
}
