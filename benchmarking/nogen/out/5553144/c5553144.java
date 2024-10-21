class c5553144 {

    private void initStreams() throws IORuntimeException {
        if (JavaCIPUnknownScope.audio != null) {
            JavaCIPUnknownScope.audio.close();
        }
        if (JavaCIPUnknownScope.url != null) {
            JavaCIPUnknownScope.audio = new OggInputStream(JavaCIPUnknownScope.url.openStream());
        } else {
            JavaCIPUnknownScope.audio = new OggInputStream(ResourceLoader.getResourceAsStream(JavaCIPUnknownScope.ref));
        }
    }
}
