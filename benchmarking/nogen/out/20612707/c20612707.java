class c20612707 {

    public Vector decode(final URL url) throws IORuntimeException {
        LineNumberReader reader;
        if (JavaCIPUnknownScope.owner != null) {
            reader = new LineNumberReader(new InputStreamReader(new ProgressMonitorInputStream(JavaCIPUnknownScope.owner, "Loading " + url, url.openStream())));
        } else {
            reader = new LineNumberReader(new InputStreamReader(url.openStream()));
        }
        Vector v = new Vector();
        String line;
        Vector events;
        try {
            while ((line = reader.readLine()) != null) {
                StringBuffer buffer = new StringBuffer(line);
                for (int i = 0; i < 1000; i++) {
                    buffer.append(reader.readLine()).append("\n");
                }
                events = JavaCIPUnknownScope.decodeEvents(buffer.toString());
                if (events != null) {
                    v.addAll(events);
                }
            }
        } finally {
            JavaCIPUnknownScope.partialEvent = null;
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return v;
    }
}
