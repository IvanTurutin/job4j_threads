package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public final class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rslt = false;
        Optional<Account> optAccFrom = getById(fromId);
        Optional<Account> optAccTo = getById(toId);
        if (optAccFrom.isPresent() && optAccTo.isPresent() && optAccFrom.get().amount() >= amount) {
            int newAmountFrom = optAccFrom.get().amount() - amount;
            update(new Account(optAccFrom.get().id(), newAmountFrom));
            int newAmountTo = optAccTo.get().amount() + amount;
            update(new Account(optAccTo.get().id(), newAmountTo));
            rslt = true;
        }
        return rslt;
    }
}
