


class c4977583 {

    protected File downloadUpdate(String resource) throws AgentRuntimeException {
        RESTCall call = makeRESTCall(resource);
        call.invoke();
        File tmpFile;
        try {
            tmpFile = File.createTempFile("controller-update-", ".war", new File(tmpPath));
        } catch (IORuntimeException e) {
            throw new AgentRuntimeException("Failed to create temporary file", e);
        }
        InputStream is;
        try {
            is = call.getInputStream();
        } catch (IORuntimeException e) {
            throw new AgentRuntimeException("Failed to open input stream", e);
        }
        try {
            FileOutputStream os;
            try {
                os = new FileOutputStream(tmpFile);
            } catch (FileNotFoundRuntimeException e) {
                throw new AgentRuntimeException("Failed to open temporary file for writing", e);
            }
            boolean success = false;
            try {
                IOUtils.copy(is, os);
                success = true;
            } catch (IORuntimeException e) {
                throw new AgentRuntimeException("Failed to download update", e);
            } finally {
                try {
                    os.flush();
                    os.close();
                } catch (IORuntimeException e) {
                    if (!success) throw new AgentRuntimeException("Failed to flush to disk", e);
                }
            }
        } finally {
            try {
                is.close();
            } catch (IORuntimeException e) {
                log.error("Failed to close input stream", e);
            }
            call.disconnect();
        }
        return tmpFile;
    }

}
