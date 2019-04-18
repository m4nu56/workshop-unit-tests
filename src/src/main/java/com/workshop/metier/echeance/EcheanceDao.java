package com.workshop.metier.echeance;

import com.dev10.connection.ConnectionConfig;
import com.dev10.dao.StandardDao;
import com.dev10.dao.enums.CRUD;
import com.dev10.dao.fieldDb.FieldDbKeyValue;
import com.dev10.dao.fieldDb.interfaces.FieldDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;

public class EcheanceDao extends StandardDao<Echeance> {

    public static final String TABLE_NAME = "t_echeance";
    public static final String ALIAS = "E";

    public EcheanceDao(String datasource, String schemaCompte) {
        super(datasource, schemaCompte);
    }

    public EcheanceDao(String datasource, String schemaCompte, ConnectionConfig connectionConfig) {
        super(datasource, schemaCompte, connectionConfig);
    }

    public EcheanceDao(StandardDao<?> otherDao) {
        super(otherDao);
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
    protected int fillPsmtWithBean(Connection con, PreparedStatement psmt, Echeance echeance, CRUD crud, int pos, FieldDb[] tblField) throws Exception {

        int modeCRUD = (crud == CRUD.CREATE) ? MODE_INSERT : MODE_UPDATE;

        FieldDbKeyValue[] tblFieldVal = {
                new FieldDbKeyValue(Field.ID, echeance.getId()),
                new FieldDbKeyValue(Field.ID_CONTRAT, echeance.getIdContrat()),
                new FieldDbKeyValue(Field.CAPITAL, echeance.getCapital()),
                new FieldDbKeyValue(Field.INTERETS, echeance.getInterets()),
                new FieldDbKeyValue(Field.DATE, echeance.getDate()),
                new FieldDbKeyValue(Field.ENCOURS, echeance.getEncours()),
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
    public Echeance buildBeanBasic(ResultSet rs, String prefix, boolean isSubBuild) throws Throwable {

        Echeance echeance = new Echeance();

        echeance.setId(rs.getLong(Field.ID.getAliasColumnName(prefix)));
        echeance.setIdContrat(rs.getLong(Field.ID_CONTRAT.getAliasColumnName(prefix)));
        echeance.setCapital(rs.getDouble(Field.CAPITAL.getAliasColumnName(prefix)));
        echeance.setInterets(rs.getDouble(Field.INTERETS.getAliasColumnName(prefix)));
        echeance.setDate(rs.getObject(Field.DATE.getAliasColumnName(prefix), LocalDate.class));
        echeance.setEncours(rs.getDouble(Field.ENCOURS.getAliasColumnName(prefix)));

        return echeance;
    }

    /*******************************************************************************************************
     * Les Fields de la table t_echeance
     *
     *******************************************************************************************************/
    public enum Field implements FieldDb {
        // @formatter:off
		ID		        ( "id",		        Types.BIGINT,		true,	MODE_SEL_INS_UP ),
		ID_CONTRAT		( "id_contrat",		Types.BIGINT,		true,	MODE_SEL_INS_UP ),
		CAPITAL		    ( "capital",		Types.DOUBLE,		true,	MODE_SEL_INS_UP ),
		INTERETS		( "interets",		Types.DOUBLE,		true,	MODE_SEL_INS_UP ),
		DATE		    ( "date",		    Types.DATE,	    	true,	MODE_SEL_INS_UP ),
		ENCOURS		    ( "encours",		Types.DOUBLE,		true,	MODE_SEL_INS_UP ),
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
            this.alias = EcheanceDao.ALIAS;
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