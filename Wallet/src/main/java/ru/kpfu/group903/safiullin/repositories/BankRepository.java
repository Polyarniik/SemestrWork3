package ru.kpfu.group903.safiullin.repositories;

import ru.kpfu.group903.safiullin.exceptions.DatabaseException;
import ru.kpfu.group903.safiullin.models.Bank;

import java.util.List;

public interface BankRepository extends CrudRepository<Bank> {
    List<Bank> getBanksByUser(Long id);

    Bank getBankByName(String bankName) throws DatabaseException;

    Bank getUserBankByID(Long userId, Long bankId);

    List<Bank> getUserBanks(Long userId);

    void addBankForUser(Long userID, Long bankID, float amount);

    void changeBankAmount(Long userID, Long bankID, float amount);

}
