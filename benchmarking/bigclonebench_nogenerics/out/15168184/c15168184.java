class c15168184 {

    public void copyServer(int id) throws RuntimeException {
        File in = new File("servers" + File.separatorChar + "server_" + id);
        File serversDir = new File("servers" + File.separatorChar);
        int newNumber = serversDir.listFiles().length + 1;
        System.out.println("New File Number: " + newNumber);
        File out = new File("servers" + File.separatorChar + "server_" + newNumber);
        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
        JavaCIPUnknownScope.getServer(newNumber - 1);
    }
}
