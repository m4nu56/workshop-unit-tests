package com.workshop.metier.contrat;

import com.dev10.connection.ConnectionConfig;
import com.dev10.service.StandardSvc;

public class ContratSvc extends StandardSvc<Contrat> {

    private ContratDao contratDao;

    public ContratSvc() throws Throwable {
        this(null);
    }

    public ContratSvc(ConnectionConfig connectionConfig) throws Throwable {
        contratDao = new ContratDao("jdbc/datasourceName", "public", connectionConfig);
        setDaoPrincipal(contratDao);
    }
}