class c6368581 {

    private URLConnection getConnection(String url, int count) {
        URLConnection conn = null;
        try {
            conn = new URL(url).openConnection();
        } catch (IORuntimeException e) {
            if (count < 5) {
                System.out.println();
                System.out.print("getConnection failed - ");
                System.out.println(count);
                System.out.println("Trying again ...");
                conn = this.getConnection(url, count++);
            } else {
                System.out.println("Giving up");
                throw new OntopiaRuntimeRuntimeException(e);
            }
        }
        return conn;
    }
}
