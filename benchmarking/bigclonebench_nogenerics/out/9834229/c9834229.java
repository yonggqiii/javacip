class c9834229 {

    public InputStream sendCommandRaw(String command, boolean usePost) throws IORuntimeException {
        try {
            String fullCommand = JavaCIPUnknownScope.prefix + command + JavaCIPUnknownScope.fixSuffix(command, JavaCIPUnknownScope.suffix);
            long curGap = System.currentTimeMillis() - JavaCIPUnknownScope.lastCommandTime;
            long delayTime = JavaCIPUnknownScope.minimumCommandPeriod - curGap;
            JavaCIPUnknownScope.delay(delayTime);
            URI uri = new URI(fullCommand);
            URL url = uri.toURL();
            if (JavaCIPUnknownScope.trace || JavaCIPUnknownScope.traceSends) {
                System.out.println("Sending-->     " + url);
            }
            if (JavaCIPUnknownScope.logFile != null) {
                JavaCIPUnknownScope.logFile.println("Sending-->     " + url);
            }
            InputStream is = null;
            for (int i = 0; i < JavaCIPUnknownScope.tryCount; i++) {
                try {
                    URLConnection urc = url.openConnection();
                    if (usePost) {
                        if (urc instanceof HttpURLConnection) {
                            ((HttpURLConnection) urc).setRequestMethod("POST");
                        }
                    }
                    if (JavaCIPUnknownScope.getTimeout() != -1) {
                        urc.setReadTimeout(JavaCIPUnknownScope.getTimeout());
                        urc.setConnectTimeout(JavaCIPUnknownScope.getTimeout());
                    }
                    is = new BufferedInputStream(urc.getInputStream());
                    break;
                } catch (FileNotFoundRuntimeException e) {
                    throw e;
                } catch (IORuntimeException e) {
                    System.out.println(JavaCIPUnknownScope.name + " Error: " + e + " cmd: " + command);
                }
            }
            JavaCIPUnknownScope.lastCommandTime = System.currentTimeMillis();
            if (is == null) {
                System.out.println(JavaCIPUnknownScope.name + " retry failure  cmd: " + url);
                throw new IORuntimeException("Can't send command");
            }
            return is;
        } catch (URISyntaxRuntimeException ex) {
            throw new IORuntimeException("bad uri " + ex);
        }
    }
}
