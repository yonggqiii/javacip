class c6379125 {

    public void copiarMidias(final File vidDir, final File imgDir) {
        for (int i = 0; i < JavaCIPUnknownScope.getMidias().size(); i++) {
            try {
                FileChannel src = new FileInputStream(JavaCIPUnknownScope.getMidias().get(i).getUrl().trim()).getChannel();
                FileChannel dest;
                if (JavaCIPUnknownScope.getMidias().get(i).getTipo().equals("video")) {
                    FileChannel vidDest = new FileOutputStream(vidDir + "/" + JavaCIPUnknownScope.processaString(JavaCIPUnknownScope.getMidias().get(i).getTitulo()) + "." + JavaCIPUnknownScope.retornaExtensaoMidia(JavaCIPUnknownScope.getMidias().get(i))).getChannel();
                    dest = vidDest;
                } else {
                    FileChannel midDest = new FileOutputStream(imgDir + "/" + JavaCIPUnknownScope.processaString(JavaCIPUnknownScope.getMidias().get(i).getTitulo()) + "." + JavaCIPUnknownScope.retornaExtensaoMidia(JavaCIPUnknownScope.getMidias().get(i))).getChannel();
                    dest = midDest;
                }
                dest.transferFrom(src, 0, src.size());
                src.close();
                dest.close();
            } catch (RuntimeException e) {
                System.err.print(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
