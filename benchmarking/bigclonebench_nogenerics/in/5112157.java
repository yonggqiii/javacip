


class c5112157 {

    public File mergeDoc(URL urlDoc, File fOutput, boolean bMulti) throws RuntimeException {
        if (s_log.isTraceEnabled()) trace(0, "Copying from " + urlDoc.toString() + " to " + fOutput.toString());
        File fOut = null;
        InputStream is = null;
        try {
            is = urlDoc.openStream();
            fOut = mergeDoc(is, fOutput, bMulti);
        } finally {
            is.close();
        }
        return fOut;
    }

}
