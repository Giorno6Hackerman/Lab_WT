package app.services.factory;

import app.services.ClientServiceImpl;
import app.services.LibraryServiceImpl;
import app.services.interfaces.ClientService;
import app.services.interfaces.LibraryService;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private ServiceFactory() {}

    private final ClientService clientService = new ClientServiceImpl();
    private final LibraryService libraryService = new LibraryServiceImpl();

    public static ServiceFactory getInstance(){
        return instance;
    }

    public ClientService getClientService(){
        return clientService;
    }

    public LibraryService getLibraryService(){
        return libraryService;
    }
}
