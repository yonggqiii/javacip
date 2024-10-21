class c11322573 {

    private void preprocessObjects(GeoObject[] objects) throws IORuntimeException {
        System.out.println("objects.length " + objects.length);
        for (int i = 0; i < objects.length; i++) {
            String fileName = objects[i].getPath();
            int dotindex = fileName.lastIndexOf(".");
            dotindex = dotindex < 0 ? 0 : dotindex;
            String tmp = dotindex < 1 ? fileName : fileName.substring(0, dotindex + 3) + "w";
            System.out.println("i: " + " world filename " + tmp);
            File worldFile = new File(tmp);
            if (worldFile.exists()) {
                BufferedReader worldFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(worldFile)));
                if (JavaCIPUnknownScope.staticDebugOn)
                    JavaCIPUnknownScope.debug("b4nextline: ");
                JavaCIPUnknownScope.line = worldFileReader.readLine();
                if (JavaCIPUnknownScope.staticDebugOn)
                    JavaCIPUnknownScope.debug("line: " + JavaCIPUnknownScope.line);
                if (JavaCIPUnknownScope.line != null) {
                    JavaCIPUnknownScope.line = worldFileReader.readLine();
                    if (JavaCIPUnknownScope.staticDebugOn)
                        JavaCIPUnknownScope.debug("line: " + JavaCIPUnknownScope.line);
                    JavaCIPUnknownScope.tokenizer = new StringTokenizer(JavaCIPUnknownScope.line, " \n\t\r\"", false);
                    objects[i].setLon(Double.valueOf(JavaCIPUnknownScope.tokenizer.nextToken()).doubleValue());
                    JavaCIPUnknownScope.line = worldFileReader.readLine();
                    if (JavaCIPUnknownScope.staticDebugOn)
                        JavaCIPUnknownScope.debug("line: " + JavaCIPUnknownScope.line);
                    JavaCIPUnknownScope.tokenizer = new StringTokenizer(JavaCIPUnknownScope.line, " \n\t\r\"", false);
                    objects[i].setLat(Double.valueOf(JavaCIPUnknownScope.tokenizer.nextToken()).doubleValue());
                }
            }
            File file = new File(objects[i].getPath());
            if (file.exists()) {
                System.out.println("object src file found ");
                int slashindex = fileName.lastIndexOf(JavaCIPUnknownScope.java.io.File.separator);
                slashindex = slashindex < 0 ? 0 : slashindex;
                if (slashindex == 0) {
                    slashindex = fileName.lastIndexOf("/");
                    slashindex = slashindex < 0 ? 0 : slashindex;
                }
                tmp = slashindex < 1 ? fileName : fileName.substring(slashindex + 1, fileName.length());
                System.out.println("filename " + JavaCIPUnknownScope.destinationDirectory + XPlat.fileSep + tmp);
                objects[i].setPath(tmp);
                file = new File(fileName);
                if (file.exists()) {
                    DataInputStream dataIn = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
                    DataOutputStream dataOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(JavaCIPUnknownScope.destinationDirectory + XPlat.fileSep + tmp)));
                    System.out.println("copying to " + JavaCIPUnknownScope.destinationDirectory + XPlat.fileSep + tmp);
                    for (; ; ) {
                        try {
                            dataOut.writeShort(dataIn.readShort());
                        } catch (EOFRuntimeException e) {
                            break;
                        } catch (IORuntimeException e) {
                            break;
                        }
                    }
                    dataOut.close();
                }
            }
        }
    }
}
