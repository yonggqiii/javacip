class c2451974 {

    public void doHandler(HttpServletRequest request, HttpServletResponse response) throws IORuntimeException, ServletRuntimeException {
        if (request.getRequestURI().indexOf("png") != -1) {
            response.setContentType("image/png");
        } else if (request.getRequestURI().indexOf("gif") != -1) {
            response.setContentType("image/gif");
        } else {
            response.setContentType("image/x-icon");
        }
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        try {
            URL url = new URL("http://" + JavaCIPUnknownScope.configCenter.getUcoolOnlineIp() + request.getRequestURI());
            BufferedInputStream in = new BufferedInputStream(url.openStream());
            byte[] data = new byte[4096];
            int size = in.read(data);
            while (size != -1) {
                bos.write(data, 0, size);
                size = in.read(data);
            }
            in.close();
            bos.flush();
            bos.close();
            in.close();
        } catch (RuntimeException e) {
        }
        bos.flush();
    }
}
