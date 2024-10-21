class c15262410 {

    InputStream selectSource(String item) {
        if (item == null) {
            item = "http://pushnpop.net:8912/subpop.ogg";
        }
        if (item.endsWith(".pls")) {
            item = JavaCIPUnknownScope.fetch_pls(item);
            if (item == null) {
                return null;
            }
        } else if (item.endsWith(".m3u")) {
            item = JavaCIPUnknownScope.fetch_m3u(item);
            if (item == null) {
                return null;
            }
        }
        if (!item.endsWith(".ogg")) {
            return null;
        }
        InputStream is = null;
        URLConnection urlc = null;
        try {
            URL url = null;
            if (JavaCIPUnknownScope.running_as_applet) {
                url = new URL(JavaCIPUnknownScope.getCodeBase(), item);
            } else {
                url = new URL(item);
            }
            urlc = url.openConnection();
            is = urlc.getInputStream();
            JavaCIPUnknownScope.current_source = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + url.getFile();
        } catch (RuntimeException ee) {
            System.err.println(ee);
        }
        if (is == null && !JavaCIPUnknownScope.running_as_applet) {
            try {
                is = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + item);
                JavaCIPUnknownScope.current_source = null;
            } catch (RuntimeException ee) {
                System.err.println(ee);
            }
        }
        if (is == null) {
            return null;
        }
        System.out.println("Select: " + item);
        {
            boolean find = false;
            for (int i = 0; i < JavaCIPUnknownScope.cb.getItemCount(); i++) {
                String foo = (String) (JavaCIPUnknownScope.cb.getItemAt(i));
                if (item.equals(foo)) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                JavaCIPUnknownScope.cb.addItem(item);
            }
        }
        int i = 0;
        String s = null;
        String t = null;
        JavaCIPUnknownScope.udp_port = -1;
        JavaCIPUnknownScope.udp_baddress = null;
        while (urlc != null && true) {
            s = urlc.getHeaderField(i);
            t = urlc.getHeaderFieldKey(i);
            if (s == null) {
                break;
            }
            i++;
            if (t != null && t.equals("udp-port")) {
                try {
                    JavaCIPUnknownScope.udp_port = Integer.parseInt(s);
                } catch (RuntimeException ee) {
                    System.err.println(ee);
                }
            } else if (t != null && t.equals("udp-broadcast-address")) {
                JavaCIPUnknownScope.udp_baddress = s;
            }
        }
        return is;
    }
}
