package app.controller;

import app.entities.*;
import app.entities.Record;
import app.services.exception.ServiceException;
import app.services.factory.ServiceFactory;
import app.services.interfaces.ClientService;
import app.services.interfaces.LibraryService;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class Controller extends HttpServlet {
    private final ClientService clientService = ServiceFactory.getInstance().getClientService();
    private final LibraryService libraryService = ServiceFactory.getInstance().getLibraryService();
    private final Logger logger = (Logger) LogManager.getLogger();
    private ResourceBundle bundle = ResourceBundle.getBundle("text");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setLangIfPresent(request);
        String action = getActionFromURI(request.getRequestURI());
        switch (action) {
            case "login" -> request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            case "register" -> request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
            case "logout" -> logout(request, response);
            case "catalog" -> serveCatalogPage(request, response);
            case "records" -> serveRecordsPage(request, response);
            case "addBook" -> addBook(request, response);
            case "editBook" -> editBook(request, response);
            default -> request.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = getActionFromURI(request.getRequestURI());
        switch (action) {
            case "login" -> login(request, response);
            case "register" -> register(request, response);
            default -> request.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(request, response);
        }
    }

    private String dispatch(String url, String destination) {
        return url.substring(0, url.lastIndexOf("/")) + "/" + destination;
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String count = request.getParameter("count");
        if (name != null && author != null && genre != null && count != null) {
            try {
                var book = new Book();
                book.setName(name);
                book.setAuthor(author);
                book.setGenre(genre);
                book.setCount(Integer.parseInt(count));
                if (!libraryService.addNewBook(book)) {
                    request.setAttribute("errorMessage", bundle.getString("error_add_book_data"));
                    request.setAttribute("type", "add");
                    request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
                } else {
                    response.sendRedirect(dispatch(request.getRequestURL().toString(), "catalog"));
                }
            } catch (ServiceException ex) {
                logger.error(ex.getMessage());
                request.setAttribute("errorMessage", bundle.getString("error_add_book"));
                request.setAttribute("type", "add");
                request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
            } catch (NumberFormatException ex) {
                logger.error(ex.getMessage());
                request.setAttribute("errorMessage", bundle.getString("error_count"));
                request.setAttribute("type", "add");
                request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
            }
        }
    }

    private void editBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String count = request.getParameter("count");
        if (id != null && name != null && author != null && genre != null && count != null) {
            try {
                var book = new Book();
                book.setId(Integer.parseInt(id));
                book.setName(name);
                book.setAuthor(author);
                book.setGenre(genre);
                book.setCount(Integer.parseInt(count));
                if (!libraryService.addEditedBook(book)) {
                    request.setAttribute("errorMessage", bundle.getString("error_edit_book_data"));
                    request.setAttribute("book", book);
                    request.setAttribute("type", "edit");
                    request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
                } else {
                    response.sendRedirect(dispatch(request.getRequestURL().toString(), "catalog"));
                }
            } catch (ServiceException ex) {
                logger.error(ex.getMessage());
                request.setAttribute("errorMessage", bundle.getString("error_edit_book"));
                request.setAttribute("type", "edit");
                request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
            } catch (NumberFormatException ex) {
                logger.error(ex.getMessage());
                request.setAttribute("errorMessage", bundle.getString("error_count"));
                request.setAttribute("type", "edit");
                request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
            }
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setRole(Role.User);
        try {
            user = clientService.register(user);
            if (user != null) {
                session.setAttribute("user", user);
                response.sendRedirect(dispatch(request.getRequestURL().toString(), "catalog"));
            } else {
                request.setAttribute("errorMessage", bundle.getString("login_is_busy"));
                request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
            }
        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
            request.setAttribute("errorMessage", bundle.getString("empty_fields"));
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        var user = new User();
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        try {
            user = clientService.login(user);
            if (user != null) {
                session.setAttribute("user", user);
                response.sendRedirect(dispatch(request.getRequestURL().toString(), "catalog"));
            } else {
                request.setAttribute("errorMessage", bundle.getString("incorrect_login_password"));
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            }
        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
            request.setAttribute("errorMessage", bundle.getString("empty_fields"));
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        response.sendRedirect(request.getContextPath() + "/library/login");
    }

    private void serveUserCatalogPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("books", libraryService.getBookList());
        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
            request.setAttribute("errorMessage", bundle.getString("error_book_list"));
        }

        request.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(request, response);
    }

    private void serveLibrarianCatalogPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String bookId = request.getParameter("bookId");
        if (action != null) {
            switch (action) {
                case "delete" -> {
                    try {
                        if (bookId != null) {
                            Book book = libraryService.getBookById(bookId);
                            libraryService.deleteBook(book);
                        }
                        response.sendRedirect(dispatch(request.getRequestURL().toString(), "catalog"));
                    } catch (ServiceException ex) {
                        logger.error(ex.getMessage());
                        request.setAttribute("errorMessage", bundle.getString("error_deleting"));
                        request.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(request, response);
                    }
                }
                case "edit" -> {
                    try {
                        request.setAttribute("book", libraryService.getBookById(bookId));
                        request.setAttribute("type", "edit");
                        request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
                    } catch (ServiceException ex) {
                        logger.error(ex.getMessage());
                        request.setAttribute("errorMessage", bundle.getString("error_getting"));
                        request.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(request, response);
                    }
                }
                case "add" -> {
                    request.setAttribute("type", "add");
                    request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
                }
            }
        } else {
            try {
                request.setAttribute("books", libraryService.getBookList());
            } catch (ServiceException ex) {
                logger.error(ex.getMessage());
                request.setAttribute("errorMessage", bundle.getString("error_book_list"));
            }
            request.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(request, response);
        }
    }

    private void serveCatalogPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user.getRole() == Role.User) {
            serveUserCatalogPage(request, response);
        } else {
            serveLibrarianCatalogPage(request, response);
        }
    }

    private List<Record> getUserRecords(String bookId, User user, String action, boolean langIsSet) throws ServiceException {
        if (bookId != null && !langIsSet) {
            Book book = libraryService.getBookById(bookId);
            Record record = new Record();
            record.setUser(user);
            record.setBook(book);
            if (action == null) {
                record.setType(OrderType.Processing);
                libraryService.addNewRecord(record);
            } else {
                libraryService.deleteRecord(record);
            }
        }
        return libraryService.getUserRecords(user);
    }

    private List<Record> getRecords(String bookId, String userLogin, String action, boolean langIsSet)
            throws ServiceException {
        if (bookId != null && !langIsSet) {
            User user = clientService.getUserByLogin(userLogin);
            Book book = libraryService.getBookById(bookId);
            Record record = new Record();
            record.setUser(user);
            record.setBook(book);
            switch (action) {
                case "reading_room" -> {
                    record.setType(OrderType.LibraryRoom);
                    libraryService.updateRecord(record);
                }
                case "subscription" -> {
                    record.setType(OrderType.SeasonTicket);
                    libraryService.updateRecord(record);
                }
                case "cancel" -> {
                    libraryService.deleteRecord(record);
                }
            }
        }
        return libraryService.getRecords();
    }

    private void serveRecordsPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute("user");
            String action = request.getParameter("action");
            String userLogin = request.getParameter("userLogin");
            String bookId = request.getParameter("bookId");
            boolean langIsSet = request.getParameter("lang") != null;
            List<Record> records;
            if (user.getRole() == Role.User) {
                records = getUserRecords(bookId, user, action, langIsSet);
            } else {
                records = getRecords(bookId, userLogin, action, langIsSet);
            }
            if (records.stream().noneMatch(record -> record.getType() == OrderType.Processing)) {
                request.setAttribute("noProcessing", bundle.getString("no_processing"));
            }
            if (records.stream().noneMatch(record -> record.getType() != OrderType.Processing)) {
                request.setAttribute("noActive", bundle.getString("no_active"));
            }
            request.setAttribute("records", records);
            request.getRequestDispatcher("/WEB-INF/jsp/records.jsp").forward(request, response);
        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
            request.setAttribute("errorMessage", bundle.getString("error_record_list"));
        }
    }

    private void setLangIfPresent(HttpServletRequest request) {
        String lang = request.getParameter("lang");
        if (lang != null) {
            request.getSession().setAttribute("lang", lang);
            bundle = ResourceBundle.getBundle("text", new Locale(lang));
            String query = request.getQueryString();
            request.setAttribute("queryWithLang", query.substring(0, query.lastIndexOf("=")) + "=");
        }
    }

    private String getActionFromURI(String URI) {
        String[] arr = URI.split("/");
        return arr[arr.length - 1];
    }


}
