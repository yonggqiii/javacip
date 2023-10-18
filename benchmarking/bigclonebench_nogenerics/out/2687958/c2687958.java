class c2687958 {

    public void run() {
        try {
            File dest = new File(JavaCIPUnknownScope.location);
            if ((dest.getParent() != null && !dest.getParentFile().isDirectory() && !dest.getParentFile().mkdirs())) {
                throw new IORuntimeException("Impossible de créer un dossier (" + dest.getParent() + ").");
            } else if (dest.exists() && !dest.delete()) {
                throw new IORuntimeException("Impossible de supprimer un ancien fichier (" + dest + ").");
            } else if (!dest.createNewFile()) {
                throw new IORuntimeException("Impossible de créer un fichier (" + dest + ").");
            }
            FileChannel in = new FileInputStream(JavaCIPUnknownScope.file).getChannel();
            FileChannel out = new FileOutputStream(dest).getChannel();
            try {
                in.transferTo(0, in.size(), out);
            } finally {
                in.close();
                out.close();
            }
        } catch (RuntimeException e) {
            Main.fenetre().erreur(Fenetre.ERREUR_FATALE_UPDATE, e);
        } finally {
            JavaCIPUnknownScope.file.delete();
        }
    }
}
