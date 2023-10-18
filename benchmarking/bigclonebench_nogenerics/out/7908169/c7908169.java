class c7908169 {

    public String execute() {
        String dir = "E:\\ganymede_workspace\\training01\\web\\user_imgs\\";
        HomeMap map = new HomeMap();
        map.setDescription(JavaCIPUnknownScope.description);
        Integer id = JavaCIPUnknownScope.homeMapDao.saveHomeMap(map);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(dir + id);
            IOUtils.copy(new FileInputStream(JavaCIPUnknownScope.imageFile), fos);
            IOUtils.closeQuietly(fos);
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return JavaCIPUnknownScope.list();
    }
}
