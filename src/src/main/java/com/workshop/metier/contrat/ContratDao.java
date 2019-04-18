package com.workshop.metier.contrat;// ------------------------------------------------------------
// -------------------------DAO--------------------------------
// ------------------------------------------------------------

import com.dev10.connection.ConnectionConfig;
import com.dev10.dao.StandardDao;
import com.dev10.dao.enums.CRUD;
import com.dev10.dao.fieldDb.FieldDbKeyValue;
import com.dev10.dao.fieldDb.interfaces.FieldDb;
import com.workshop.metier.echeance.Echeance;
import com.workshop.metier.echeance.EcheanceDao;
import org.apache.commons.collections.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;

public class ContratDao extends StandardDao<Contrat> {

    public static final String TABLE_NAME = "t_contrat";
    public static final String ALIAS = "C";

    private EcheanceDao echeanceDao;

    public ContratDao(String datasource, String schemaCompte) {
        this(datasource, schemaCompte, null);
    }

    public ContratDao(String datasource, String schemaCompte, ConnectionConfig connectionConfig) {
        super(datasource, schemaCompte, connectionConfig);
        init();
    }

    public ContratDao(StandardDao<?> otherDao) {
        super(otherDao);
        init();
    }

    private void init() {
        this.echeanceDao = new EcheanceDao(this);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public FieldDb[] getFields() {
        return Field.values();
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }

    // ------------------------------------------------------------
    // -------------------------FILL PSMT--------------------------
    // ------------------------------------------------------------
    @Override
    protected int fillPsmtWithBean(Connection con, PreparedStatement psmt, Contrat contrat, CRUD crud, int pos, FieldDb[] tblField) throws Exception {

        int modeCRUD = (crud == CRUD.CREATE) ? MODE_INSERT : MODE_UPDATE;

        FieldDbKeyValue[] tblFieldVal = {
                new FieldDbKeyValue(Field.ID, contrat.getId()),
                new FieldDbKeyValue(Field.CODE, contrat.getCode()),
                new FieldDbKeyValue(Field.MONTANT, contrat.getMontant()),
                new FieldDbKeyValue(Field.CODE_TAUX, contrat.getCodeTaux()),
                new FieldDbKeyValue(Field.DUREE, contrat.getDuree()),
                new FieldDbKeyValue(Field.DATE_DEBUT, contrat.getDateDebut()),
        };

        if (pos < 1) pos = 1;
        for (FieldDb f : tblField) {
            if (f.isModeWrite(modeCRUD)) fillPsmtData(con, pos++, psmt, f, rechercheValue(f, tblFieldVal));
        }

        return pos;
    }

    // ------------------------------------------------------------
    // -------------------------BUILD BEAN--------------------------
    // ------------------------------------------------------------
    @Override
    public Contrat buildBeanBasic(ResultSet rs, String prefix, boolean isSubBuild) throws Throwable {

        Contrat contrat = new Contrat();

        contrat.setId(rs.getLong(Field.ID.getAliasColumnName(prefix)));
        contrat.setCode(rs.getString(Field.CODE.getAliasColumnName(prefix)));
        contrat.setMontant(rs.getDouble(Field.MONTANT.getAliasColumnName(prefix)));
        contrat.setCodeTaux(rs.getString(Field.CODE_TAUX.getAliasColumnName(prefix)));
        contrat.setDuree(rs.getInt(Field.DUREE.getAliasColumnName(prefix)));
        contrat.setDateDebut(rs.getObject(Field.DATE_DEBUT.getAliasColumnName(prefix), LocalDate.class));

        return contrat;
    }

    @Override
    protected void crudChildren(Connection con, Contrat contrat, CRUD crud) throws Throwable {
        switch (crud) {
            case CREATE:
                if (CollectionUtils.isEmpty(contrat.getListEcheance())) return;
                for (Echeance echeance : contrat.getListEcheance()) {
                    echeanceDao.crud(con, echeance, CRUD.CREATE);
                }
                break;
            case UPDATE:
                // TODO
                break;
            case DELETE:
                // Dealt by constraint in DB
                break;
        }
    }

    /*******************************************************************************************************
     * Les Fields de la table t_contrat
     *******************************************************************************************************/
    public enum Field implements FieldDb {
        // @formatter:off
		ID		    ( "id",		    Types.BIGINT,		true,	MODE_SEL_INS_UP ),
		CODE		( "code",		Types.VARCHAR,		true,	MODE_SEL_INS_UP ),
		MONTANT		( "montant",	Types.DOUBLE,		true,	MODE_SEL_INS_UP ),
		CODE_TAUX	( "code_taux",	Types.VARCHAR,		false,	MODE_SEL_INS_UP ),
		DUREE		( "duree",		Types.INTEGER,		true,	MODE_SEL_INS_UP ),
		DATE_DEBUT	( "date_debut",	Types.DATE,	    	true,	MODE_SEL_INS_UP ),
		// @formatter:on
        ;

        private String columnName;
        private int typeSql;
        private boolean notNull;
        private int modeWrite;
        private int length;
        private String foreignKeyTable;
        private String alias;

        Field(String columnName, int typeSql, boolean notNull, int modeWrite) {
            this(columnName, typeSql, notNull, modeWrite, 0, null);
        }

        Field(String columnName, int typeSql, boolean notNull, int modeWrite, int length, String foreignKeyTable) {
            this.columnName = columnName;
            this.typeSql = typeSql;
            this.notNull = notNull;
            this.modeWrite = modeWrite;
            this.length = length;
            this.foreignKeyTable = foreignKeyTable;
            this.alias = ContratDao.ALIAS;
        }

        @Override
        public String getColumnName() {
            return columnName;
        }

        @Override
        public int getTypeSql() {
            return typeSql;
        }

        @Override
        public boolean isNotNull() {
            return notNull;
        }

        @Override
        public int getModeWrite() {
            return modeWrite;
        }

        @Override
        public String getForeignKeyTable() {
            return foreignKeyTable;
        }

        @Override
        public String getAlias() {
            return alias;
        }
    }

}