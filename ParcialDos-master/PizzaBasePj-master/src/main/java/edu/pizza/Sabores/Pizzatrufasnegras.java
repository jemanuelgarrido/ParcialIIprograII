package edu.pizza.Sabores;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

public class Pizzatrufasnegras extends Pizza {

    public Pizzatrufasnegras(String name, Topping... toppings) {
        super(name, toppings);
        Ingredientestrufanegra();
    }

    public void Ingredientestrufanegra() {
        addTopping(new Topping("Mozzarella doble", 66));
        addTopping(new Topping("Queso parmesano de lascas", 50));
        addTopping(new Topping("Trufa negra (Tuber Melanosporum", 122));
    }
}
