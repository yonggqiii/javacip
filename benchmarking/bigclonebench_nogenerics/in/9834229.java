


class c9834229 {

    public InputStream sendCommandRaw(String command, boolean usePost) throws IORuntimeException {
        try {
            String fullCommand = prefix + command + fixSuffix(command, suffix);
            long curGap = System.currentTimeMillis() - lastCommandTime;
            long delayTime = minimumCommandPeriod - curGap;
            delay(delayTime);
            URI uri = new URI(fullCommand);
            URL url = uri.toURL();
            if (trace || traceSends) {
                System.out.println("Sending-->     " + url);
            }
            if (logFile != null) {
                logFile.println("Sending-->     " + url);
            }
            InputStream is = null;
            for (int i = 0; i < tryCount; i++) {
                try {
                    URLConnection urc = url.openConnection();
                    if (usePost) {
                        if (urc instanceof HttpURLConnection) {
                            ((HttpURLConnection) urc).setRequestMethod("POST");
                        }
                    }
                    if (getTimeout() != -1) {
                        urc.setReadTimeout(getTimeout());
                        urc.setConnectTimeout(getTimeout());
                    }
                    is = new BufferedInputStream(urc.getInputStream());
                    break;
                } catch (FileNotFoundRuntimeException e) {
                    throw e;
                } catch (IORuntimeException e) {
                    System.out.println(name + " Error: " + e + " cmd: " + command);
                }
            }
            lastCommandTime = System.currentTimeMillis();
            if (is == null) {
                System.out.println(name + " retry failure  cmd: " + url);
                throw new IORuntimeException("Can't send command");
            }
            return is;
        } catch (URISyntaxRuntimeException ex) {
            throw new IORuntimeException("bad uri " + ex);
        }
    }

}
