package liquibase.database.ext;

import liquibase.database.Database;
import liquibase.snapshot.jvm.JdbcDatabaseSnapshotGenerator;


public class CtreeDatabaseSnapshotGenerator extends JdbcDatabaseSnapshotGenerator {

	@Override
	public boolean supports(Database database) {
		return database.getDatabaseProductName().equals(CtreeDatabase.PRODUCT_NAME);
	}

	@Override
	public int getPriority(Database database) {
		return 2; // Higher than PRIORITY_DEFAULT
	}
	
	@Override
	protected String convertTableNameToDatabaseTableName(String tableName) {
		return super.convertTableNameToDatabaseTableName(tableName).toLowerCase();
	}
	
	@Override
	public boolean hasTable(String schemaName, String tableName, Database database) {
		//((JdbcConnection) database.getConnection()).getUnderlyingConnection().createStatement();
		return super.hasTable(schemaName, tableName, database);
	}

}
