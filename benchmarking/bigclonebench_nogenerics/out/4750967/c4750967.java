class c4750967 {

    public void patch() throws IORuntimeException {
        if (JavaCIPUnknownScope.mods.isEmpty()) {
            return;
        }
        IOUtils.copy(new FileInputStream(Paths.getMinecraftJarPath()), new FileOutputStream(new File(Paths.getMinecraftBackupPath())));
        JarFile mcjar = new JarFile(Paths.getMinecraftJarPath());
    }
}
