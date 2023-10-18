// added by JavaCIP
public interface GraphicalViewer {

    public abstract void setKeyHandler(KeyHandler arg0);

    public abstract void setContents(boolean arg0);

    public abstract UNKNOWN_105 getControl();

    public abstract void setContextMenu(ContextMenuProvider arg0);

    public abstract void createControl(Composite arg0);

    public abstract void addDropTargetListener(DataEditDropTargetListner arg0);

    public abstract void setRootEditPart(ScalableFreeformRootEditPart arg0);

    public abstract void setEditPartFactory(boolean arg0);
}
