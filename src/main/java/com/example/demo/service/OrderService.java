package com.example.demo.service;

import com.example.demo.dto.color.ColorDto;
import com.example.demo.dto.item.ItemDto;
import com.example.demo.dto.itemdetail.ItemDetailDto;
import com.example.demo.dto.order.DateRangeRequest;
import com.example.demo.dto.order.OrderDto;
import com.example.demo.dto.order.OrderSaveDto;
import com.example.demo.dto.orderdeliverystatusdetail.OrderDeliveryStatusDetailDto;
import com.example.demo.dto.orderdetail.OrderDetailDto;
import com.example.demo.dto.response.Response;
import com.example.demo.entity.Bill;
import com.example.demo.entity.ItemDetail;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional

public class OrderService {
    private final OrderRepository repository;
    private final ModelMapper mapper;
    private final OrderDetailRepository orderDetailRepository;
    private final ItemCartRepository itemCartRepository;
    private final ItemDetailRepository itemDetailRepository;
    private final OrderDeliveryStatusDetailRepository orderDeliveryStatusDetailRepository;
    private final BillRepository billRepository;
    private final Integer deliveryStatus = 1;
    private final String desc = "PROCESSING";

    public ResponseEntity<List<OrderDto>> getAllOrder(Integer page,Integer size,String sort) {
        Integer offSet = (page-1)*size;
        List<Object[]> result = repository.getCountAndTotalOrderFee();
        int totalCount = ((Number) result.get(0)[0]).intValue();
        double totalFee = ((Number) result.get(0)[1]).doubleValue();
        List<Order> orders = repository.getAllOrder(size,offSet,sort);
        if (orders != null) {
            List<OrderDto> orderDtos = orders.stream().map(o -> {
                OrderDto orderDto = mapper.map(o, OrderDto.class);
                orderDto.setOrderDetails(o.getOrderDetails().stream().map(od -> {
                    OrderDetailDto orderDetailDto = mapper.map(od, OrderDetailDto.class);
                    ItemDetailDto itemDetailDto = mapper.map(od.getItemDetail(), ItemDetailDto.class);
                    ItemDto itemDto = mapper.map(od.getItemDetail().getItems(), ItemDto.class);
                    itemDetailDto.setItemDto(itemDto);
                    orderDetailDto.setItemDetailDto(itemDetailDto);
                    return orderDetailDto;
                }).toList());
                orderDto.setName(o.getName());
                orderDto.setPhone(o.getPhone());
                OrderDeliveryStatusDetailDto ODSDDto = OrderDeliveryStatusDetailDto.builder().deliveryStatusId(o.getOrderDeliveryStatusDetails().getDeliveryStatusId())
                        .orderId(o.getOrderDeliveryStatusDetails().getOrderId()).desc(o.getOrderDeliveryStatusDetails().getDesc())
                        .lastUpdateAt(o.getOrderDeliveryStatusDetails().getLastUpdateAt()).build();
                orderDto.setOrderDeliveryStatusDetails(ODSDDto);
                return orderDto;
            }).toList();
            return ResponseEntity.ok().header("X-Total-Order", String.valueOf(totalCount)).header("X-Total-Fee", String.valueOf(totalFee)).body(orderDtos);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<OrderDto> getOrderById(Integer orderId) {
        Order order = repository.getOrderById(orderId);
        if (order != null) {
            OrderDto orderDto = mapper.map(order, OrderDto.class);
            return new ResponseEntity<>(orderDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<OrderDto>> getOrderByCustomerId(Integer customerId,Integer page,Integer size,String sort) {
        Integer offSet = (page-1)*size;
        List<Object[]> result = repository.getTotalOrderCountByCustomer(customerId);
        int totalCount = ((Number) result.get(0)[0]).intValue();
        double totalFee = ((Number) result.get(0)[1]).doubleValue();
        System.out.println("=============>" + totalFee);
        List<Order> orders = repository.getOrderByCustomerId(customerId,size,offSet,sort);
        if (orders != null) {
            List<OrderDto> orderDtos = orders.stream().map(o -> {
                OrderDto orderDto = mapper.map(o, OrderDto.class);
                orderDto.setOrderDetails(o.getOrderDetails().stream().map(od -> {
                    OrderDetailDto orderDetailDto = mapper.map(od, OrderDetailDto.class);
                    ItemDetailDto itemDetailDto = mapper.map(od.getItemDetail(), ItemDetailDto.class);
                    ItemDto itemDto = mapper.map(od.getItemDetail().getItems(), ItemDto.class);
                    itemDetailDto.setItemDto(itemDto);
                    orderDetailDto.setItemDetailDto(itemDetailDto);
                    return orderDetailDto;
                }).toList());
                orderDto.setName(o.getName());
                orderDto.setPhone(o.getPhone());
                OrderDeliveryStatusDetailDto ODSDDto = OrderDeliveryStatusDetailDto.builder().deliveryStatusId(o.getOrderDeliveryStatusDetails().getDeliveryStatusId())
                        .orderId(o.getOrderDeliveryStatusDetails().getOrderId()).desc(o.getOrderDeliveryStatusDetails().getDesc())
                        .lastUpdateAt(o.getOrderDeliveryStatusDetails().getLastUpdateAt()).build();
                orderDto.setOrderDeliveryStatusDetails(ODSDDto);
                return orderDto;
            }).toList();
            return ResponseEntity.ok().header("X-Total-Order", String.valueOf(totalCount))
                    .header("X-Total-Fee",  String.format("%.0f", totalFee))
                    .body(orderDtos);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getOrderByDateRange(DateRangeRequest dateRangeRequest) {
        try{
            LocalDateTime startDateTime = dateRangeRequest.getStartDateAsDateTime();
            LocalDateTime endDateTime = dateRangeRequest.getEndDateAsDateTime();
            if(startDateTime.isAfter(endDateTime)){
                return new ResponseEntity<>(Response.builder().message("INVALID DATE TIME").build(),HttpStatus.BAD_REQUEST);
            }
            List<Order> orders = repository.getOrderByDateRange(startDateTime, endDateTime);
            if (orders != null) {
                List<OrderDto> orderDtos = orders.stream().map(o -> {
                    OrderDto orderDto = mapper.map(o, OrderDto.class);
                    orderDto.setOrderDetails(o.getOrderDetails().stream().map(od -> {
                        OrderDetailDto orderDetailDto = mapper.map(od, OrderDetailDto.class);
                        ItemDetailDto itemDetailDto = mapper.map(od.getItemDetail(), ItemDetailDto.class);
                        ItemDto itemDto = mapper.map(od.getItemDetail().getItems(), ItemDto.class);
                        itemDetailDto.setItemDto(itemDto);
                        orderDetailDto.setItemDetailDto(itemDetailDto);
                        return orderDetailDto;
                    }).toList());
                    orderDto.setName(o.getName());
                    orderDto.setPhone(o.getPhone());
                    OrderDeliveryStatusDetailDto ODSDDto = OrderDeliveryStatusDetailDto.builder().deliveryStatusId(o.getOrderDeliveryStatusDetails().getDeliveryStatusId())
                            .orderId(o.getOrderDeliveryStatusDetails().getOrderId()).desc(o.getOrderDeliveryStatusDetails().getDesc())
                            .lastUpdateAt(o.getOrderDeliveryStatusDetails().getLastUpdateAt()).build();
                    orderDto.setOrderDeliveryStatusDetails(ODSDDto);
                    return orderDto;
                }).toList();
                return new ResponseEntity<>(orderDtos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void saveOrder(OrderSaveDto orderSaveDto, Integer statusPayment) {
        Order order = Order.builder().deliveryAddress(orderSaveDto.getDeliveryAddress()).totalFee(orderSaveDto.getTotalFee())
                .deliveryDate(orderSaveDto.getDeliveryDate()).createAt(orderSaveDto.getCreateAt())
                .customerId(orderSaveDto.getCustomerId()).payMethodId(orderSaveDto.getPaymentMethodId())
                .maGiaoDich(orderSaveDto.getMaGiaoDich())
                .phone(orderSaveDto.getPhone())
                .name(orderSaveDto.getName())
                .build();
        order.setStatusPayment(statusPayment);
        System.out.println(order.toString());
        if (repository.saveOrder(order) == 1) {
            Order OrderLastIndexOf = repository.getOrderLastIndexOf();
            Integer idLastIndexOf = OrderLastIndexOf.getId();
            orderSaveDto.getOrderDetailDtos().forEach(e -> {
                OrderDetail orderDetail = mapper.map(e, OrderDetail.class);
                orderDetailRepository.saveOrderDetail(idLastIndexOf, orderDetail.getItemDetail().getId()
                        , orderDetail.getAmount(), orderDetail.getNote());
                itemCartRepository.removeItemInCarts( e.getItemCartId());
                ItemDetail itemDetail = itemDetailRepository.getItemDetailById(orderDetail.getItemDetail().getId());
                itemDetailRepository.updateAmountById(orderDetail.getItemDetail().getId(), itemDetail.getAmount() - orderDetail.getAmount());
            });
            orderDeliveryStatusDetailRepository.saveODSD(deliveryStatus, idLastIndexOf, orderSaveDto.getCreateAt(), desc);
            billRepository.saveBill(new Bill(orderSaveDto.getCreateAt(), idLastIndexOf, orderSaveDto.getTotalFee()));
        }

    }

    public ResponseEntity<?> updateStatusPayment(Integer id) {
        if (repository.updateStatusPayment(id) == 1) {
            return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Response.builder().message("ID IS NOT EXIST").build(), HttpStatus.BAD_REQUEST);
        }
    }

}
