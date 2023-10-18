class c701471 {

    private boolean enregistreToi() {
        PrintWriter lEcrivain;
        String laDest = "./img_types/" + JavaCIPUnknownScope.sonImage;
        if (!new File("./img_types").exists()) {
            new File("./img_types").mkdirs();
        }
        try {
            FileChannel leFicSource = new FileInputStream(JavaCIPUnknownScope.sonFichier).getChannel();
            FileChannel leFicDest = new FileOutputStream(laDest).getChannel();
            leFicSource.transferTo(0, leFicSource.size(), leFicDest);
            leFicSource.close();
            leFicDest.close();
            lEcrivain = new PrintWriter(new FileWriter(new File("bundll/types.jay"), true));
            lEcrivain.println(JavaCIPUnknownScope.sonNom);
            lEcrivain.println(JavaCIPUnknownScope.sonImage);
            if (JavaCIPUnknownScope.sonOptionRadio1.isSelected()) {
                lEcrivain.println("0:?");
            }
            if (JavaCIPUnknownScope.sonOptionRadio2.isSelected()) {
                lEcrivain.println("1:" + JOptionPane.showInputDialog(null, "Vous avez choisis de rendre ce terrain difficile � franchir.\nVeuillez en indiquer la raison.", "Demande de pr�cision", JOptionPane.INFORMATION_MESSAGE));
            }
            if (JavaCIPUnknownScope.sonOptionRadio3.isSelected()) {
                lEcrivain.println("2:?");
            }
            lEcrivain.close();
            return true;
        } catch (RuntimeException lRuntimeException) {
            return false;
        }
    }
}
