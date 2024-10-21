public class OrderedRequestContextFilter extends RequestContextFilter implements OrderedFilter {

	private int order = REQUEST_WRAPPER_FILTER_MAX_ORDER - 105;

	@Override
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
