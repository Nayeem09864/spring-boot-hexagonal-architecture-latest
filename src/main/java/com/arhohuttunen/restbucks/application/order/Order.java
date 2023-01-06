package com.arhohuttunen.restbucks.application.order;

import com.arhohuttunen.restbucks.shared.Location;
import com.arhohuttunen.restbucks.shared.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Order {
    private UUID id = UUID.randomUUID();
    private final Location location;
    private final List<OrderItem> items;
    private Status status = Status.PAYMENT_EXPECTED;

    public boolean isUnpaid() {
        return status == Status.PAYMENT_EXPECTED;
    }

    public boolean isComplete() {
        return status == Status.TAKEN;
    }

    public boolean canBeCancelled() {
        return status == Status.PAYMENT_EXPECTED;
    }

    public BigDecimal getCost() {
        return items.stream().map(OrderItem::getCost).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    public Order update(Order order) {
        if (status == Status.PAID) {
            throw new IllegalStateException("Order is already paid");
        }
        return new Order(id, order.getLocation(), order.getItems(), status);
    }

    public Order markPaid() {
        if (status != Status.PAYMENT_EXPECTED) {
            throw new IllegalStateException("Order is already paid");
        }
        status = Status.PAID;
        return this;
    }

    public Order markBeingPrepared() {
        if (status != Status.PAID) {
            throw new IllegalStateException("Order is not paid");
        }
        status = Status.PREPARING;
        return this;
    }

    public Order markPrepared() {
        if (status != Status.PREPARING) {
            throw new IllegalStateException("Order is not being prepared");
        }
        status = Status.READY;
        return this;
    }

    public Order markTaken() {
        if (status != Status.READY) {
            throw new IllegalStateException("Order is not ready");
        }
        status = Status.TAKEN;
        return this;
    }
}
