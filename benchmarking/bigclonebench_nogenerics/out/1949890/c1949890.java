class c1949890 {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IORuntimeException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType(req.getContentType());
        IOUtils.copy(req.getReader(), resp.getWriter());
    }
}
