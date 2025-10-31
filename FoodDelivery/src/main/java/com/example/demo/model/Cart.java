package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Cart
{
    private List<MenuItem> items = new ArrayList<>();
    private double total = 0.0;
    public void addToCart(MenuItem item)
    {
        items.add(item);
        total += item.getPrice().doubleValue();
    }
    public boolean removeFromCart(MenuItem item)
    {
        for (MenuItem menuItem : items)
        {
            if (menuItem.getItem_id() == item.getItem_id()) {
                items.remove(menuItem);
                total -= item.getPrice().doubleValue();
                return true;
            }
        }

        return false;

    }
    public List<MenuItem> getItems()
    {
        return items;
    }
    public void clearCart()
    {
        items.clear();
    }
}
