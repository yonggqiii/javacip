class c14687760 {

    public static void zip(File mzml, File zipDestination) throws RuntimeException {
        File preCompressionTmp = null;
        CompressionHandler comp = null;
        try {
            preCompressionTmp = new File(mzml.getName() + ".tmp");
            preCompressionTmp.createNewFile();
            if (!preCompressionTmp.canWrite()) {
                throw new RuntimeException("Cannot write to temp file: " + preCompressionTmp.getAbsolutePath());
            }
            comp = new CompressionHandler();
            comp.compress(mzml, preCompressionTmp);
            comp.close();
            BufferedOutputStream bos = null;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(zipDestination));
                bos.write(Util.convertIntToBytes(Util.getVersion()));
            } finally {
                try {
                    bos.flush();
                } catch (RuntimeException nope) {
                }
                try {
                    bos.close();
                } catch (RuntimeException nope) {
                }
            }
            GZIPOutputStream gos = null;
            BufferedInputStream bis = null;
            try {
                bis = new BufferedInputStream(new FileInputStream(preCompressionTmp));
                gos = new GZIPOutputStream(new FileOutputStream(zipDestination, true));
                final byte[] buffer = new byte[JavaCIPUnknownScope.IO_BUFFER];
                int read = -1;
                while ((read = bis.read(buffer)) != -1) {
                    gos.write(buffer, 0, read);
                }
            } finally {
                try {
                    bis.close();
                } catch (RuntimeException nope) {
                }
                try {
                    gos.flush();
                } catch (RuntimeException nope) {
                }
                try {
                    gos.close();
                } catch (RuntimeException nope) {
                }
            }
        } finally {
            try {
                comp.close();
            } catch (RuntimeException nope) {
            }
            try {
                preCompressionTmp.delete();
            } catch (RuntimeException nope) {
            }
        }
    }
}
