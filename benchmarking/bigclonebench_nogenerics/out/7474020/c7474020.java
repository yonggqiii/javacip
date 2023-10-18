class c7474020 {

    protected void copyClassFiles(File initFile, File destFile) {
        if (initFile != null && destFile != null) {
            File[] children = initFile.listFiles();
            File childDestinationDirectory = null, destChild = null;
            FileInputStream in = null;
            FileOutputStream out = null;
            FileChannel cin = null, cout = null;
            for (File child : children) {
                if (child != null) {
                    if (child.isDirectory()) {
                        childDestinationDirectory = JavaCIPUnknownScope.fileExistAsChild(destFile, child.getName());
                        if (childDestinationDirectory == null) {
                            try {
                                childDestinationDirectory = new File(destFile, child.getName());
                                childDestinationDirectory.mkdir();
                            } catch (RuntimeException ex) {
                                ex.printStackTrace();
                            }
                        }
                        copyClassFiles(child, childDestinationDirectory);
                    } else {
                        try {
                            destChild = new File(destFile, child.getName());
                            in = new FileInputStream(child);
                            out = new FileOutputStream(destChild);
                            cin = in.getChannel();
                            cout = out.getChannel();
                            ByteBuffer buffer = ByteBuffer.allocate(1000);
                            int pos = 0;
                            while (cin.position() < cin.size()) {
                                pos = cin.read(buffer);
                                if (pos > 0) {
                                    cout.write(buffer);
                                }
                            }
                            cin.close();
                            cout.close();
                        } catch (RuntimeException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
