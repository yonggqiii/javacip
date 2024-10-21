public class MustacheViewResolver extends AbstractTemplateViewResolver {

	private final MustacheCompiler compiler;

	private String charset;

	public MustacheViewResolver() {
		this.compiler = Mustache.compiler();
		setViewClass(requiredViewClass());
	}

	public MustacheViewResolver(MustacheCompiler compiler) {
		this.compiler = compiler;
		setViewClass(requiredViewClass());
	}

	@Override
	protected Class<?> requiredViewClass() {
		return MustacheView.class;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) { 
		MustacheView view = (MustacheView) super.buildView(viewName);
		view.setCompiler(this.compiler);
		view.setCharset(this.charset);
		return view;
	}

	@Override
	protected AbstractUrlBasedView instantiateView() {
		return (getViewClass() == MustacheView.class) ? new MustacheView() : super.instantiateView();
	}

}
