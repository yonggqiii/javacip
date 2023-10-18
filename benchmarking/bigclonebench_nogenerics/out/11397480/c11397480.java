class c11397480 {

    public void render(Map map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws RuntimeException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(JavaCIPUnknownScope.OUTPUT_BYTE_ARRAY_INITIAL_SIZE);
        File file = (File) map.get("targetFile");
        IOUtils.copy(new FileInputStream(file), baos);
        httpServletResponse.setContentType(JavaCIPUnknownScope.getContentType());
        httpServletResponse.setContentLength(baos.size());
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + file.getName());
        ServletOutputStream out = httpServletResponse.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }
}
