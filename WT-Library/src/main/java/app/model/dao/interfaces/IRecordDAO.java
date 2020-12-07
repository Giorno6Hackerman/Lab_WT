package app.model.dao.interfaces;

import java.util.List;
import app.entities.Record;

public interface IRecordDAO {
    void addRecord(Record record);
    void deleteRecord(Record record);
    void deleteRecordsByBook(Record record);
    void updateRecord(Record record);
    List<Record> getRecords();
}
