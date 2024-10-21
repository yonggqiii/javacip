class c7169018 {

    private void show(String fileName, HttpServletResponse response) throws IORuntimeException {
        TelnetInputStream ftpIn = JavaCIPUnknownScope.ftpClient_sun.get(fileName);
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
