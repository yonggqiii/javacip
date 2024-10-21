public class OrderedFormContentFilter extends FormContentFilter implements OrderedFilter {

	public static final int DEFAULT_ORDER = FormContentFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER - 9900;

	private int order = DEFAULT_ORDER;

	@Override
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
