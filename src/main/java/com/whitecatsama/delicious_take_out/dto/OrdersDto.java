package com.whitecatsama.delicious_take_out.dto;


import com.whitecatsama.delicious_take_out.domain.OrderDetail;
import com.whitecatsama.delicious_take_out.domain.Orders;
import lombok.Data;

import java.util.List;


@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
