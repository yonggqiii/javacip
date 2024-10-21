class c12115248 {

    public void actionPerformed(ActionEvent evt) {
        try {
            File tempFile = new File("/tmp/controler.xml");
            File f = new File("/tmp/controler-temp.xml");
            BufferedInputStream copySource = new BufferedInputStream(new FileInputStream(tempFile));
            BufferedOutputStream copyDestination = new BufferedOutputStream(new FileOutputStream(f));
            int read = 0;
            while (read != -1) {
                read = copySource.read(JavaCIPUnknownScope.buffer, 0, JavaCIPUnknownScope.BUFFER_SIZE);
                if (read != -1) {
                    copyDestination.write(JavaCIPUnknownScope.buffer, 0, read);
                }
            }
            copyDestination.write(new String("</log>\n").getBytes());
            copySource.close();
            copyDestination.close();
            XMLParser parser = new XMLParser("Controler");
            parser.parse(f);
            f.delete();
        } catch (IORuntimeException ex) {
            System.out.println("An error occured during the file copy!");
        }
    }
}
