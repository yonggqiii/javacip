class Class_20 {

    public static void receive() {
        // try {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(null));
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(null, 0));
        // read the number of files from the client
        int number = dis.readInt();
        ArrayList<File> files = new ArrayList<File>(number);
        System.out.println("Number of Files to be received: " + number);
        // read file names, add files to arraylist
        for (int i = 0; i < number; i++) {
            File file = new File(dis.readUTF());
            files.add(file);
        }
        int n = 0;
        byte[] buf = new byte[4092];
        // outer loop, executes one for each file
        for (int i = 0; i < files.size(); i++) {
            System.out.println("Receiving file: " + files.get(i).getName());
            // create a new fileoutputstream for each new file
            FileOutputStream fos = new FileOutputStream("C:\\users\\tom5\\desktop\\salestools\\" + files.get(i).getName());
            // read file
            while ((n = dis.read(buf)) != -1) {
                fos.write(buf, 0, n);
                fos.flush();
            }
            fos.close();
        }
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // 
        // }
    }

    public void send(ArrayList<File> files) {
        // try {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(null));
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(null));
        System.out.println(files.size());
        // write the number of files to the server
        dos.writeInt(files.size());
        dos.flush();
        // write file names
        for (int i = 0; i < files.size(); i++) {
            dos.writeUTF(files.get(i).getName());
            dos.flush();
        }
        // buffer for file writing, to declare inside or outside loop?
        int n = 0;
        byte[] buf = new byte[4092];
        // outer loop, executes one for each file
        for (int i = 0; i < files.size(); i++) {
            System.out.println(files.get(i).getName());
            // create new fileinputstream for each file
            FileInputStream fis = new FileInputStream(files.get(i));
            // write file to dos
            while ((n = fis.read(buf)) != -1) {
                dos.write(buf, 0, n);
                dos.flush();
            }
            // should i close the dataoutputstream here and make a new one each time?
        }
        // or is this good?
        dos.close();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }
}
