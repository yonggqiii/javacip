class c21517335 {

    public void run() {
        Shell shell = new Shell(JavaCIPUnknownScope.display);
        shell.setLayout(new GridLayout(1, false));
        ERDiagramEditPartFactory editPartFactory = new ERDiagramEditPartFactory();
        JavaCIPUnknownScope.viewer = new ScrollingGraphicalViewer();
        JavaCIPUnknownScope.viewer.setControl(new FigureCanvas(shell));
        ScalableFreeformRootEditPart rootEditPart = new PagableFreeformRootEditPart(JavaCIPUnknownScope.diagram);
        JavaCIPUnknownScope.viewer.setRootEditPart(rootEditPart);
        JavaCIPUnknownScope.viewer.setEditPartFactory(editPartFactory);
        JavaCIPUnknownScope.viewer.setContents(JavaCIPUnknownScope.diagram);
    }
}
