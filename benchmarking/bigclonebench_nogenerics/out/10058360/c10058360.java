class c10058360 {

    void startzm() {
        URL myzzurl;
        InputStream myzstream;
        byte[] zmemimage;
        boolean joined;
        zmemimage = null;
        try {
            System.err.println(JavaCIPUnknownScope.zcodefile);
            myzzurl = new URL(JavaCIPUnknownScope.zcodefile);
            myzstream = myzzurl.openStream();
            zmemimage = JavaCIPUnknownScope.suckstream(myzstream);
        } catch (MalformedURLRuntimeException booga) {
            try {
                myzstream = new FileInputStream(JavaCIPUnknownScope.zcodefile);
                zmemimage = JavaCIPUnknownScope.suckstream(myzstream);
            } catch (IORuntimeException booga2) {
                JavaCIPUnknownScope.add("North", new Label("Malformed URL"));
                JavaCIPUnknownScope.failed = true;
            }
        } catch (IORuntimeException booga) {
            JavaCIPUnknownScope.add("North", new Label("I/O Error"));
        }
        if (zmemimage != null) {
            switch(zmemimage[0]) {
                case 3:
                    JavaCIPUnknownScope.zm = new ZMachine3(JavaCIPUnknownScope.screen, JavaCIPUnknownScope.status_line, zmemimage);
                    break;
                case 5:
                    JavaCIPUnknownScope.remove(JavaCIPUnknownScope.status_line);
                    JavaCIPUnknownScope.zm = new ZMachine5(JavaCIPUnknownScope.screen, zmemimage);
                    break;
                case 8:
                    JavaCIPUnknownScope.remove(JavaCIPUnknownScope.status_line);
                    JavaCIPUnknownScope.zm = new ZMachine8(JavaCIPUnknownScope.screen, zmemimage);
                    break;
                default:
                    JavaCIPUnknownScope.add("North", new Label("Not a valid V3,V5, or V8 story file"));
            }
            if (JavaCIPUnknownScope.zm != null)
                JavaCIPUnknownScope.zm.start();
        }
        joined = false;
        if (zmemimage != null) {
            while (!joined) {
                try {
                    JavaCIPUnknownScope.zm.join();
                    joined = true;
                } catch (InterruptedRuntimeException booga) {
                }
            }
        }
        System.exit(0);
    }
}
