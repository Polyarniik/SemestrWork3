package ru.kpfu.group903.safiullin.repositories;

import ru.kpfu.group903.safiullin.models.Record;

import java.util.List;

public interface RecordRepository extends CrudRepository<Record> {
    List<Record> getRecordsByUser(Long userID);

    Record getRecordByID(Long recordID);
}
