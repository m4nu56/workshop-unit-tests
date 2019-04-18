package com.workshop.metier.echeance;

import com.dev10.connection.ConnectionConfig;
import com.dev10.service.StandardSvc;

public class EcheanceSvc extends StandardSvc<Echeance> {

    private EcheanceDao echeanceDao;

    public EcheanceSvc() throws Throwable {
        this(null);
    }

    public EcheanceSvc(ConnectionConfig connectionConfig) throws Throwable {
        echeanceDao = new EcheanceDao("jdbc/datasourceName", "public", connectionConfig);
        setDaoPrincipal(echeanceDao);
    }
}

