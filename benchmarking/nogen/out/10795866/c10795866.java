class c10795866 {

    public String upload() {
        System.out.println(JavaCIPUnknownScope.imgFile);
        String destDir = "E:\\ganymede_workspace\\training01\\web\\user_imgs\\map_bg.jpg";
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(new File(destDir));
            IOUtils.copy(new FileInputStream(JavaCIPUnknownScope.imgFile), fos);
            IOUtils.closeQuietly(fos);
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return "show";
    }
}
