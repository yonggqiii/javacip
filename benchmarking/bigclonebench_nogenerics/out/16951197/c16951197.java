class c16951197 {

    public void testReleaseOnEntityWriteTo() throws RuntimeException {
        HttpParams params = JavaCIPUnknownScope.defaultParams.copy();
        ConnManagerParams.setMaxTotalConnections(params, 1);
        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(1));
        ThreadSafeClientConnManager mgr = JavaCIPUnknownScope.createTSCCM(params, null);
        JavaCIPUnknownScope.assertEquals(0, mgr.getConnectionsInPool());
        DefaultHttpClient client = new DefaultHttpClient(mgr, params);
        HttpGet httpget = new HttpGet("/random/20000");
        HttpHost target = JavaCIPUnknownScope.getServerHttp();
        HttpResponse response = client.execute(target, httpget);
        ClientConnectionRequest connreq = mgr.requestConnection(new HttpRoute(target), null);
        try {
            connreq.getConnection(250, TimeUnit.MILLISECONDS);
            JavaCIPUnknownScope.fail("ConnectionPoolTimeoutRuntimeException should have been thrown");
        } catch (ConnectionPoolTimeoutRuntimeException expected) {
        }
        HttpEntity e = response.getEntity();
        JavaCIPUnknownScope.assertNotNull(e);
        ByteArrayOutputStream outsteam = new ByteArrayOutputStream();
        e.writeTo(outsteam);
        JavaCIPUnknownScope.assertEquals(1, mgr.getConnectionsInPool());
        connreq = mgr.requestConnection(new HttpRoute(target), null);
        ManagedClientConnection conn = connreq.getConnection(250, TimeUnit.MILLISECONDS);
        mgr.releaseConnection(conn, -1, null);
        mgr.shutdown();
    }
}
