class c14776145 {

    private void addPNMLFileToLibrary(File selected) {
        try {
            FileChannel srcChannel = new FileInputStream(selected.getAbsolutePath()).getChannel();
            FileChannel dstChannel = new FileOutputStream(new File(JavaCIPUnknownScope.matchingOrderXML).getParent() + "/" + selected.getName()).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
            JavaCIPUnknownScope.order.add(new ComponentDescription(false, selected.getName().replaceAll(".pnml", ""), 1.0));
            JavaCIPUnknownScope.updateComponentList();
        } catch (IORuntimeException ioe) {
            JOptionPane.showMessageDialog(JavaCIPUnknownScope.dialog, "Could not add the PNML file " + selected.getName() + " to the library!");
        }
    }
}
