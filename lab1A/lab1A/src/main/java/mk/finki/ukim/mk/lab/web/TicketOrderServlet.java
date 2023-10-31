package mk.finki.ukim.mk.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.finki.ukim.mk.lab.model.Movie;
import mk.finki.ukim.mk.lab.service.impl.MovieServiceImpl;
import mk.finki.ukim.mk.lab.service.impl.TicketOrderServiceImpl;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(urlPatterns="/ticketOrder")
public class TicketOrderServlet extends HttpServlet {

    private final TicketOrderServiceImpl ticketOrderService;
    private final SpringTemplateEngine templateEngine;


    public TicketOrderServlet( TicketOrderServiceImpl ticketOrderService,
                               SpringTemplateEngine templateEngine ) {
        this.ticketOrderService=ticketOrderService;
        this.templateEngine=templateEngine;
    }

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req,resp);
        WebContext context = new WebContext(webExchange);
        String title = req.getParameter("movieTitle");
        String tickets = req.getParameter("tickets");
        if (title == null || title.isEmpty() || title.equals("null"))
            title = "Invalid movie";
        if(tickets == null || tickets.isEmpty())
            tickets = "-1";

        context.setVariable("ticket",
                ticketOrderService.placeOrder(
                        title,
                        "Martin Patcev",req.getRemoteAddr(),
                        Integer.parseInt(tickets)
                )
        );
        templateEngine.process("orderConfirmation.html",context,resp.getWriter());
    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        String movie = (String)req.getParameter("movieTitle");
        String tickets = req.getParameter("numTickets");

        resp.sendRedirect("/ticketOrder?movieTitle="+movie+"&tickets="+tickets);
    }
}
