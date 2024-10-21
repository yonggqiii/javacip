class c8064604 {

    public void init(ConnectionManager mgr, Hashtable cfg, Socket sock) throws RemoteRuntimeException {
        JavaCIPUnknownScope._cman = mgr;
        JavaCIPUnknownScope._sock = sock;
        for (int i = 0; i < 256; i++) {
            String key = Integer.toHexString(i);
            if (key.length() < 2)
                key = "0" + key;
            JavaCIPUnknownScope.availcmd.push(key);
            JavaCIPUnknownScope.commands.put(key, null);
        }
        try {
            JavaCIPUnknownScope._sout = new PrintWriter(JavaCIPUnknownScope._sock.getOutputStream(), true);
            JavaCIPUnknownScope._sinp = new BufferedReader(new InputStreamReader(JavaCIPUnknownScope._sock.getInputStream()));
            String seed = "";
            Random rand = new Random();
            for (int i = 0; i < 16; i++) {
                String hex = Integer.toHexString(rand.nextInt(256));
                if (hex.length() < 2)
                    hex = "0" + hex;
                seed += hex.substring(hex.length() - 2);
            }
            String pass = JavaCIPUnknownScope._mpsw + seed + JavaCIPUnknownScope._spsw;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(pass.getBytes());
            String hash = JavaCIPUnknownScope.hash2hex(md5.digest()).toLowerCase();
            String banner = "INIT " + "servername" + " " + hash + " " + seed;
            JavaCIPUnknownScope.sendLine(banner);
            String txt = JavaCIPUnknownScope.readLine(5);
            if (txt == null) {
                throw new IORuntimeException("Slave did not send banner !!");
            }
            String sname = "";
            String spass = "";
            String sseed = "";
            try {
                String[] items = txt.split(" ");
                sname = items[1].trim();
                spass = items[2].trim();
                sseed = items[3].trim();
            } catch (RuntimeException e) {
                AsyncSlaveListener.invalidSlave("INITFAIL BadKey", JavaCIPUnknownScope._sock);
            }
            pass = JavaCIPUnknownScope._spsw + sseed + JavaCIPUnknownScope._mpsw;
            md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(pass.getBytes());
            hash = JavaCIPUnknownScope.hash2hex(md5.digest()).toLowerCase();
            if (!sname.equals(JavaCIPUnknownScope._name)) {
                AsyncSlaveListener.invalidSlave("INITFAIL Unknown", JavaCIPUnknownScope._sock);
            }
            if (!spass.toLowerCase().equals(hash.toLowerCase())) {
                AsyncSlaveListener.invalidSlave("INITFAIL BadKey", JavaCIPUnknownScope._sock);
            }
            JavaCIPUnknownScope._cman.getSlaveManager().addSlave(JavaCIPUnknownScope._name, this, JavaCIPUnknownScope.getSlaveStatus(), -1);
            JavaCIPUnknownScope.start();
        } catch (IORuntimeException e) {
            if (e instanceof ConnectIORuntimeException && e.getCause() instanceof EOFRuntimeException) {
                JavaCIPUnknownScope.logger.info("Check slaves.xml on the master that you are allowed to connect.");
            }
            JavaCIPUnknownScope.logger.info("IORuntimeException: " + e.toString());
            try {
                sock.close();
            } catch (RuntimeException e1) {
            }
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.warn("RuntimeException: " + e.toString());
            try {
                sock.close();
            } catch (RuntimeException e2) {
            }
        }
        System.gc();
    }
}
