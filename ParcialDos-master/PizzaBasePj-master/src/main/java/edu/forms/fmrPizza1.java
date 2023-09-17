package edu.forms;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;
import edu.pizza.Sabores.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fmrPizza1 {
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
    private JRadioButton ballyRadioButton;
    private JRadioButton mexiRadioButton;
    private JRadioButton ventiRadioButton;
    private JRadioButton ventitwoRadioButton;
    private List<Topping> ingredientes=new ArrayList<>();
    private DefaultListModel modeloLista=new DefaultListModel();
    private DefaultListModel<String> modeloLista2 = new DefaultListModel<>();
    private List<String> tiposPizza=new ArrayList<>();

    private Map<String, List<Topping>> pizzaToppings = new HashMap<>();


    private double total;

    private double precioAdicional = 0;
    private JPanel panel1;

    private void aplicarBorde(JComponent componente, Border borde) {
        componente.setBorder(borde);
    }


    public fmrPizza1() {

        Border bordeNegroGrueso = BorderFactory.createLineBorder(Color.BLACK, 2);

        // Aplicar el borde a todos los componentes principales del formulario
        aplicarBorde(jPanelPincipal, bordeNegroGrueso);
        //aplicarBorde(ComboPrepararPizza, bordeNegroGrueso);
        //aplicarBorde(txtPizza, bordeNegroGrueso);
        aplicarBorde(btnAddIngrediente, bordeNegroGrueso);
        //aplicarBorde(lblTotal, bordeNegroGrueso);
        // aplicarBorde(lista1, bordeNegroGrueso);
        aplicarBorde(preparaPizzaButton, bordeNegroGrueso);
        //aplicarBorde(list2, bordeNegroGrueso);
        //aplicarBorde(comboTipoPizza, bordeNegroGrueso);
        aplicarBorde(buttonRemoverIngredientes, bordeNegroGrueso);
        //aplicarBorde(ComboRemoverIngredientes, bordeNegroGrueso);
        aplicarBorde(ballyRadioButton, bordeNegroGrueso);
        aplicarBorde(mexiRadioButton, bordeNegroGrueso);
        aplicarBorde(ventiRadioButton, bordeNegroGrueso);
        aplicarBorde(ventitwoRadioButton, bordeNegroGrueso);



        cargarTipoPizza();

        cargarToppings();
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(ballyRadioButton);
        sizeGroup.add(mexiRadioButton);
        sizeGroup.add(ventiRadioButton);
        sizeGroup.add(ventitwoRadioButton);

        // Establece pequeño como seleccionado por defecto
        ballyRadioButton.setSelected(true);

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
                txtPizza.setText(tipoPizzaSeleccionada);
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


        ballyRadioButton.addChangeListener(new ChangeListener() {
            @Override

            public void stateChanged(ChangeEvent e) {
                if (ballyRadioButton.isSelected()) {
                    precioAdicional = 0;
                    actualizarTotal();
                }
            }
        });

        mexiRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (mexiRadioButton.isSelected()) {
                    precioAdicional = total * 0.3; // Aumenta el precio en un 30%
                    actualizarTotal();
                }
            }
        });

        ventiRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (ventiRadioButton.isSelected()) {
                    precioAdicional = total * 0.4; // Aumenta el precio en un 40%
                    actualizarTotal();
                }
            }
        });

        ventitwoRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (ventitwoRadioButton.isSelected()) {
                    precioAdicional = total * 0.5; // Aumenta el precio en un 50%
                    actualizarTotal();
                }
            }
        });



    }

    public JPanel getjPanelPincipal1() {
        return jPanelPincipal;
    }
    //cargar toppings

    private void cargarToppings(){
        ingredientes.add(new Topping("Salsa de tomate", 20));
        ingredientes.add(new Topping("Hongos", 45));
        ingredientes.add(new Topping("Peperoni", 23));
        ingredientes.add(new Topping("Jamon", 24));
        ingredientes.add(new Topping("Salsa de la casa", 22));
        ingredientes.add(new Topping("Ajo", 3));
        ingredientes.add(new Topping("Cilantro", 1));
        ingredientes.add(new Topping("Queso Mozarrella", 12));
        ingredientes.add(new Topping("Mozzarella xtra", 15));
        ingredientes.add(new Topping("Pescado", 35));
        ingredientes.add(new Topping("Camarones", 42));
        ingredientes.add(new Topping("Pimienta negra", 5));
        ingredientes.add(new Topping("Aceitunas negras", 9));
        ingredientes.add(new Topping("Aceitunas verdes", 7));
        ingredientes.add(new Topping("Tofu", 24));
        ingredientes.add(new Topping("Portobello", 34));
        ingredientes.add(new Topping("Chile Jalapeño", 6));
        ingredientes.add(new Topping("Salchicha", 8));
        ingredientes.add(new Topping("Queso en polvo", 1));


        DefaultComboBoxModel model= new DefaultComboBoxModel(ingredientes.toArray());
        ComboPrepararPizza.setModel(model);

    }
    private void cargarTipoPizza(){
        Pizzafideos pizzafideos = new Pizzafideos("Pizza de Fideos con Salsa");
        tiposPizza.add(pizzafideos.getName());

        Pizzatrufasnegras pizzatrufasnegras = new Pizzatrufasnegras("Pizza con trufa negra");
        tiposPizza.add(pizzatrufasnegras.getName());

        PizzaPortobello pizzaPortobello = new PizzaPortobello("De portobello en tempura y tofu");
        tiposPizza.add(pizzaPortobello.getName());
        Pizzapaella pizzapaella = new Pizzapaella("Paella (Pizza de fresa)");
        tiposPizza.add(pizzapaella.getName());
        Pizza pizzaPersonal= new Pizza("Pizza personal");
        tiposPizza.add(pizzaPersonal.getName());
        Pizzaquesoycereales pizzaquesoycereales = new Pizzaquesoycereales("Pizza Delumg ( queso y cereleales)");
        tiposPizza.add(pizzaquesoycereales.getName());

        DefaultComboBoxModel model = new DefaultComboBoxModel(tiposPizza.toArray());
        comboTipoPizza.setModel(model);

        pizzaToppings.put(pizzafideos.getName(), pizzafideos.getToppings());
        pizzaToppings.put(pizzatrufasnegras.getName(), pizzatrufasnegras.getToppings());
        pizzaToppings.put(pizzaPortobello.getName(), pizzaPortobello.getToppings());
        pizzaToppings.put(pizzapaella.getName(), pizzapaella.getToppings());
        pizzaToppings.put(pizzaquesoycereales.getName(), pizzaquesoycereales.getToppings());
        pizzaToppings.put(pizzaPersonal.getName(),pizzaPersonal.getToppings());




    }

    private void cargarIngredientesEnComboRemover() {
        ComboRemoverIngredientes.removeAllItems();
        int itemCount = modeloLista.getSize();

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
        String nombrePizza = txtPizza.getText().trim();
        if (nombrePizza.isEmpty()) {
            mostrarError("No tienes nombre o que?");
            return;
        }
        String tipoPizzaSeleccionada = (String) comboTipoPizza.getSelectedItem();
        if (tipoPizzaSeleccionada == null) {
            mostrarError("Aun no seleccionas ninguna pizza, pizza fantasma o ke.");
            return;
        }

        if (modeloLista.isEmpty()) {
            mostrarError("Agrega ingredientes, la pizza no se puede preparar sola o te vendo solo el pan?");
            return;
        }

        Pizza pizza = new Pizza(tipoPizzaSeleccionada);
        modeloLista2.clear();

        List<Topping> ingredientesPizza = pizzaToppings.get(tipoPizzaSeleccionada);

        modeloLista2.addElement("Preparanding.... ");
        modeloLista2.addElement("Charging.... " );
        modeloLista2.addElement("toppings");

        for (int i = 0; i < lista1.getModel().getSize(); i++) {
            String ingrediente = (String) lista1.getModel().getElementAt(i);
            String nombreIngrediente = ingrediente.split("Querpo")[0].trim();
            modeloLista2.addElement("- " + nombreIngrediente);
        }

        modeloLista2.addElement("Pizza pedorra");
        modeloLista2.addElement("Tu pizza esta fea pero lista, se mas aestetic eh!");


        list2.setModel(modeloLista2);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
