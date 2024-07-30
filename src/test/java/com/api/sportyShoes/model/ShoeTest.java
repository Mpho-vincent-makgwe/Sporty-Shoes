package com.api.sportyShoes.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ShoeTest {

    @Test
    public void testNoArgsConstructor() {
        Shoe shoe = new Shoe(0, null, null, 0);
        assertNotNull(shoe);
    }

    @Test
    public void testAllArgsConstructor() {
        Shoe shoe = new Shoe(1, "Running Shoe", "Sports", 99.99);
        assertEquals(1, shoe.getId());
        assertEquals("Running Shoe", shoe.getName());
        assertEquals("Sports", shoe.getCategory());
        assertEquals(99.99, shoe.getPrice());
    }

    @Test
    public void testSettersAndGetters() {
        Shoe shoe = new Shoe(0, null, null, 0);
        shoe.setId(2);
        shoe.setName("Basketball Shoe");
        shoe.setCategory("Sports");
        shoe.setPrice(129.99);

        assertEquals(2, shoe.getId());
        assertEquals("Basketball Shoe", shoe.getName());
        assertEquals("Sports", shoe.getCategory());
        assertEquals(129.99, shoe.getPrice());
    }

    @Test
    public void testToString() {
        Shoe shoe = new Shoe(3, "Tennis Shoe", "Sports", 79.99);
        String expected = "Shoe{id=3, name='Tennis Shoe', category='Sports', price=79.99}";
        assertEquals(expected, shoe.toString());
    }
}
