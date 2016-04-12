package liquibase.database.ext;

import liquibase.database.Database;
import liquibase.database.structure.type.BlobType;
import liquibase.database.structure.type.BooleanType;
import liquibase.database.structure.type.DateTimeType;
import liquibase.database.structure.type.TextType;
import liquibase.database.typeconversion.core.AbstractTypeConverter;


public class CtreeTypeConverter extends AbstractTypeConverter {

	@Override
	public int getPriority() {
		return 2; // Higher than PRIORITY_DEFAULT
	}
	
	public BooleanType getBooleanType() {
        return new BooleanType.NumericBooleanType("BIT");
    }
	
	@Override
	public TextType getTextType() {
		return new TextType("LVARCHAR", 0, 0);
	}
	
	@Override
	public BlobType getBlobType() {
		return new BlobType("LVARBINARY");
	}
	
	@Override
	public BlobType getLongBlobType() {
		return new BlobType("LVARBINARY");
	}
	
	@Override
	public DateTimeType getDateTimeType() {
		return new DateTimeType("TIMESTAMP");
	}


	@Override
	public boolean supports(Database database) {
		return database.getDatabaseProductName().equals(CtreeDatabase.PRODUCT_NAME);
	}


}
