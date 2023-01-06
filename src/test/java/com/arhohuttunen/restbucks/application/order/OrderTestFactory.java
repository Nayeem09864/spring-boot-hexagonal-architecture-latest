package com.arhohuttunen.restbucks.application.order;

import com.arhohuttunen.restbucks.shared.Drink;
import com.arhohuttunen.restbucks.shared.Location;
import com.arhohuttunen.restbucks.shared.Milk;
import com.arhohuttunen.restbucks.shared.Size;

import java.util.List;

public class OrderTestFactory {
    public static Order anOrder() {
        return new Order(Location.TAKE_AWAY, List.of(new OrderItem(Drink.LATTE, 1, Milk.WHOLE, Size.LARGE)));
    }

    public static Order aPaidOrder() {
        return anOrder()
                .markPaid();
    }

    public static Order anOrderInPreparation() {
        return aPaidOrder()
                .markBeingPrepared();
    }

    public static Order aReadyOrder() {
        return anOrderInPreparation()
                .markPrepared();
    }
}
