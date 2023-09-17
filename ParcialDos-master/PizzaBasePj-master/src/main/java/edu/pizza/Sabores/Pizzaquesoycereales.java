package edu.pizza.Sabores;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

public class Pizzaquesoycereales extends Pizza {

    public Pizzaquesoycereales(String name, Topping... toppings) {
        super(name, toppings);
        Ingredientesdulcesald();
    }

    public void Ingredientesdulcesald() {
        addTopping(new Topping("Queso mozzarella", 30));
        addTopping(new Topping("Queso azul", 45));
        addTopping(new Topping("Fresas", 18));
        addTopping(new Topping("Chocolate", 25));
        addTopping(new Topping("Cereal chocomilk", 19));
        addTopping(new Topping("Cereal simple zucaritas", 19));
        addTopping(new Topping("Nutella", 35));
        addTopping(new Topping("Mani ", 25));

    }
}
