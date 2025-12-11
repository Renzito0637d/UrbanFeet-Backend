package com.urbanfeet_backend.Controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanfeet_backend.Services.Interfaces.PedidoService;
import com.urbanfeet_backend.Services.Interfaces.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/reports")
public class ReportesController {

    private final PedidoService pedidoService;

    private final UserService userService;

    public ReportesController(PedidoService pedidoService, UserService userService) {
        this.pedidoService = pedidoService;
        this.userService = userService;

    }

    @GetMapping("/users/pdf")
    public void generateUserPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=usuarios_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        userService.exportUserPdf(response);
    }

    @GetMapping("/sales/pdf")
    public void generateSalesPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ventas_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        pedidoService.exportSalesPdf(response);
    }

    @GetMapping("/users/excel")
    public void generateUserExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=usuarios_reporte.xlsx";
        response.setHeader(headerKey, headerValue);

        userService.exportUserExcel(response);
    }

    @GetMapping("/sales/excel")
    public void generateSalesExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ventas_reporte.xlsx";
        response.setHeader(headerKey, headerValue);

        pedidoService.exportSalesExcel(response);
    }
}
