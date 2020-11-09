package ru.kpfu.group903.safiullin.services;

import ru.kpfu.group903.safiullin.exceptions.DatabaseException;
import ru.kpfu.group903.safiullin.models.Bank;
import ru.kpfu.group903.safiullin.repositories.BankRepository;

import java.util.List;

public class BankService {
    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public void addBankForUser(Long userID, Long bankID, float amount) {
        bankRepository.addBankForUser(userID, bankID, amount);
    }

    public Bank getBankById(Long bankId) {
        return bankRepository.findById(bankId);
    }

    public List<Bank> getUserBanks(Long id) {
        return bankRepository.getBanksByUser(id);
    }

    public List<Bank> getNotUserBanks(Long id) {
        List<Bank> allBanks = bankRepository.findAll();
        List<Bank> userBanks = bankRepository.getBanksByUser(id);
        for (Bank bank : userBanks) {
            bank.setAmount(0);
            allBanks.remove(bank);
        }
        return allBanks;
    }

    public void changeAmount(Long userID, Long bankID, float amount) {
        bankRepository.changeBankAmount(userID, bankID, amount);
    }

    public Bank getUserBankByID(Long userID, Long bankID){
        return bankRepository.getUserBankByID(userID, bankID);
    }
}
