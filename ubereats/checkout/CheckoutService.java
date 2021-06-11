package com.example.ubereats.checkout;

import com.example.ubereats.cart.Cart;

import java.util.List;

public class CheckoutService {

    private static final String EFECTIVO = "efectivo";

    private static CheckoutService checkoutService;
    private List<Cart> carts;
    private String orderDescription;
    private String restaurantId;
    private String paymentId;
    private String addressId;

    public CheckoutService() {
        this.carts = null;
        this.paymentId = null;
        this.orderDescription = null;
        this.addressId = null;
    }

    public static CheckoutService getInstance() {
        if (checkoutService == null)
            checkoutService = new CheckoutService();

        return checkoutService;
    }

    public void newCheckout(String restaurantId) {
        this.checkoutService = new CheckoutService();
        checkoutService.setRestaurantId(restaurantId);
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }
}
