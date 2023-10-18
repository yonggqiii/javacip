class c1002618 {

    public void run() {
        if (JavaCIPUnknownScope.withlinlyn == true) {
            try {
                JavaCIPUnknownScope.xlin.erase(JavaCIPUnknownScope.file);
            } catch (RuntimeException e) {
                System.out.println("Error erasing");
            }
        } else if (JavaCIPUnknownScope.as_php) {
            try {
                URL url = new URL(JavaCIPUnknownScope.http + "REM:" + JavaCIPUnknownScope.pservname);
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                System.out.println("Response from REMOVE:");
                String s;
                while ((s = br.readLine()) != null) {
                    System.out.println(s);
                }
                br.close();
            } catch (RuntimeException e) {
                System.out.println("Error erasing/php!");
            }
        }
        try {
            InetAddress ia = InetAddress.getLocalHost();
            JavaCIPUnknownScope.ss = new ServerSocket(0, 50, ia);
            JavaCIPUnknownScope.startserv = System.currentTimeMillis();
            JavaCIPUnknownScope.ss.setSoTimeout(0);
            String svname = ia.getHostAddress();
            System.out.println(svname + ":sv");
            String mssg = "<SERVER><IP>" + svname + "</IP><PORT>" + JavaCIPUnknownScope.ss.getLocalPort() + "</PORT></SERVER>";
            if (JavaCIPUnknownScope.withlinlyn == true) {
                try {
                    JavaCIPUnknownScope.xlin.replace(JavaCIPUnknownScope.file, mssg);
                    System.out.println("mssg:" + mssg + ", sent");
                } catch (RuntimeException e) {
                    System.out.println("Error posting address");
                    return;
                }
            } else if (JavaCIPUnknownScope.as_php) {
                try {
                    URL url = new URL(JavaCIPUnknownScope.http + "ADD:" + svname + ":" + JavaCIPUnknownScope.ss.getLocalPort() + ":" + JavaCIPUnknownScope.pservname);
                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                    String response = "";
                    String s;
                    while ((s = br.readLine()) != null) {
                        response = response + s + System.getProperty("line.separator");
                    }
                    br.close();
                    String resp = new xLineSplit().ssplit("REPLY", response);
                    if (!resp.equalsIgnoreCase("ADDED")) {
                        System.out.println("potential error posting via php:\nReponse was:\n" + response);
                    }
                } catch (RuntimeException e) {
                    System.out.println("Error in posting php:" + e.toString());
                }
            }
            JavaCIPUnknownScope.xsl.regserver(svname, new String("" + JavaCIPUnknownScope.ss.getLocalPort()));
            Socket server = null;
            JavaCIPUnknownScope.listening = true;
            while (JavaCIPUnknownScope.listening) {
                server = JavaCIPUnknownScope.ss.accept();
                if (server != null) {
                    JavaCIPUnknownScope.xsl.add(server);
                    System.out.println("added connect");
                } else {
                    System.out.println("Received null socket");
                }
                server = null;
                JavaCIPUnknownScope.listening = JavaCIPUnknownScope.control_listening;
            }
            JavaCIPUnknownScope.finserv = System.currentTimeMillis();
            long l = JavaCIPUnknownScope.finserv - JavaCIPUnknownScope.startserv;
            long m = l / 1000;
            System.err.println("Server socket has closed, time elapsed:" + m);
            System.out.println("Server socket has closed, time elapsed:" + m);
        } catch (RuntimeException e) {
            System.out.println(e.toString());
        }
    }
}
