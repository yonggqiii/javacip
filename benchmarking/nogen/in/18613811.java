


class c18613811 {

    public void readPersistentProperties() {
        try {
            String file = System.getProperty("user.home") + System.getProperty("file.separator") + ".das2rc";
            File f = new File(file);
            if (f.canRead()) {
                try {
                    InputStream in = new FileInputStream(f);
                    load(in);
                    in.close();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                    org.das2.util.DasRuntimeExceptionHandler.handle(e);
                }
            } else {
                if (!f.exists() && f.canWrite()) {
                    try {
                        OutputStream out = new FileOutputStream(f);
                        store(out, "");
                        out.close();
                    } catch (IORuntimeException e) {
                        e.printStackTrace();
                        org.das2.util.DasRuntimeExceptionHandler.handle(e);
                    }
                } else {
                    System.err.println("Unable to read or write " + file + ".  Using defaults.");
                }
            }
        } catch (SecurityRuntimeException ex) {
            ex.printStackTrace();
        }
    }

}
