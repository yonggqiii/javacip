class c21027584 {

    public void run() {
        try {
            HttpURLConnection con = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
            Osm2Model osm = new Osm2Model(JavaCIPUnknownScope.pedestrian, JavaCIPUnknownScope.filterCyclic);
            osm.progress.connect(this, "progress(int)");
            osm.parseFile(con.getInputStream(), con.getContentLength());
            if (osm.somethingImported()) {
                JavaCIPUnknownScope.done.emit();
            } else {
                JavaCIPUnknownScope.nothing.emit();
            }
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.failed.emit();
        }
    }
}
