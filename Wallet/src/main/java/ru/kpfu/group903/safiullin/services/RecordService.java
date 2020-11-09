package ru.kpfu.group903.safiullin.services;

import ru.kpfu.group903.safiullin.models.Record;
import ru.kpfu.group903.safiullin.models.User;
import ru.kpfu.group903.safiullin.repositories.BankRepository;
import ru.kpfu.group903.safiullin.repositories.BankRepositoryJdbcImpl;
import ru.kpfu.group903.safiullin.repositories.RecordRepository;

import java.text.ParseException;
import java.util.List;

public class RecordService {
    private final RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public List<Record> getUserRecords(User user) {
        return recordRepository.getRecordsByUser(user.getId());
    }

    public void deleteRecord(Record record) {
        recordRepository.delete(record);
    }

    public void editRecord(Record record) throws ParseException {
        recordRepository.update(record);
    }

    public void addRecord(Record record) throws ParseException {
        recordRepository.save(record);
    }

    public Record getRecordByID(Long recordID) {
        return recordRepository.getRecordByID(recordID);
    }

}
