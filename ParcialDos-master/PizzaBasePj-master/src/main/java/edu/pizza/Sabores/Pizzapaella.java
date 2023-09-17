package edu.pizza.Sabores;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

public class Pizzapaella extends Pizza {

    public Pizzapaella(String name, Topping... toppings) {
        super(name, toppings);
        Ingredientesdulce();
    }

    public void Ingredientesdulce() {
        addTopping(new Topping("Fresa", 50));
        addTopping(new Topping("Nutella", 35));
        addTopping(new Topping("Azucar", 5));
        addTopping(new Topping("Miel", 10));
        addTopping(new Topping("Chocolate", 30));
        addTopping(new Topping("Jalea de fresa", 25));
    }

}
