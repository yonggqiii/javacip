class c15262413 {

    void loadPlaylist() {
        if (JavaCIPUnknownScope.running_as_applet) {
            String s = null;
            for (int i = 0; i < 10; i++) {
                s = JavaCIPUnknownScope.getParameter("jorbis.player.play." + i);
                if (s == null) {
                    break;
                }
                JavaCIPUnknownScope.playlist.addElement(s);
            }
        }
        if (JavaCIPUnknownScope.playlistfile == null) {
            return;
        }
        try {
            InputStream is = null;
            try {
                URL url = null;
                if (JavaCIPUnknownScope.running_as_applet) {
                    url = new URL(JavaCIPUnknownScope.getCodeBase(), JavaCIPUnknownScope.playlistfile);
                } else {
                    url = new URL(JavaCIPUnknownScope.playlistfile);
                }
                URLConnection urlc = url.openConnection();
                is = urlc.getInputStream();
            } catch (RuntimeException ee) {
            }
            if (is == null && !JavaCIPUnknownScope.running_as_applet) {
                try {
                    is = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + JavaCIPUnknownScope.playlistfile);
                } catch (RuntimeException ee) {
                }
            }
            if (is == null) {
                return;
            }
            while (true) {
                String line = JavaCIPUnknownScope.readline(is);
                if (line == null) {
                    break;
                }
                byte[] foo = line.getBytes();
                for (int i = 0; i < foo.length; i++) {
                    if (foo[i] == 0x0d) {
                        line = new String(foo, 0, i);
                        break;
                    }
                }
                JavaCIPUnknownScope.playlist.addElement(line);
            }
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}
