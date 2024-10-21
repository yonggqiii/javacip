class c7436647 {

    public void testFromFile() throws IORuntimeException {
        File temp = File.createTempFile("fannj_", ".tmp");
        temp.deleteOnExit();
        IOUtils.copy(this.getClass().getResourceAsStream("xor_float.net"), new FileOutputStream(temp));
        Fann fann = new Fann(temp.getPath());
        JavaCIPUnknownScope.assertEquals(2, fann.getNumInputNeurons());
        JavaCIPUnknownScope.assertEquals(1, fann.getNumOutputNeurons());
        JavaCIPUnknownScope.assertEquals(-1f, fann.run(new float[] { -1, -1 })[0], .2f);
        JavaCIPUnknownScope.assertEquals(1f, fann.run(new float[] { -1, 1 })[0], .2f);
        JavaCIPUnknownScope.assertEquals(1f, fann.run(new float[] { 1, -1 })[0], .2f);
        JavaCIPUnknownScope.assertEquals(-1f, fann.run(new float[] { 1, 1 })[0], .2f);
        fann.close();
    }
}
