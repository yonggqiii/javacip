class c9791183 {

    private void handleFile(File file, HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        String filename = file.getName();
        long filesize = file.length();
        String mimeType = JavaCIPUnknownScope.getMimeType(filename);
        response.setContentType(mimeType);
        if (filesize > JavaCIPUnknownScope.getDownloadThreshhold()) {
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        }
        response.setContentLength((int) filesize);
        ServletOutputStream out = response.getOutputStream();
        IOUtils.copy(new FileInputStream(file), out);
        out.flush();
    }
}
