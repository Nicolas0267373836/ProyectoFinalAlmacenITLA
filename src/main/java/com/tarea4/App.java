package com.tarea4;

import com.tarea4.factory.DAOFactory;
import com.tarea4.ui.LoginFrame;

import javax.swing.SwingUtilities;

public class App {

    private static LoginFrame login;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            login = new LoginFrame(DAOFactory.crearUsuarioDAO(), DAOFactory.crearProductoDAO());
            login.setVisible(true);
        });
    }
}
