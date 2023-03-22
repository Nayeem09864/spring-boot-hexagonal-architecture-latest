package com.arhohuttunen.coffeeshop.application.order;

import com.arhohuttunen.coffeeshop.shared.Drink;
import com.arhohuttunen.coffeeshop.shared.Location;
import com.arhohuttunen.coffeeshop.shared.Milk;
import com.arhohuttunen.coffeeshop.shared.Size;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class OrderTest {

    private static Stream<Arguments> drinkCosts() {
        return Stream.of(
                arguments(1, Size.SMALL, BigDecimal.valueOf(4.0)),
                arguments(1, Size.LARGE, BigDecimal.valueOf(5.0)),
                arguments(2, Size.SMALL, BigDecimal.valueOf(8.0))
        );
    }

    @ParameterizedTest(name = "{0} drinks of size {1} cost {2}")
    @MethodSource("drinkCosts")
    void creatingOrderReturnsCost(int quantity, Size size, BigDecimal expectedCost) {
        var order = new Order(Location.TAKE_AWAY, List.of(
                new LineItem(Drink.LATTE, quantity, Milk.WHOLE, size)
        ));

        assertThat(order.getCost()).isEqualTo(expectedCost);
    }

    @Test
    void creatingOrderWithMultipleItemsReturnsCost() {
        var order = new Order(Location.TAKE_AWAY, List.of(
                new LineItem(Drink.LATTE, 1, Milk.SKIMMED, Size.LARGE),
                new LineItem(Drink.ESPRESSO, 1, Milk.SOY, Size.SMALL)
        ));

        assertThat(order.getCost()).isEqualTo(BigDecimal.valueOf(9.0));
    }
}
