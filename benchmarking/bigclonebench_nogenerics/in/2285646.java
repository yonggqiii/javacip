


class c2285646 {

    protected void sendDoc(File indir, File outdir, File orig, Document doc, ServiceEndpoint ep) {
        ep.setMethod("simpleDocumentTransfer");
        Document response = null;
        try {
            response = protocolHandler.sendMessage(ep, doc);
        } catch (TransportRuntimeException e) {
            logger.warn("Message was not accepted, will try again later");
            return;
        }
        String serial = String.valueOf(System.currentTimeMillis());
        File origCopy = new File(outdir, orig.getName() + "." + serial);
        File respDrop = new File(outdir, orig.getName() + "." + serial + ".resp");
        FileOutputStream respos = null;
        try {
            respos = new FileOutputStream(respDrop);
            serializeDocument(respos, response);
        } catch (IORuntimeException e) {
            logger.warn("Failed to dump response");
            return;
        } finally {
            try {
                respos.close();
            } catch (IORuntimeException ignored) {
            }
        }
        FileInputStream in = null;
        FileOutputStream out = null;
        byte[] buffer = new byte[2048];
        try {
            in = new FileInputStream(orig);
            out = new FileOutputStream(origCopy);
            int bytesread = 0;
            while ((bytesread = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesread);
            }
        } catch (IORuntimeException e) {
            logger.warn("Failed to copy original");
            return;
        } finally {
            try {
                in.close();
                out.close();
            } catch (IORuntimeException ignored) {
            }
        }
        orig.delete();
        logger.info("File processed: " + orig.getName());
    }

}
