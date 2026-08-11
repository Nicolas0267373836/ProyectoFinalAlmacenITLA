package com.tarea4.ui;

import com.tarea4.dao.ProductoDAO;
import com.tarea4.dao.UsuarioDAO;
import com.tarea4.model.Producto;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ProductosFrame extends JFrame {

    private final UsuarioDAO usuarioDAO;
    private final ProductoDAO productoDAO;
    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Nombre", "Marca", "Categoría", "Precio", "Cantidad Disponible"}, 0) {
        @Override public boolean isCellEditable(int fila, int columna) { return false; }
    };
    private final JTable tabla = new JTable(modelo);
    private List<Producto> productos = new ArrayList<>();

    public ProductosFrame(UsuarioDAO usuarioDAO, ProductoDAO productoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;
        setTitle("Productos de Almacén");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Productos de Almacén", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        tabla.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent event) {
                if (tabla.getSelectedRow() != -1) abrirSeleccionado();
            }
        });

        JPanel botones = new JPanel(new FlowLayout());
        JButton nuevo = new JButton("Nuevo");
        JButton volver = new JButton("Volver");
        nuevo.addActionListener(event -> new ProductoDialog(this, productoDAO, null).setVisible(true));
        volver.addActionListener(event -> volver());
        botones.add(nuevo);
        botones.add(volver);
        add(botones, BorderLayout.SOUTH);
        cargarTabla();
    }

    public void cargarTabla() {
        try {
            productos = productoDAO.listarProductos();
            modelo.setRowCount(0);
            for (Producto producto : productos) {
                modelo.addRow(new Object[]{producto.getNombre(), producto.getMarca(), producto.getCategoria(),
                        String.format("%.2f", producto.getPrecio()), producto.getCantidadDisponible()});
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar los productos: " + error.getMessage());
        }
    }

    private void abrirSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) new ProductoDialog(this, productoDAO, productos.get(fila)).setVisible(true);
    }

    private void volver() {
        dispose();
        new PanelPrincipalFrame(usuarioDAO, productoDAO).setVisible(true);
    }
}
