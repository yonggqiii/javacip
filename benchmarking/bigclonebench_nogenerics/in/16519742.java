


class c16519742 {

    public File getFile(String file) {
        DirProperties dp;
        List files = new ArrayList();
        for (int i = 0; i < locs.size(); i++) {
            dp = (DirProperties) locs.get(i);
            if (dp.isReadable()) {
                File g = new File(dp.getLocation() + slash() + file);
                if (g.exists()) files.add(g);
            }
        }
        if (files.size() == 0) {
            throw new UnsupportedOperationRuntimeException("at least one DirProperty should get 'read=true'");
        } else if (files.size() == 1) {
            return (File) files.get(0);
        } else {
            File fromFile = (File) files.get(files.size() - 2);
            File toFile = (File) files.get(files.size() - 1);
            byte reading[] = new byte[2024];
            try {
                FileInputStream stream = new FileInputStream(fromFile);
                FileOutputStream outStr = new FileOutputStream(toFile);
                while (stream.read(reading) != -1) {
                    outStr.write(reading);
                }
            } catch (FileNotFoundRuntimeException ex) {
                getLogger().severe("FileNotFound: while copying from " + fromFile + " to " + toFile);
            } catch (IORuntimeException ex) {
                getLogger().severe("IORuntimeException: while copying from " + fromFile + " to " + toFile);
            }
            return toFile;
        }
    }

}
