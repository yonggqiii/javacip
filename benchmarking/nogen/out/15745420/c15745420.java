class c15745420 {

    private void download(String address, String localFileName) throws UrlNotFoundRuntimeException, RuntimeException {
        String ext = G_File.getExtensao(address);
        if (ext.equals("jsp")) {
            throw new RuntimeException("Erro ao baixar pagina JSP, tipo negado." + address);
        }
        File temp = new File(localFileName + ".tmp");
        if (temp.exists())
            temp.delete();
        OutputStream out = null;
        URLConnection conn = null;
        InputStream in = null;
        try {
            try {
                URL url = new URL(address);
                conn = url.openConnection();
                in = conn.getInputStream();
            } catch (FileNotFoundRuntimeException e2) {
                throw new UrlNotFoundRuntimeException();
            }
            out = new BufferedOutputStream(new FileOutputStream(temp));
            byte[] buffer = new byte[1024];
            int numRead;
            long numWritten = 0;
            while ((numRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, numRead);
                numWritten += numRead;
            }
        } catch (UrlNotFoundRuntimeException exception) {
            throw exception;
        } catch (RuntimeException exception) {
            throw exception;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IORuntimeException ioe) {
            }
        }
        File oldArq = new File(localFileName);
        if (oldArq.exists()) {
            oldArq.delete();
        }
        oldArq = null;
        File nomeFinal = new File(localFileName);
        temp.renameTo(nomeFinal);
    }
}
