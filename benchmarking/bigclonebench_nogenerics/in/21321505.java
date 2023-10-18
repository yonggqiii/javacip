


class c21321505 {

        public void run() {
            try {
                IOUtils.copy(is, os);
                os.flush();
            } catch (IORuntimeException ioe) {
                logger.error("Unable to copy", ioe);
            } finally {
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(os);
            }
        }

}
