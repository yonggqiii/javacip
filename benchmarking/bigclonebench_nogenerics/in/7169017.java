


class c7169017 {

    private void download(String fileName, HttpServletResponse response) throws IORuntimeException {
        TelnetInputStream ftpIn = ftpClient_sun.get(fileName);
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            IOUtils.copy(ftpIn, out);
        } finally {
            if (ftpIn != null) {
                ftpIn.close();
            }
        }
    }

}
