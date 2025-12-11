package com.urbanfeet_backend.Services.Implements;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.urbanfeet_backend.Config.Auth.JwtService;
import com.urbanfeet_backend.DAO.Interfaces.CarritoDAO;
import com.urbanfeet_backend.DAO.Interfaces.UserDAO;
import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.RoleName;
import com.urbanfeet_backend.Model.AuthDTOs.AuthResponse;
import com.urbanfeet_backend.Model.AuthDTOs.RegisterRequest;
import com.urbanfeet_backend.Model.UserDTOs.UserResponse;
import com.urbanfeet_backend.Model.UserDTOs.UserUpdateRequest;
import com.urbanfeet_backend.Services.Interfaces.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDAO userDAO;

    @Autowired
    private final CarritoDAO carritoDao;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserDAO userDAO, CarritoDAO carritoDao, PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userDAO = userDAO;
        this.carritoDao = carritoDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private void validateUniqueness(RegisterRequest r) {
        userDAO.buscarUserPorCorreo(r.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Email ya registrado");
                });

        userDAO.buscarPorNumeroDocumento(
                r.getDocumentType(),
                r.getDocumentNumber()).ifPresent(u -> {
                    throw new IllegalArgumentException("Documento ya registrado");
                });
    }

    @Override
    public List<User> listarUsers() {
        return userDAO.listarUsers();
    }

    @Override
    public User obtenerUserPorId(Integer id) {
        return userDAO.obtenerUserPorId(id);
    }

    @Override
    public void guardarUser(User User) {
        userDAO.guardarUser(User);
    }

    @Override
    public void actualizarUser(User User) {
        userDAO.actualizarUser(User);
    }

    @Override
    public void eliminarUser(Integer id) {
        userDAO.eliminarUser(id);
    }

    @Override
    public AuthResponse registerCliente(RegisterRequest request) {
        validateUniqueness(request);
        var encodedPassword = passwordEncoder.encode(request.getPassword());

        var user = User.from(request, encodedPassword, RoleName.CLIENTE);
        // Guardar el nuevo usuario en la base de datos
        userDAO.guardarUser(user);

        Carrito carrito = new Carrito();
        carrito.setUser(user);

        carritoDao.save(carrito);
        // Generar un token JWT para el usuario registrado
        var jwtToken = jwtService.generateAccessToken(user);
        // Devolver el token generado dentro de un objeto AuthResponse
        return AuthResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthResponse registerAdmin(RegisterRequest request) {
        validateUniqueness(request);
        var encodedPassword = passwordEncoder.encode(request.getPassword());

        RoleName roleAssign = (request.getRole() != null) ? request.getRole() : RoleName.ADMIN;

        // Evitar que alguien cree un CLIENTE por este medio (seguridad opcional)
        if (roleAssign == RoleName.CLIENTE) {
            throw new IllegalArgumentException("No se pueden crear clientes desde el panel interno");
        }

        var user = User.from(request, encodedPassword, roleAssign); // Usamos la variable roleAssign
        // Guardar el nuevo usuario en la base de datos
        userDAO.guardarUser(user);
        // Generar un token JWT para el usuario registrado
        var jwtToken = jwtService.generateAccessToken(user);
        // Devolver el token generado dentro de un objeto AuthResponse
        return AuthResponse.builder().token(jwtToken).build();
    }

    @Override
    public Page<UserResponse> listarPorRol(RoleName role, Pageable pageable) {
        return userDAO.listarPorRol(role, pageable);
    }

    @Override
    public Page<UserResponse> listarNoClientes(Pageable pageable) {
        return userDAO.listarNoClientes(pageable);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Integer id, UserUpdateRequest request) {
        // 1. Buscar usuario existente
        User user = userDAO.obtenerUserPorId(id);

        // 2. Actualizar campos básicos
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setPhone(request.getPhone());
        user.setDocumentType(request.getDocumentType());
        user.setDocumentNumber(request.getDocumentNumber());

        // 3. Lógica para actualizar ROL (Solo para internos)
        // Verificamos que se envíe un rol Y que el usuario actual NO sea un cliente.
        if (request.getRole() != null && !user.getRoles().contains(RoleName.CLIENTE)) {
            user.getRoles().clear();
            user.getRoles().add(request.getRole());
        }

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            // Opcional: validar unicidad aquí
            user.setEmail(request.getEmail());
        }

        // 4. Guardar cambios
        User userActualizado = userDAO.actualizarUser(user);

        // 5. Devolver DTO
        return UserResponse.fromEntity(userActualizado);
    }

    @Override
    public void changePassword(Integer id, String newPassword) {
        User user = userDAO.obtenerUserPorId(id);
        if (user == null)
            throw new RuntimeException("Usuario no encontrado");

        // Encriptamos la nueva contraseña
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userDAO.actualizarUser(user);
    }

    public void exportUserPdf(HttpServletResponse response) throws IOException {
        List<User> users = userDAO.listarUsers();

        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        try {
            PdfContentByte canvas = writer.getDirectContentUnder();

            ClassPathResource imageFile = new ClassPathResource("static/logo.png");
            Image watermark = Image.getInstance(imageFile.getURL());

            watermark.scaleToFit(400, 400);

            float x = (PageSize.A4.getWidth() - watermark.getScaledWidth()) / 2;
            float y = (PageSize.A4.getHeight() - watermark.getScaledHeight()) / 2;

            watermark.setAbsolutePosition(x, y);

            // Transparencia (Opacidad)
            PdfGState state = new PdfGState();
            state.setFillOpacity(0.3f);
            canvas.setGState(state);

            canvas.addImage(watermark);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Título
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph paragraph = new Paragraph("Reporte de Usuarios - UrbanFeet", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        document.add(new Paragraph(" ")); // Espacio

        // Tabla (5 columnas)
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] { 1.5f, 3.0f, 3.5f, 2.5f, 2.0f });

        // Cabecera
        addHeader(table, "ID");
        addHeader(table, "Nombre Completo");
        addHeader(table, "Email");
        addHeader(table, "Teléfono");
        addHeader(table, "Rol");

        // Datos
        for (User user : users) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getNombre() + " " + user.getApellido());
            table.addCell(user.getEmail());
            table.addCell(user.getPhone() != null ? user.getPhone() : "-");
            String roles = user.getRoles().isEmpty() ? "CLIENTE" : user.getRoles().toString();
            table.addCell(roles);
        }

        document.add(table);
        document.close();
    }

    private void addHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
        cell.setPadding(5);
        cell.setPhrase(new Phrase(text));
        table.addCell(cell);
    }

    public void exportUserExcel(HttpServletResponse response) throws IOException {
        List<User> users = userDAO.listarUsers();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");

        insertLogoToExcel(workbook, sheet, 7);

        // Estilo para la cabecera (Negrita)
        CellStyle headerStyle = workbook.createCellStyle();

        org.apache.poi.ss.usermodel.Font font = workbook.createFont();

        font.setBold(true);
        headerStyle.setFont(font);

        // Cabecera
        Row headerRow = sheet.createRow(0);
        String[] columns = { "ID", "Nombre", "Apellido", "Email", "Teléfono", "Roles" };

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Datos
        int rowNum = 1;
        for (User user : users) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getNombre());
            row.createCell(2).setCellValue(user.getApellido());
            row.createCell(3).setCellValue(user.getEmail());
            row.createCell(4).setCellValue(user.getPhone() != null ? user.getPhone() : "-");

            String roles = user.getRoles().isEmpty() ? "CLIENTE" : user.getRoles().toString();
            row.createCell(5).setCellValue(roles);
        }

        // Autoajustar columnas
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void insertLogoToExcel(Workbook workbook, Sheet sheet, int colNumber) {
        try {
            // 1. Cargar imagen
            ClassPathResource imgFile = new ClassPathResource("static/logo.png");
            InputStream is = imgFile.getInputStream();
            byte[] bytes = StreamUtils.copyToByteArray(is);
            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            is.close();

            CreationHelper helper = workbook.getCreationHelper();
            Drawing<?> drawing = sheet.createDrawingPatriarch();

            // 2. Crear Ancla
            ClientAnchor anchor = helper.createClientAnchor();
            
            // --- POSICIÓN Y TAMAÑO FIJO ---
            
            // COMIENZO: Columna 'colNumber', Fila 0 (Arriba)
            anchor.setCol1(colNumber);
            anchor.setRow1(0);

            // FINAL: Columna 'colNumber + 1', Fila 3
            // Esto significa: "Ocupa exactamente 1 columna de ancho y 3 filas de alto"
            anchor.setCol2(colNumber + 1); 
            anchor.setRow2(3); 

            // 3. Crear imagen (SIN RESIZE)
            // Al definir Col2 y Row2, la imagen se estira para llenar ese espacio.
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            
            // Opcional: Si quieres mantener la proporción original dentro de ese cuadro:
            // pict.resize(1.0); 

        } catch (Exception e) {
            System.err.println("Error logo excel: " + e.getMessage());
        }
    }
}
