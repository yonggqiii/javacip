class c4303376 {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletRuntimeException, IORuntimeException {
        String[] path = StringUtils.split(request.getRequestURI(), "/");
        String file = path[path.length - 1];
        File f = new File(JavaCIPUnknownScope.pathToImages + "/" + file);
        response.setContentType(JavaCIPUnknownScope.getServletContext().getMimeType(f.getName()));
        FileInputStream fis = new FileInputStream(f);
        IOUtils.copy(fis, response.getOutputStream());
        fis.close();
    }
}
