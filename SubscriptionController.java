
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SubscriptionController {
	private OrderService orderService;

	static Logger log = Logger.getLogger(SubscriptionController.class.getName());

	private static final String SUBSTITUTED_STRING = "... (truncated) ... ";
	private static final int SUBSTITUTED_STRING_LENGTH = SUBSTITUTED_STRING.length();
	
	@RequestMapping (value = "/subscribe" , method = RequestMethod. GET )
	public String subscribe(HttpServletRequest request ) {
		String orderDetails = getOrderDetails( request );
		String truncatedOrderDetails = truncate( orderDetails, 250 );
		log( truncatedOrderDetails );
		return orderDetails;
	}

	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	private void log(String truncatedOrderDetails) {
		log.info(truncatedOrderDetails);
	}

	private String truncate(String orderDetails, int length) {
		String truncatedOrderDetails = orderDetails;
		if (orderDetails.length() > length && length > SUBSTITUTED_STRING_LENGTH) {
            		truncatedOrderDetails = orderDetails.substring(0, length/2 - SUBSTITUTED_STRING_LENGTH/2) + SUBSTITUTED_STRING + orderDetails.substring(orderDetails.length() - length/2 + SUBSTITUTED_STRING_LENGTH/2);
        	} 
            	return truncatedOrderDetails;
	}

	private String getOrderDetails(HttpServletRequest request) {
		return orderService.getOrderDetails(request.getParameter("orderId"));
	}
}
