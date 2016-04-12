package liquibase.database.ext;

import java.util.Arrays;
import java.util.List;

import liquibase.change.Change;
import liquibase.change.core.AddForeignKeyConstraintChange;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.AbstractDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnsupportedChangeException;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.statement.SqlStatement;

public class CtreeDatabase extends AbstractDatabase {

	public static final String PRODUCT_NAME = "c-treeACE SQL";

	@Override
	public String getCurrentDateTimeFunction() {
		if (currentDateTimeFunction != null) {
			return currentDateTimeFunction;
		}

		return "CURRENT_TIMESTAMP()"; // "CURDATE()";
	}

	@Override
	public String getDefaultDriver(String url) {
		if (url.startsWith("jdbc:ctree:")) {
			return "ctree.jdbc.ctreeDriver";
		}
		return null;
	}

	@Override
	public String getTypeName() {
		return PRODUCT_NAME;
	}

	@Override
	public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
		return PRODUCT_NAME.equalsIgnoreCase(conn.getDatabaseProductName());
	}
	
	
	// Since there is no way to change the behavior of AddForeignKeyConstraintGenerator.generateSQL in a database dependent way, we hack this here in.
	@Override
	public void executeStatements(Change change, DatabaseChangeLog changeLog, List<SqlVisitor> sqlVisitors) throws LiquibaseException, UnsupportedChangeException {
		if( change instanceof AddForeignKeyConstraintChange) {
			AddForeignKeyConstraintChange afkcc = (AddForeignKeyConstraintChange) change;
			afkcc.setOnDelete((String)null);
			afkcc.setOnUpdate((String)null);
		}
		super.executeStatements(change, changeLog, sqlVisitors);
	}
	
	@Override
	public void execute(SqlStatement[] statements, List<SqlVisitor> sqlVisitors) throws LiquibaseException {
		super.execute(statements, sqlVisitors);
	}

	@Override
	public boolean supportsInitiallyDeferrableColumns() {
		return false;
	}

	// I start to think that c-tree actually supports table spaces...
	@Override
	public boolean supportsTablespaces() {
		return false;
	}

	@Override
	public boolean supportsSequences() {
		return false;
	}

	@Override
	protected String getAutoIncrementClause() {
		return "IDENTITY";
	}

	// this only escapes names that collide with c-tree built in names
	// if we escape all the time we get problems with select statements
	List<String> reservedKeyWords = Arrays.asList("version", "definition", "timeout", "date");
	@Override
	public String escapeDatabaseObject(String objectName) {
		return reservedKeyWords.contains(objectName) ? '"' + objectName + '"' : objectName;
	}

	@Override
	public int getPriority() {
		return 2; // we make this higher than PRIORITY_DEFAULT to make sure this class is used.
	}

	
	// c-tree does not need a special primary key name
	@Override
	public String generatePrimaryKeyName(String tableName) {
		return null;
	}

	@Override
	public String convertRequestedSchemaToCatalog(String requestedSchema) throws DatabaseException {
		return super.convertRequestedSchemaToCatalog(requestedSchema);
	}

	@Override
	public String convertRequestedSchemaToSchema(String requestedSchema) throws DatabaseException {
		return super.convertRequestedSchemaToSchema(requestedSchema).toLowerCase();
	}

	// we change this to lower case or we get exceptions.
	@Override
	public String getDatabaseChangeLogLockTableName() {
		return super.getDatabaseChangeLogLockTableName().toLowerCase();
	}

	public String getDatabaseChangeLogTableName() {
		return super.getDatabaseChangeLogTableName().toLowerCase();
	}

}
