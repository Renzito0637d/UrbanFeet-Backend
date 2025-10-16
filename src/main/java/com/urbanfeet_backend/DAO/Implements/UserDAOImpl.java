package com.urbanfeet_backend.DAO.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.UserDAO;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.DocumentType;
import com.urbanfeet_backend.Repository.UserRepository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listarUsers() {
        return userRepository.findAll();
    }

    @Override
    public User obtenerUserPorId(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void guardarUser(User User) {
        userRepository.save(User);
    }

    @Override
    public void actualizarUser(User User) {
        userRepository.save(User);
    }

    @Override
    public void eliminarUser(Integer id) {
        User usuarioObj = userRepository.findById(id).get();
        userRepository.delete(usuarioObj);
    }

    @Override
    public Optional<User> buscarUserPorCorreo(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> buscarPorNumeroDocumento(DocumentType documentType, String documentNumber) {
        return userRepository.findByDocumentTypeAndDocumentNumber(documentType, documentNumber);
    }

}
