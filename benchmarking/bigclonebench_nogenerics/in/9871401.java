


class c9871401 {

    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletRuntimeException, IORuntimeException {
        String pathInfo = httpServletRequest.getPathInfo();
        log.info("PathInfo: " + pathInfo);
        if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String fileName = pathInfo.charAt(0) == '/' ? pathInfo.substring(1) : pathInfo;
        log.info("FileName: " + fileName);
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getDataSource().getConnection();
            ps = con.prepareStatement("select file, size from files where name=?");
            ps.setString(1, fileName);
            rs = ps.executeQuery();
            if (rs.next()) {
                httpServletResponse.setContentType(getServletContext().getMimeType(fileName));
                httpServletResponse.setContentLength(rs.getInt("size"));
                OutputStream os = httpServletResponse.getOutputStream();
                org.apache.commons.io.IOUtils.copy(rs.getBinaryStream("file"), os);
                os.flush();
            } else {
                httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (SQLRuntimeException e) {
            throw new ServletRuntimeException(e);
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLRuntimeException e) {
            }
            if (ps != null) try {
                ps.close();
            } catch (SQLRuntimeException e) {
            }
            if (con != null) try {
                con.close();
            } catch (SQLRuntimeException e) {
            }
        }
    }

}
