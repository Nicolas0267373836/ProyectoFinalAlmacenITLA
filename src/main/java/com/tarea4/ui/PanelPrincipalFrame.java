package com.tarea4.ui;

import com.tarea4.dao.ProductoDAO;
import com.tarea4.dao.UsuarioDAO;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

/* Panel principal: permite escoger el módulo de usuarios o productos. */
public class PanelPrincipalFrame extends JFrame {

    private final UsuarioDAO usuarioDAO;
    private final ProductoDAO productoDAO;

    public PanelPrincipalFrame(UsuarioDAO usuarioDAO, ProductoDAO productoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;
        setTitle("Sistema de Gestión de Almacén");
        setSize(620, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel principal = new JPanel(new BorderLayout(0, 25));
        principal.setBorder(BorderFactory.createEmptyBorder(35, 55, 35, 55));
        principal.setBackground(Color.WHITE);
        JLabel titulo = new JLabel("SISTEMA DE ALMACÉN", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        principal.add(titulo, BorderLayout.NORTH);

        JPanel opciones = new JPanel(new GridLayout(1, 2, 25, 0));
        opciones.setBackground(Color.WHITE);
        JButton usuarios = new JButton("👥  Gestión de Usuarios");
        JButton productos = new JButton("📦  Gestión de Productos");
        usuarios.setFont(new Font("Arial", Font.BOLD, 15));
        productos.setFont(new Font("Arial", Font.BOLD, 15));
        usuarios.addActionListener(event -> abrirUsuarios());
        productos.addActionListener(event -> abrirProductos());
        opciones.add(usuarios);
        opciones.add(productos);
        principal.add(opciones, BorderLayout.CENTER);

        JButton cerrarSesion = new JButton("Cerrar Sesión");
        cerrarSesion.addActionListener(event -> cerrarSesion());
        principal.add(cerrarSesion, BorderLayout.SOUTH);
        add(principal);
    }

    private void abrirUsuarios() {
        dispose();
        new PrincipalFrame(usuarioDAO, productoDAO).setVisible(true);
    }

    private void abrirProductos() {
        dispose();
        new ProductosFrame(usuarioDAO, productoDAO).setVisible(true);
    }

    private void cerrarSesion() {
        dispose();
        new LoginFrame(usuarioDAO, productoDAO).setVisible(true);
    }
}
