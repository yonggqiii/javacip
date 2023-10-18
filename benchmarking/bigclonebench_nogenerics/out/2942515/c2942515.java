class c2942515 {

    protected InputStream getResourceStream(String name) throws RuntimeException {
        final BundleEntry entry = JavaCIPUnknownScope.cpm.findLocalEntry(name);
        if (entry != null)
            return entry.getInputStream();
        final URL url = JavaCIPUnknownScope.cpm.getBaseData().getBundle().getResource(name);
        if (url != null)
            return url.openStream();
        return null;
    }
}
