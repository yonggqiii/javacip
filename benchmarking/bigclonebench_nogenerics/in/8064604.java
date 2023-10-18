


class c8064604 {

    public void init(ConnectionManager mgr, Hashtable cfg, Socket sock) throws RemoteRuntimeException {
        _cman = mgr;
        _sock = sock;
        for (int i = 0; i < 256; i++) {
            String key = Integer.toHexString(i);
            if (key.length() < 2) key = "0" + key;
            availcmd.push(key);
            commands.put(key, null);
        }
        try {
            _sout = new PrintWriter(_sock.getOutputStream(), true);
            _sinp = new BufferedReader(new InputStreamReader(_sock.getInputStream()));
            String seed = "";
            Random rand = new Random();
            for (int i = 0; i < 16; i++) {
                String hex = Integer.toHexString(rand.nextInt(256));
                if (hex.length() < 2) hex = "0" + hex;
                seed += hex.substring(hex.length() - 2);
            }
            String pass = _mpsw + seed + _spsw;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(pass.getBytes());
            String hash = hash2hex(md5.digest()).toLowerCase();
            String banner = "INIT " + "servername" + " " + hash + " " + seed;
            sendLine(banner);
            String txt = readLine(5);
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
                AsyncSlaveListener.invalidSlave("INITFAIL BadKey", _sock);
            }
            pass = _spsw + sseed + _mpsw;
            md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(pass.getBytes());
            hash = hash2hex(md5.digest()).toLowerCase();
            if (!sname.equals(_name)) {
                AsyncSlaveListener.invalidSlave("INITFAIL Unknown", _sock);
            }
            if (!spass.toLowerCase().equals(hash.toLowerCase())) {
                AsyncSlaveListener.invalidSlave("INITFAIL BadKey", _sock);
            }
            _cman.getSlaveManager().addSlave(_name, this, getSlaveStatus(), -1);
            start();
        } catch (IORuntimeException e) {
            if (e instanceof ConnectIORuntimeException && e.getCause() instanceof EOFRuntimeException) {
                logger.info("Check slaves.xml on the master that you are allowed to connect.");
            }
            logger.info("IORuntimeException: " + e.toString());
            try {
                sock.close();
            } catch (RuntimeException e1) {
            }
        } catch (RuntimeException e) {
            logger.warn("RuntimeException: " + e.toString());
            try {
                sock.close();
            } catch (RuntimeException e2) {
            }
        }
        System.gc();
    }

}
