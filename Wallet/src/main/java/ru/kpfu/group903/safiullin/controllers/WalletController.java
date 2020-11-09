package ru.kpfu.group903.safiullin.controllers;

import ru.kpfu.group903.safiullin.models.Bank;
import ru.kpfu.group903.safiullin.models.Record;
import ru.kpfu.group903.safiullin.models.User;
import ru.kpfu.group903.safiullin.services.BankService;
import ru.kpfu.group903.safiullin.services.CategoryService;
import ru.kpfu.group903.safiullin.services.RecordService;
import ru.kpfu.group903.safiullin.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/wallet")
public class WalletController extends HttpServlet {
    private UserService userService;
    private RecordService recordService;
    private BankService bankService;
    private CategoryService categoryService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        this.userService = (UserService) servletContext.getAttribute("userService");
        this.recordService = (RecordService) servletContext.getAttribute("recordsService");
        this.bankService = (BankService) servletContext.getAttribute("bankService");
        this.categoryService = (CategoryService) servletContext.getAttribute("categoryService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            req.setAttribute("recordsList", recordService.getUserRecords(user));
            req.setAttribute("banksList", bankService.getUserBanks(user.getId()));
            req.setAttribute("addBanksList", bankService.getNotUserBanks(user.getId()));
            req.setAttribute("categoryList", categoryService.getAllCategory());
            if (req.getParameter("rec") != null) {
                Long recordID = Long.valueOf(req.getParameter("rec"));
                Record record = recordService.getRecordByID(recordID);
                req.setAttribute("editRecord", record);
                try {
                    Date date = new SimpleDateFormat("dd.MM.yyyy").parse(record.getDate());
                    req.setAttribute("editRecordDate", new SimpleDateFormat("yyyy-MM-dd").format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (req.getParameter("del") != null) {
                Long recordID = Long.valueOf(req.getParameter("del"));
                recordService.deleteRecord(recordService.getRecordByID(recordID));
                resp.sendRedirect(getServletContext().getContextPath() + "/wallet");
                return;
            }
            if (req.getParameter("quit") != null) {
                req.removeAttribute("user");
                resp.sendRedirect(getServletContext().getContextPath() + "/login");
            } else req.getRequestDispatcher("WEB-INF/views/main.jsp").forward(req, resp);
        } else resp.sendRedirect(getServletContext().getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (req.getParameter("form").equals("form1")) {
            long addBankID = Integer.parseInt(req.getParameter("bank-add"));
            float amount = Float.parseFloat(req.getParameter("bank-add-amount"));
            bankService.addBankForUser(user.getId(), addBankID, amount);
        } else if (req.getParameter("form").equals("form2")) {
            long bankId = Long.parseLong(req.getParameter("bank-select"));
            boolean recordType = req.getParameter("record-type-select").equals("1");
            float recordValue = Float.parseFloat(req.getParameter("record-input"));
            long categoryId = Integer.parseInt(req.getParameter("category-select"));
            String date = req.getParameter("date");
            String descriptions = req.getParameter("description");
            Bank bank = bankService.getUserBankByID(user.getId(), bankId);
            Float amount;
            System.out.println("" + bankId);
            if (!recordType) {
                amount = bank.getAmount() - recordValue;
                recordValue *= -1;
            } else amount = bank.getAmount() + recordValue;

            if (req.getParameter("rec") == null) {
                try {
                    recordService.addRecord(Record.builder().
                            bank(bank).
                            user(user).
                            sum(recordValue).
                            category(categoryService.getCategoryById(categoryId)).
                            date(date).
                            description(descriptions).
                            build());
                } catch (ParseException e) {
                    req.setAttribute("error", "Ошибка! Попробуйте позже!");
                    resp.sendRedirect(getServletContext().getContextPath() + "/wallet");
                }
            } else {
                try {
                    recordService.editRecord(Record.builder()
                            .bank(bank)
                            .id(Long.parseLong(req.getParameter("rec")))
                            .user(user)
                            .sum(recordValue)
                            .category(categoryService.getCategoryById(categoryId))
                            .date(date)
                            .description(descriptions)
                            .build());
                } catch (ParseException e) {
                    req.setAttribute("error", "Ошибка! Попробуйте позже!");
                    resp.sendRedirect(getServletContext().getContextPath() + "/wallet");
                }
            }
            bankService.changeAmount(user.getId(), bankId, amount);
        }
        resp.sendRedirect(getServletContext().getContextPath() + "/wallet");
    }
}
