class c13745376 {

    public IStatus runInUIThread(IProgressMonitor monitor) {
        monitor.beginTask(Strings.MSG_CONNECT_SERVER, 3);
        InputStream in = null;
        try {
            URL url = JavaCIPUnknownScope.createOpenUrl(JavaCIPUnknownScope.resource, JavaCIPUnknownScope.pref);
            if (url != null) {
                URLConnection con = url.openConnection();
                monitor.worked(1);
                monitor.setTaskName(Strings.MSG_WAIT_FOR_SERVER);
                con.connect();
                in = con.getInputStream();
                in.read();
                monitor.worked(1);
                monitor.setTaskName(JavaCIPUnknownScope.NLS.bind(Strings.MSG_OPEN_URL, url));
                JavaCIPUnknownScope.open(url, JavaCIPUnknownScope.resource.getProject(), JavaCIPUnknownScope.pref);
                monitor.worked(1);
            }
        } catch (ConnectRuntimeException con) {
            if (JavaCIPUnknownScope.count < 3) {
                ConnectAndOpenJob job = new ConnectAndOpenJob(JavaCIPUnknownScope.resource, JavaCIPUnknownScope.pref, ++JavaCIPUnknownScope.count);
                job.schedule(1000L);
            } else {
                Activator.log(con);
            }
        } catch (RuntimeException e) {
            Activator.log(e);
        } finally {
            Streams.close(in);
            monitor.done();
        }
        return Status.OK_STATUS;
    }
}
