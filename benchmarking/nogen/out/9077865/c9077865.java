class c9077865 {

    public void exec() {
        if (JavaCIPUnknownScope.fileURI == null)
            return;
        InputStream is = null;
        try {
            if (JavaCIPUnknownScope.fileURI.toLowerCase().startsWith("dbgp://")) {
                String uri = JavaCIPUnknownScope.fileURI.substring(7);
                if (uri.toLowerCase().startsWith("file/")) {
                    uri = JavaCIPUnknownScope.fileURI.substring(5);
                    is = new FileInputStream(new File(uri));
                } else {
                    XmldbURI pathUri = XmldbURI.create(URLDecoder.decode(JavaCIPUnknownScope.fileURI.substring(15), "UTF-8"));
                    Database db = JavaCIPUnknownScope.getJoint().getContext().getDatabase();
                    DBBroker broker = null;
                    try {
                        broker = db.getBroker();
                        DocumentImpl resource = broker.getXMLResource(pathUri, Lock.READ_LOCK);
                        if (resource.getResourceType() == DocumentImpl.BINARY_FILE) {
                            is = broker.getBinaryResource((BinaryDocument) resource);
                        } else {
                            return;
                        }
                    } catch (EXistRuntimeException e) {
                        JavaCIPUnknownScope.exception = e;
                    } finally {
                        db.release(broker);
                    }
                }
            } else {
                URL url = new URL(JavaCIPUnknownScope.fileURI);
                URLConnection conn = url.openConnection();
                is = conn.getInputStream();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[256];
            int c;
            while ((c = is.read(buf)) > -1) {
                baos.write(buf, 0, c);
            }
            JavaCIPUnknownScope.source = baos.toByteArray();
            JavaCIPUnknownScope.success = true;
        } catch (MalformedURLRuntimeException e) {
            JavaCIPUnknownScope.exception = e;
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.exception = e;
        } catch (PermissionDeniedRuntimeException e) {
            JavaCIPUnknownScope.exception = e;
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IORuntimeException e) {
                    if (JavaCIPUnknownScope.exception == null)
                        JavaCIPUnknownScope.exception = e;
                }
        }
    }
}
