package edu.pizza.Sabores;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

public class PizzaPortobello extends Pizza {

    public PizzaPortobello(String name, Topping... toppings) {
        super(name, toppings);
        Ingredientesportobello();
    }

    public void Ingredientesportobello() {
        addTopping(new Topping("Portobello (Grandes)", 90));
        addTopping(new Topping("Morron ", 15));
        addTopping(new Topping("Aguacate", 12));
        addTopping(new Topping("Jitomate", 8));
        addTopping(new Topping("Aceitunas verdes o negras", 34));
        addTopping(new Topping("Tofu", 90));
    }
}
