package liquibase.database.ext;

import liquibase.database.Database;
import liquibase.database.typeconversion.TypeConverterFactory;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.ModifyDataTypeGenerator;
import liquibase.statement.core.ModifyDataTypeStatement;

public class ModifyDataTypeGeneratorCtree extends ModifyDataTypeGenerator {

	@Override
    public int getPriority() {
        return SqlGenerator.PRIORITY_DATABASE;
    }
	
	public Sql[] generateSql(ModifyDataTypeStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String alterTable = "ALTER TABLE " + database.escapeTableName(statement.getSchemaName(), statement.getTableName());

        alterTable += " MODIFY ";

        // add column name
        alterTable += database.escapeColumnName(statement.getSchemaName(), statement.getTableName(), statement.getColumnName());

        alterTable += getPreDataTypeString(database); // adds a space if nothing else

        // add column type
        alterTable += TypeConverterFactory.getInstance().findTypeConverter(database).getDataType(statement.getNewDataType(), false);

        return new Sql[]{new UnparsedSql(alterTable)};
    }
	
	
	private String getPreDataTypeString(Database database) {
        return " ";
    }
}
