class c9209383 {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletRuntimeException, IORuntimeException {
        final Map<String, String> fileAttr = new HashMap<String, String>();
        boolean download = false;
        String dw = req.getParameter("d");
        if (StringUtils.isNotEmpty(dw) && StringUtils.equals(dw, "true")) {
            download = true;
        }
        final ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream(JavaCIPUnknownScope.DEFAULT_CONTENT_LENGTH_SIZE);
        InputStream imageInputStream = null;
        try {
            imageInputStream = JavaCIPUnknownScope.getImageAsStream(req, fileAttr);
            IOUtils.copy(imageInputStream, imageOutputStream);
            resp.setHeader("Cache-Control", "no-store");
            resp.setHeader("Pragma", "no-cache");
            resp.setDateHeader("Expires", 0);
            resp.setContentType(fileAttr.get("mimetype"));
            if (download) {
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileAttr.get("filename") + "\"");
            }
            final ServletOutputStream responseOutputStream = resp.getOutputStream();
            responseOutputStream.write(imageOutputStream.toByteArray());
            responseOutputStream.flush();
            responseOutputStream.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
            resp.setContentType("text/html");
            resp.getWriter().println("<h1>Sorry... cannot find document</h1>");
        } finally {
            IOUtils.closeQuietly(imageInputStream);
            IOUtils.closeQuietly(imageOutputStream);
        }
    }
}
