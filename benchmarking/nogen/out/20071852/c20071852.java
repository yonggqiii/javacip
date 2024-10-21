class c20071852 {

    void write() throws IORuntimeException {
        if (!JavaCIPUnknownScope.allowUnlimitedArgs && JavaCIPUnknownScope.args != null && JavaCIPUnknownScope.args.length > 1)
            throw new IllegalArgumentRuntimeException("Only one argument allowed unless allowUnlimitedArgs is enabled");
        String shebang = "#!" + JavaCIPUnknownScope.interpretter;
        for (int i = 0; i < JavaCIPUnknownScope.args.length; i++) {
            shebang += " " + JavaCIPUnknownScope.args[i];
        }
        shebang += '\n';
        IOUtils.copy(new StringReader(shebang), JavaCIPUnknownScope.outputStream);
    }
}
