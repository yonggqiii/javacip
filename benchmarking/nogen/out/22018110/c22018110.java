class c22018110 {

    private CachedQuery loadQuery(String path) throws CacheRuntimeException, IORuntimeException, XQueryRuntimeException {
        final URL url;
        final long lastModified;
        final InputStream is;
        try {
            url = JavaCIPUnknownScope.getServletContext().getResource(path);
            assert (url != null);
            lastModified = url.openConnection().getLastModified();
            is = url.openStream();
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.log(PrintUtils.prettyPrintStackTrace(e, -1));
            throw e;
        }
        JavaCIPUnknownScope._lock.readLock().lock();
        CachedQuery cached = JavaCIPUnknownScope._caches.get(path);
        if (cached == null || cached.loadTimeStamp < lastModified) {
            if (cached == null) {
                cached = new CachedQuery();
            }
            XQueryParser parser = new XQueryParser(is);
            StaticContext staticEnv = parser.getStaticContext();
            try {
                URI baseUri = url.toURI();
                staticEnv.setBaseURI(baseUri);
            } catch (URISyntaxRuntimeException e) {
                JavaCIPUnknownScope.log(PrintUtils.prettyPrintStackTrace(e, -1));
            }
            final XQueryModule module;
            try {
                module = parser.parse();
            } catch (XQueryRuntimeException e) {
                JavaCIPUnknownScope.log(PrintUtils.prettyPrintStackTrace(e, -1));
                JavaCIPUnknownScope._lock.readLock().unlock();
                throw e;
            }
            JavaCIPUnknownScope._lock.readLock().unlock();
            JavaCIPUnknownScope._lock.writeLock().lock();
            cached.queryObject = module;
            cached.staticEnv = staticEnv;
            cached.loadTimeStamp = System.currentTimeMillis();
            JavaCIPUnknownScope._caches.put(path, cached);
            JavaCIPUnknownScope._lock.writeLock().unlock();
            JavaCIPUnknownScope._lock.readLock().lock();
            try {
                module.staticAnalysis(staticEnv);
            } catch (XQueryRuntimeException e) {
                JavaCIPUnknownScope.log(PrintUtils.prettyPrintStackTrace(e, -1));
                JavaCIPUnknownScope._lock.readLock().unlock();
                throw e;
            }
        }
        JavaCIPUnknownScope._lock.readLock().unlock();
        return cached;
    }
}
