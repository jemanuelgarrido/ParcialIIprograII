
package edu.forms;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;
import edu.pizza.Sabores.Pizzafideos;
import edu.pizza.Sabores.Pizzatrufasnegras;
import edu.pizza.Sabores.PizzaPortobello;
import edu.pizza.Sabores.Pizzapaella;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class frmPizza {

    private JPanel jPanelPincipal;
    private JComboBox ComboPrepararPizza;
    private JTextField txtPizza;
    private JButton btnAddIngrediente;
    private JLabel lblTotal;
    private JList lista1;
    private JButton preparaPizzaButton;
    private JList list2;
    private JComboBox comboTipoPizza;
    private JButton buttonRemoverIngredientes;
    private JComboBox ComboRemoverIngredientes;
    private JRadioButton smalRadioButton;
    private JRadioButton medianaRadioButton;
    private JRadioButton grandeRadioButton;
    private JRadioButton extraGrandeRadioButton;
    private List<Topping> ingredientes=new ArrayList<>();
    private DefaultListModel modeloLista=new DefaultListModel();
    private DefaultListModel<String> modeloLista2 = new DefaultListModel<>();
    private List<String> tiposPizza=new ArrayList<>();

    private Map<String, List<Topping>> pizzaToppings = new HashMap<>();


    private double total;

    private double precioAdicional = 0;
    public frmPizza() {

        cargarTipoPizza();

        cargarToppings();
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(smalRadioButton);
        sizeGroup.add(medianaRadioButton);
        sizeGroup.add(grandeRadioButton);
        sizeGroup.add(extraGrandeRadioButton);

        // Establece Small como seleccionado por defecto
        smalRadioButton.setSelected(true);

        comboTipoPizza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                modeloLista2.clear();

                // Obtén el tipo de pizza seleccionado por el usuario
                String tipoPizzaSeleccionada = (String) comboTipoPizza.getSelectedItem();

                // Obtén la lista de toppings asociados a ese tipo de pizza desde el mapa pizzaToppings
                List<Topping> toppingsPizza = pizzaToppings.get(tipoPizzaSeleccionada);


                // Limpia la lista actual
                modeloLista.clear();


                // Agrega los toppings de la pizza seleccionada a la lista
                for (Topping topping : toppingsPizza) {
                    modeloLista.addElement(topping.toString());
                    lista1.setModel(modeloLista);
                }

                // Calcula el total de los precios de los toppings
                total = toppingsPizza.stream().mapToDouble(Topping::getPrecio).sum();
                lblTotal.setText(String.valueOf(total));
                cargarIngredientesEnComboRemover();
            }
        });





        btnAddIngrediente.addActionListener(new ActionListener() {//agragar ingredinetes
            @Override
            public void actionPerformed(ActionEvent e) {
                modeloLista2.clear();
                Topping ingrediente=(Topping)  ComboPrepararPizza.getSelectedItem();

                modeloLista.addElement(ingrediente.toString());
                lista1.setModel(modeloLista);
                total +=ingrediente.getPrecio();
                lblTotal.setText(String.valueOf(total));
                DefaultComboBoxModel<String> comboRemoverModel = (DefaultComboBoxModel<String>) ComboRemoverIngredientes.getModel();
                comboRemoverModel.addElement(ingrediente.toString());

                Pizza pizza =new Pizza(txtPizza.getName());
                Topping topi;
                for (int i=0; i < lista1.getModel().getSize(); i++){

                    topi=(Topping) lista1.getModel().getElementAt(i);
                    pizza.addTopping(topi);
                }

            }

        });

        preparaPizzaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararPizza();
            }

        });




        buttonRemoverIngredientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificar si se ha seleccionado un ingrediente para remover
                if (ComboRemoverIngredientes.getSelectedItem() != null) {
                    String ingredienteSeleccionado = (String) ComboRemoverIngredientes.getSelectedItem();

                    // Obtener el precio del ingrediente seleccionado
                    String[] parts = ingredienteSeleccionado.split("Q");
                    double precioIngrediente = Double.parseDouble(parts[1].trim());

                    // Quitar el ingrediente de lista1
                    modeloLista.removeElement(ingredienteSeleccionado);
                    lista1.setModel(modeloLista);

                    // Restar el precio del ingrediente a total
                    total -= precioIngrediente;
                    lblTotal.setText(String.valueOf(total));


                    DefaultComboBoxModel<String> comboRemoverModel = (DefaultComboBoxModel<String>) ComboRemoverIngredientes.getModel();
                    comboRemoverModel.removeElement(ingredienteSeleccionado);
                }
            }
        });


        smalRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (smalRadioButton.isSelected()) {
                    precioAdicional = 0;
                    actualizarTotal();
                }
            }
        });

        medianaRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (medianaRadioButton.isSelected()) {
                    precioAdicional = total * 0.2; // Aumenta el precio en un 20%
                    actualizarTotal();
                }
            }
        });

        grandeRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (grandeRadioButton.isSelected()) {
                    precioAdicional = total * 0.3; // Aumenta el precio en un 30%
                    actualizarTotal();
                }
            }
        });

        extraGrandeRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (extraGrandeRadioButton.isSelected()) {
                    precioAdicional = total * 0.4; // Aumenta el precio en un 40%
                    actualizarTotal();
                }
            }
        });



    }

    public JPanel getjPanelPincipal() {
        return jPanelPincipal;
    }
    //cargar toppings

    private void cargarToppings(){
        ingredientes.add(new Topping("tomate", 30));
        ingredientes.add(new Topping("champiniones", 30));
        ingredientes.add(new Topping("salsa", 30));
        ingredientes.add(new Topping("carne", 30));

        DefaultComboBoxModel model= new DefaultComboBoxModel(ingredientes.toArray());
        ComboPrepararPizza.setModel(model);

    }
    private void cargarTipoPizza(){
        Pizzafideos pizzadesobras = new Pizzafideos("Pizza de Sobras");
        tiposPizza.add(pizzadesobras.getName());

        Pizzatrufasnegras pizzaexotica = new Pizzatrufasnegras("Pizza Exotica");
        tiposPizza.add(pizzaexotica.getName());

        PizzaPortobello pizzasushi = new PizzaPortobello("Pizza Sushi");
        tiposPizza.add(pizzasushi.getName());

        Pizza pizz= new Pizza("Pizza para crear");
        tiposPizza.add(pizz.getName());
        Pizzapaella pizzadedetacos=new Pizzapaella("Pizza de Tacos");

        DefaultComboBoxModel model = new DefaultComboBoxModel(tiposPizza.toArray());
        comboTipoPizza.setModel(model);

        pizzaToppings.put(pizzadesobras.getName(), pizzadesobras.getToppings());
        pizzaToppings.put(pizzaexotica.getName(), pizzaexotica.getToppings());
        pizzaToppings.put(pizzasushi.getName(), pizzasushi.getToppings());
        pizzaToppings.put(pizz.getName(),pizz.getToppings());
        pizzaToppings.put(pizzadedetacos.getName(),pizzadedetacos.getToppings());




        }

    private void cargarIngredientesEnComboRemover() {
        ComboRemoverIngredientes.removeAllItems(); // Limpia los elementos existentes en ComboRemoverIngredientes
        int itemCount = modeloLista.getSize(); // Obtiene la cantidad de elementos en modeloLista

        // Agrega cada elemento de modeloLista a ComboRemoverIngredientes
        for (int i = 0; i < itemCount; i++) {
            String ingrediente = (String) modeloLista.getElementAt(i);
            ComboRemoverIngredientes.addItem(ingrediente);
        }
    }

    private void actualizarTotal() {
        double nuevoTotal = total + precioAdicional;
        lblTotal.setText(String.valueOf(nuevoTotal));
    }

    private void prepararPizza() {
        String tipoPizzaSeleccionada = (String) comboTipoPizza.getSelectedItem();
        if (tipoPizzaSeleccionada == null) {
            mostrarError("Selecciona un tipo de pizza antes de prepararla.");
            return;
        }

        if (modeloLista.isEmpty()) {
            mostrarError("Agrega al menos un ingrediente a la pizza antes de prepararla.");
            return;
        }

        String nombrePizza = txtPizza.getText().trim();
        if (nombrePizza.isEmpty()) {
            mostrarError("Ingresa un nombre para la pizza antes de prepararla.");
            return;
        }

        Pizza pizza = new Pizza(tipoPizzaSeleccionada);
        modeloLista2.clear();

        List<Topping> ingredientesPizza = pizzaToppings.get(tipoPizzaSeleccionada);

            modeloLista2.addElement("Preparando.... " + tipoPizzaSeleccionada);
            modeloLista2.addElement("Agregando toppings");

            for (int i = 0; i < lista1.getModel().getSize(); i++) {
                String ingrediente = (String) lista1.getModel().getElementAt(i);
                String nombreIngrediente = ingrediente.split("Q")[0].trim();
                modeloLista2.addElement("- " + nombreIngrediente);
            }

            modeloLista2.addElement("Pizza lista!");

        list2.setModel(modeloLista2);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }












}





