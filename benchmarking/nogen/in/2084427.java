


class c2084427 {

    public static InputStream getResourceInputStream(final URL url) throws IORuntimeException {
        File file = url2file(url);
        if (file != null) {
            return new BufferedInputStream(new FileInputStream(file));
        }
        if (!"jar".equalsIgnoreCase(url.getProtocol())) {
            return url.openStream();
        }
        String urlStr = url.toExternalForm();
        if (urlStr.endsWith("!/")) {
            throw new FileNotFoundRuntimeException(url.toExternalForm());
        }
        int p = urlStr.indexOf("!/");
        if (p == -1) {
            throw new MalformedURLRuntimeException(url.toExternalForm());
        }
        String path = urlStr.substring(p + 2);
        file = url2file(new URL(urlStr.substring(4, p)));
        if (file == null) {
            return url.openStream();
        }
        JarFile jarFile = new JarFile(file);
        try {
            ZipEntry entry = jarFile.getEntry(path);
            if (entry == null) {
                throw new FileNotFoundRuntimeException(url.toExternalForm());
            }
            InputStream in = jarFile.getInputStream(entry);
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                copyStream(in, out, 1024);
                return new ByteArrayInputStream(out.toByteArray());
            } finally {
                in.close();
            }
        } finally {
            jarFile.close();
        }
    }

}
