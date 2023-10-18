class c3857069 {

    public void run(IAction action) {
        int style = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getStyle();
        Shell shell = new Shell((style & JavaCIPUnknownScope.SWT.MIRRORED) != 0 ? JavaCIPUnknownScope.SWT.RIGHT_TO_LEFT : JavaCIPUnknownScope.SWT.NONE);
        GraphicalViewer viewer = new ScrollingGraphicalViewer();
        viewer.createControl(shell);
        viewer.setEditDomain(new DefaultEditDomain(null));
        viewer.setRootEditPart(new ScalableFreeformRootEditPart());
        viewer.setEditPartFactory(new GraphicalPartFactory());
        viewer.setContents(JavaCIPUnknownScope.getContents());
        viewer.flush();
        int printMode = new PrintModeDialog(shell).open();
        if (printMode == -1)
            return;
        PrintDialog dialog = new PrintDialog(shell, JavaCIPUnknownScope.SWT.NULL);
        PrinterData data = dialog.open();
        if (data != null) {
            PrintGraphicalViewerOperation op = new PrintGraphicalViewerOperation(new Printer(data), viewer);
            op.setPrintMode(printMode);
            op.run(JavaCIPUnknownScope.selectedFile.getName());
        }
    }
}
