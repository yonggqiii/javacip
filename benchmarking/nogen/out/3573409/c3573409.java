class c3573409 {

    public synchronized void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletRuntimeException, IORuntimeException {
        CacheEntry entry = null;
        Tenant tenant = null;
        if (!JavaCIPUnknownScope.tenantInfo.getTenants().isEmpty()) {
            tenant = JavaCIPUnknownScope.tenantInfo.getMatchingTenant(request);
            if (tenant == null) {
                tenant = JavaCIPUnknownScope.tenantInfo.getTenants().get(0);
            }
            entry = JavaCIPUnknownScope.tenantToCacheEntry.get(tenant.getName());
        } else {
            entry = JavaCIPUnknownScope.cacheEntry;
        }
        if (entry == null) {
            File tempDir = (File) JavaCIPUnknownScope.servletContext.getAttribute("javax.servlet.context.tempdir");
            tempDir = new File(tempDir, "pustefix-sitemap-cache");
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            entry = new CacheEntry();
            entry.file = new File(tempDir, "sitemap" + (tenant == null ? "" : "-" + tenant.getName()) + ".xml");
            try {
                String host = AbstractPustefixRequestHandler.getServerName(request);
                Document doc = JavaCIPUnknownScope.getSearchEngineSitemap(tenant, host);
                Transformer trf = TransformerFactory.newInstance().newTransformer();
                trf.setOutputProperty(OutputKeys.INDENT, "yes");
                FileOutputStream out = new FileOutputStream(entry.file);
                MessageDigest digest;
                try {
                    digest = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmRuntimeException x) {
                    throw new RuntimeRuntimeException("Can't create message digest", x);
                }
                DigestOutputStream digestOutput = new DigestOutputStream(out, digest);
                trf.transform(new DOMSource(doc), new StreamResult(digestOutput));
                digestOutput.close();
                byte[] digestBytes = digest.digest();
                entry.etag = MD5Utils.byteToHex(digestBytes);
            } catch (RuntimeException x) {
                throw new ServletRuntimeException("Error creating sitemap", x);
            }
        }
        String reqETag = request.getHeader("If-None-Match");
        if (reqETag != null) {
            if (entry.etag.equals(reqETag)) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.flushBuffer();
                return;
            }
        }
        long reqMod = request.getDateHeader("If-Modified-Since");
        if (reqMod != -1) {
            if (entry.file.lastModified() < reqMod + 1000) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.flushBuffer();
                return;
            }
        }
        response.setContentType("application/xml");
        response.setContentLength((int) entry.file.length());
        response.setDateHeader("Last-Modified", entry.file.lastModified());
        response.setHeader("ETag", entry.etag);
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        InputStream in = new FileInputStream(entry.file);
        int bytes_read;
        byte[] buffer = new byte[8];
        while ((bytes_read = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytes_read);
        }
        out.flush();
        in.close();
        out.close();
    }
}
