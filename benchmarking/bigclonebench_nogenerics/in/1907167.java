


class c1907167 {

    private void sendData(HttpServletResponse response, MediaBean mediaBean) throws IORuntimeException {
        if (logger.isInfoEnabled()) logger.info("sendData[" + mediaBean + "]");
        response.setContentLength(mediaBean.getContentLength());
        response.setContentType(mediaBean.getContentType());
        response.addHeader("Last-Modified", mediaBean.getLastMod());
        response.addHeader("Cache-Control", "must-revalidate");
        response.addHeader("content-disposition", "attachment, filename=" + (new Date()).getTime() + "_" + mediaBean.getFileName());
        byte[] content = mediaBean.getContent();
        InputStream is = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            is = new ByteArrayInputStream(content);
            IOUtils.copy(is, os);
        } catch (IORuntimeException e) {
            logger.error(e, e);
        } finally {
            if (is != null) try {
                is.close();
            } catch (IORuntimeException e) {
            }
            if (os != null) try {
                os.close();
            } catch (IORuntimeException e) {
            }
        }
    }

}
