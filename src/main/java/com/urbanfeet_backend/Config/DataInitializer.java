package com.urbanfeet_backend.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.urbanfeet_backend.DAO.Interfaces.UserDAO;
import com.urbanfeet_backend.Entity.Enum.DocumentType;
import com.urbanfeet_backend.Model.RegisterRequest;
import com.urbanfeet_backend.Services.Interfaces.UserService;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final UserDAO userDAO;

    public DataInitializer(UserService userService, UserDAO userDAO) {
        this.userService = userService;
        this.userDAO = userDAO;
    }

    // Define los datos del admin
    private static final String ADMIN_EMAIL = "admin@gmail.com";

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Iniciando DataInitializer...");

        if (userDAO.buscarUserPorCorreo(ADMIN_EMAIL).isPresent()) {
            System.out.println("El usuario administrador ya existe. No se tomarán acciones.");
        } else {
            System.out.println("El usuario administrador no existe. Creando usuario admin por defecto...");

            try {
                RegisterRequest adminRequest = new RegisterRequest();
                adminRequest.setNombre("Admin");
                adminRequest.setApellido("Principal");
                adminRequest.setEmail(ADMIN_EMAIL);
                adminRequest.setPhone("999999999");
                adminRequest.setPassword("admin123");
                adminRequest.setDocumentType(DocumentType.DNI);
                adminRequest.setDocumentNumber("00000001");

                userService.registerAdmin(adminRequest);

                System.out.println("Usuario administrador creado exitosamente con email: " + ADMIN_EMAIL);
                System.out.println("La contraseña por defecto es 'admin123'.");

            } catch (Exception e) {
                System.err.println("Error al intentar crear el usuario admin por defecto: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}