class c11322572 {

    private void preprocessImages(GeoImage[] detailedImages) throws IORuntimeException {
        for (int i = 0; i < detailedImages.length; i++) {
            BufferedImage img = JavaCIPUnknownScope.loadImage(detailedImages[i].getPath());
            detailedImages[i].setLatDim(img.getHeight());
            detailedImages[i].setLonDim(img.getWidth());
            JavaCIPUnknownScope.freeImage(img);
            String fileName = detailedImages[i].getPath();
            int dotindex = fileName.lastIndexOf(".");
            dotindex = dotindex < 0 ? 0 : dotindex;
            String tmp = dotindex < 1 ? fileName : fileName.substring(0, dotindex + 3) + "w";
            System.out.println("filename " + tmp);
            File worldFile = new File(tmp);
            if (!worldFile.exists()) {
                System.out.println("Rez: Could not find file: " + tmp);
                JavaCIPUnknownScope.debug("Rez: Could not find directory: " + tmp);
                throw new IORuntimeException("File not Found");
            }
            BufferedReader worldFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(worldFile)));
            if (JavaCIPUnknownScope.staticDebugOn)
                JavaCIPUnknownScope.debug("b4nextline: ");
            JavaCIPUnknownScope.line = worldFileReader.readLine();
            if (JavaCIPUnknownScope.staticDebugOn)
                JavaCIPUnknownScope.debug("line: " + JavaCIPUnknownScope.line);
            if (JavaCIPUnknownScope.line != null) {
                JavaCIPUnknownScope.tokenizer = new StringTokenizer(JavaCIPUnknownScope.line, " \n\t\r\"", false);
                detailedImages[i].setLonSpacing(Double.valueOf(JavaCIPUnknownScope.tokenizer.nextToken()).doubleValue());
                detailedImages[i].setLonExtent(detailedImages[i].getLonSpacing() * ((double) detailedImages[i].getLonDim() - 1d));
                System.out.println("setLonExtent " + detailedImages[i].getLonExtent());
                JavaCIPUnknownScope.line = worldFileReader.readLine();
                if (JavaCIPUnknownScope.staticDebugOn)
                    JavaCIPUnknownScope.debug("skip line: " + JavaCIPUnknownScope.line);
                JavaCIPUnknownScope.line = worldFileReader.readLine();
                if (JavaCIPUnknownScope.staticDebugOn)
                    JavaCIPUnknownScope.debug("skip line: " + JavaCIPUnknownScope.line);
                JavaCIPUnknownScope.line = worldFileReader.readLine();
                if (JavaCIPUnknownScope.staticDebugOn)
                    JavaCIPUnknownScope.debug("line: " + JavaCIPUnknownScope.line);
                JavaCIPUnknownScope.tokenizer = new StringTokenizer(JavaCIPUnknownScope.line, " \n\t\r\"", false);
                detailedImages[i].setLatSpacing(Double.valueOf(JavaCIPUnknownScope.tokenizer.nextToken()).doubleValue());
                detailedImages[i].setLatExtent(detailedImages[i].getLatSpacing() * ((double) detailedImages[i].getLatDim() - 1d));
                JavaCIPUnknownScope.line = worldFileReader.readLine();
                if (JavaCIPUnknownScope.staticDebugOn)
                    JavaCIPUnknownScope.debug("line: " + JavaCIPUnknownScope.line);
                JavaCIPUnknownScope.tokenizer = new StringTokenizer(JavaCIPUnknownScope.line, " \n\t\r\"", false);
                detailedImages[i].setLon(Double.valueOf(JavaCIPUnknownScope.tokenizer.nextToken()).doubleValue());
                JavaCIPUnknownScope.line = worldFileReader.readLine();
                if (JavaCIPUnknownScope.staticDebugOn)
                    JavaCIPUnknownScope.debug("line: " + JavaCIPUnknownScope.line);
                JavaCIPUnknownScope.tokenizer = new StringTokenizer(JavaCIPUnknownScope.line, " \n\t\r\"", false);
                detailedImages[i].setLat(Double.valueOf(JavaCIPUnknownScope.tokenizer.nextToken()).doubleValue());
                int slashindex = fileName.lastIndexOf(JavaCIPUnknownScope.java.io.File.separator);
                slashindex = slashindex < 0 ? 0 : slashindex;
                if (slashindex == 0) {
                    slashindex = fileName.lastIndexOf("/");
                    slashindex = slashindex < 0 ? 0 : slashindex;
                }
                tmp = slashindex < 1 ? fileName : fileName.substring(slashindex + 1, fileName.length());
                System.out.println("filename " + JavaCIPUnknownScope.destinationDirectory + XPlat.fileSep + tmp);
                detailedImages[i].setPath(tmp);
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
            } else {
                System.out.println("Rez: ERROR: World file for image is null");
            }
        }
    }
}
