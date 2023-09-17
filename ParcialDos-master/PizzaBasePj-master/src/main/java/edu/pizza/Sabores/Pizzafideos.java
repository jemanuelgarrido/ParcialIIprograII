package edu.pizza.Sabores;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

public class Pizzafideos extends Pizza {

    public Pizzafideos(String name, Topping... toppings) {
        super(name, toppings);
        Ingredientesfideos();
    }

    public void Ingredientesfideos() {
        addTopping(new Topping("Fideos largos", 20));
        addTopping(new Topping("Pimienta negra", 3));
        addTopping(new Topping("Salsa de tomate", 20));
        addTopping(new Topping("Mozzarella", 25));
        addTopping(new Topping("Crema ", 5));
        addTopping(new Topping("Cebolla", 5));
        addTopping(new Topping("Tomate", 5));
        addTopping(new Topping("Chile Jalapeño", 3));
        addTopping(new Topping("Carne", 33));


    }
}

