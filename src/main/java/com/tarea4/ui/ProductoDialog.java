package com.tarea4.ui;

import com.tarea4.dao.ProductoDAO;
import com.tarea4.model.Producto;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

public class ProductoDialog extends JDialog {

    private final ProductosFrame principal;
    private final ProductoDAO dao;
    private final Producto producto;
    private final JTextField nombre = new JTextField();
    private final JTextField marca = new JTextField();
    private final JTextField categoria = new JTextField();
    private final JTextField precio = new JTextField();
    private final JTextField cantidad = new JTextField();

    public ProductoDialog(JFrame parent, ProductoDAO dao, Producto producto) {
        super(parent, true);
        this.principal = (ProductosFrame) parent;
        this.dao = dao;
        this.producto = producto;
        setTitle(producto == null ? "Nuevo producto" : "Producto seleccionado");
        setSize(450, 420);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 52, 25, 52));
        panel.setBackground(Color.WHITE);
        JLabel titulo = new JLabel(producto == null ? "NUEVO PRODUCTO" : "PRODUCTO SELECCIONADO", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 21));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1;
        c.insets = new Insets(3, 0, 3, 0);
        agregar(formulario, c, 0, "Nombre", nombre);
        agregar(formulario, c, 2, "Marca", marca);
        agregar(formulario, c, 4, "Categoría", categoria);
        agregar(formulario, c, 6, "Precio", precio);
        agregar(formulario, c, 8, "Cantidad Disponible", cantidad);
        panel.add(formulario, BorderLayout.CENTER);

        if (producto != null) cargarProducto();
        JPanel botones = new JPanel(new GridLayout(1, producto == null ? 2 : 3, 10, 0));
        botones.setBackground(Color.WHITE);
        JButton volver = new JButton("Volver");
        JButton guardar = new JButton("Guardar");
        volver.addActionListener(event -> dispose());
        guardar.addActionListener(event -> guardar());
        botones.add(volver);
        botones.add(guardar);
        if (producto != null) {
            JButton eliminar = new JButton("Eliminar");
            eliminar.addActionListener(event -> eliminar());
            botones.add(eliminar);
        }
        panel.add(botones, BorderLayout.SOUTH);
        add(panel);
    }

    private void agregar(JPanel panel, GridBagConstraints c, int fila, String texto, JTextField campo) {
        c.gridy = fila; panel.add(new JLabel(texto + ":"), c);
        c.gridy = fila + 1; campo.setPreferredSize(new Dimension(280, 26)); panel.add(campo, c);
    }

    private void cargarProducto() {
        nombre.setText(producto.getNombre()); marca.setText(producto.getMarca()); categoria.setText(producto.getCategoria());
        precio.setText(String.valueOf(producto.getPrecio())); cantidad.setText(String.valueOf(producto.getCantidadDisponible()));
    }

    private void guardar() {
        if (nombre.getText().trim().isEmpty() || marca.getText().trim().isEmpty() || categoria.getText().trim().isEmpty()
                || precio.getText().trim().isEmpty() || cantidad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos del producto son obligatorios."); return;
        }
        try {
            double valorPrecio = Double.parseDouble(precio.getText().trim());
            int valorCantidad = Integer.parseInt(cantidad.getText().trim());
            if (valorPrecio < 0 || valorCantidad < 0) throw new NumberFormatException();
            Producto datos = producto == null ? new Producto() : producto;
            datos.setNombre(nombre.getText().trim()); datos.setMarca(marca.getText().trim());
            datos.setCategoria(categoria.getText().trim()); datos.setPrecio(valorPrecio); datos.setCantidadDisponible(valorCantidad);
            if (producto == null) dao.registrar(datos); else dao.actualizar(datos);
            principal.cargarTabla(); dispose();
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(this, "Precio y cantidad deben ser números positivos.");
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el producto: " + error.getMessage());
        }
    }

    private void eliminar() {
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar este producto?", "Eliminar", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            try { dao.eliminar(producto.getId()); principal.cargarTabla(); dispose(); }
            catch (Exception error) { JOptionPane.showMessageDialog(this, "No se pudo eliminar el producto."); }
        }
    }
}
