package com.urbanfeet_backend.Services.Implements;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urbanfeet_backend.DAO.Interfaces.CarritoItemDAO;
import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Carrito_item;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Model.CarritoDTOs.CarritoItemDetailResponse;
import com.urbanfeet_backend.Model.CarritoDTOs.CarritoItemRequest;
import com.urbanfeet_backend.Model.CarritoDTOs.CarritoItemResponse;
import com.urbanfeet_backend.Model.CarritoDTOs.CarritoResponse;
import com.urbanfeet_backend.Repository.CarritoItemRepository;
import com.urbanfeet_backend.Repository.CarritoRepository;
import com.urbanfeet_backend.Repository.UserRepository;
import com.urbanfeet_backend.Repository.Zapatilla_variacionRepository;
import com.urbanfeet_backend.Services.Interfaces.CarritoItemService;

@Service
public class CarritoItemServiceImpl implements CarritoItemService {

    @Autowired
    private CarritoItemDAO carritoItemDao;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @Autowired
    private Zapatilla_variacionRepository variacionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Carrito_item> obtenerTodo() {
        return carritoItemDao.findAll();
    }

    @Override
    public void guardar(Carrito_item carrito_item) {
        carritoItemDao.save(carrito_item);
    }

    @Override
    public Carrito_item buscarPorId(Integer id) {
        return carritoItemDao.findById(id);
    }

    @Override
    public void actualizar(Carrito_item carrito_item) {
        carritoItemDao.update(carrito_item);
    }

    @Override
    public void eliminarPorId(Integer id, Authentication auth) {
        // 1. Buscamos el item primero para ver de quién es
        Carrito_item item = carritoItemDao.findById(id); // O buscarPorId(id) si usas tu método auxiliar

        if (item == null) {
            throw new IllegalArgumentException("El item con id " + id + " no existe.");
        }

        // 2. SEGURIDAD: Verificamos el dueño
        String usuarioLogueado = auth.getName();
        String duenoDelItem = item.getCarrito().getUser().getEmail(); // Asumiendo estructura User -> Email

        if (!usuarioLogueado.equals(duenoDelItem)) {
            throw new SecurityException("Acceso denegado: No puedes eliminar un item que no es tuyo.");
        }

        // 3. Si es el dueño, borramos
        carritoItemDao.deleteById(id);
    }

    @Override
    public Carrito buscarCarritoPorId(Integer id) {
        return carritoRepository.findById(id).orElse(null);
    }

    @Override
    public Zapatilla_variacion buscarVariacionPorId(Integer id) {
        return variacionRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true) // <--- CRÍTICO: Mantiene la sesión abierta para leer los items
    public CarritoResponse obtenerCarritoDelUsuario(Authentication auth) {
        if (auth == null) throw new IllegalArgumentException("No autenticado");

        String email = auth.getName(); // Spring Security usa el nombre/email principal aquí
        
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Buscamos el carrito (o creamos uno si no existe, según tu lógica de negocio)
        // Aquí asumo que ya existe. Si puede ser null, manéjalo.
        Carrito carrito = carritoRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no tiene carrito"));

        // Mapeamos a DTO
        List<CarritoItemDetailResponse> itemsDto = carrito.getItems().stream()
                .map(item -> {
                    var variacion = item.getZapatilla_variacion();
                    var zapatilla = variacion.getZapatilla();
                    
                    return new CarritoItemDetailResponse(
                        item.getId(),
                        item.getCantidad(),
                        item.getCantidad() * variacion.getPrecio(), // Subtotal
                        
                        variacion.getId(),
                        variacion.getColor(),
                        variacion.getTalla(),
                        variacion.getPrecio(),
                        variacion.getImageUrl(),
                        variacion.getStock(),
                        
                        zapatilla.getNombre(),
                        zapatilla.getMarca()
                    );
                })
                .collect(Collectors.toList());

        // Calcular total general
        double total = itemsDto.stream().mapToDouble(i -> i.subtotal()).sum();

        return new CarritoResponse(carrito.getId(), total, itemsDto);
    }

    @Override
    @Transactional
    public CarritoItemResponse crearOIncrementarDesdeRequest(CarritoItemRequest request, Authentication auth) {
        if (request == null) throw new IllegalArgumentException("Request inválido");

        // CORRECCIÓN AQUÍ: Usamos el método privado que devuelve la ENTIDAD
        Carrito carrito = getCarritoEntity(auth); 

        Zapatilla_variacion variacion = buscarVariacionPorId(request.getZapatillaVariacionId());
        if (variacion == null) {
            throw new IllegalArgumentException("La variación con ID " + request.getZapatillaVariacionId() + " no existe");
        }

        Optional<Carrito_item> existenteOpt = carritoItemRepository.findByCarritoAndZapatillaVariacion(carrito, variacion);
        Carrito_item itemGuardado;

        if (existenteOpt.isPresent()) {
            Carrito_item existente = existenteOpt.get();
            int nuevaCantidad = (existente.getCantidad() == null ? 0 : existente.getCantidad()) + 1;
            existente.setCantidad(nuevaCantidad);
            actualizar(existente); // Asegúrate que tu DAO haga merge/save
            itemGuardado = existente;
        } else {
            Carrito_item nuevo = new Carrito_item();
            nuevo.setCarrito(carrito); // Ahora sí funciona porque 'carrito' es una Entidad
            nuevo.setZapatilla_variacion(variacion);
            nuevo.setCantidad(request.getCantidad() == null ? 1 : request.getCantidad());
            guardar(nuevo);
            itemGuardado = nuevo;
        }

        return convertirADTO(itemGuardado);
    }
    @Override
    public Carrito_item modificarCantidad(Integer id, boolean incrementar, Authentication auth) {
        // 1. Buscamos el item
        Carrito_item item = buscarPorId(id);
        if (item == null) {
            throw new IllegalArgumentException("Carrito_item no encontrado con id: " + id);
        }

        // 2. LOGICA DE SEGURIDAD: Obtener el usuario actual
        // Reutilizamos tu lógica de obtener usuario desde el Auth
        String emailAutenticado = auth.getName(); // Spring Security guarda el username/email aquí por defecto

        // Obtenemos el dueño del carrito al que pertenece este item
        String emailDueñoItem = item.getCarrito().getUser().getEmail(); // Asumiendo que User tiene getEmail()

        // 3. Comparamos
        if (!emailDueñoItem.equals(emailAutenticado)) {
            // Lanzamos una excepción específica de seguridad
            throw new SecurityException("No tienes permiso para modificar este item porque no te pertenece.");
        }

        // 4. Si pasa la validación, procedemos con la lógica normal
        Integer cantidadActual = item.getCantidad() == null ? 0 : item.getCantidad();
        int nuevaCantidad = cantidadActual + (incrementar ? 1 : -1);

        if (nuevaCantidad <= 0) {
            eliminarPorId(id,auth);
            return null;
        } else {
            item.setCantidad(nuevaCantidad);
            actualizar(item);
            return item;
        }
    }

    private CarritoItemResponse convertirADTO(Carrito_item entidad) {
        // Asumiendo que 'variacion' tiene un precio, ajústalo a tus getters reales
        Double precio = (entidad.getZapatilla_variacion() != null)
                ? entidad.getZapatilla_variacion().getPrecio() // O el campo que tengas
                : 0.0;

        return new CarritoItemResponse(
                entidad.getId(),
                entidad.getCantidad(),
                entidad.getZapatilla_variacion().getId(),
                precio);
    }

    private Carrito getCarritoEntity(Authentication auth) {
        if (auth == null) throw new IllegalArgumentException("No autenticado");
        
        String email = auth.getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        return carritoRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no tiene carrito"));
    }
}
