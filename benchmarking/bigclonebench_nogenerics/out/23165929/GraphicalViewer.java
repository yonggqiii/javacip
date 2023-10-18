// added by JavaCIP
public interface GraphicalViewer {

    public abstract void setKeyHandler(GraphicalViewerKeyHandler arg0);

    public abstract void setContents(Block arg0);

    public abstract void setContextMenu(ContextMenuProvider arg0);

    public abstract void createControl(c23165929 arg0);

    public abstract void setRootEditPart(ScalableRootEditPart arg0);

    public abstract void setEditPartFactory(BlockEditPartFactory arg0);
}
