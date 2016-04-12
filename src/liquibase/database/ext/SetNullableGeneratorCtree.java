package liquibase.database.ext;

import java.util.ArrayList;
import java.util.List;

import liquibase.database.Database;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.SetNullableGenerator;
import liquibase.statement.core.SetNullableStatement;

public class SetNullableGeneratorCtree extends SetNullableGenerator {

	@Override
    public int getPriority() {
        return SqlGenerator.PRIORITY_DATABASE;
    }
	

	public Sql[] generateSql(SetNullableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sql;

        //TypeConverter typeConverter = TypeConverterFactory.getInstance().findTypeConverter(database);

        sql = "ALTER TABLE " + database.escapeTableName(statement.getSchemaName(), statement.getTableName()) + " MODIFY  " + database.escapeColumnName(statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + (statement.isNullable() ? " NULL" : " NOT NULL");

        List<Sql> returnList = new ArrayList<Sql>();
        returnList.add(new UnparsedSql(sql));

        return returnList.toArray(new Sql[returnList.size()]);
    }
}
