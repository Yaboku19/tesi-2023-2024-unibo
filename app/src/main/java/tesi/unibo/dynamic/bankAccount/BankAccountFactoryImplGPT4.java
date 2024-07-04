package tesi.unibo.dynamic.bankAccount;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
public class BankAccountFactoryImplGPT4 implements BankAccountFactory {

    @Override
    public BankAccount createBasic() {
        return new BasicBankAccount();
    }

    @Override
    public BankAccount createWithFee(UnaryOperator<Integer> feeFunction) {
        return new FeeBankAccount(feeFunction);
    }

    @Override
    public BankAccount createWithCredit(Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction) {
        return new CreditBankAccount(allowedCredit, rateFunction);
    }

    @Override
    public BankAccount createWithBlock(BiPredicate<Integer, Integer> blockingPolicy) {
        return new BlockBankAccount(blockingPolicy);
    }

    @Override
    public BankAccount createWithFeeAndCredit(UnaryOperator<Integer> feeFunction, Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction) {
        return new FeeAndCreditBankAccount(feeFunction, allowedCredit, rateFunction);
    }
}

class BasicBankAccount implements BankAccount {
    protected int balance = 0;

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void deposit(int amount) {
        balance += amount;
    }

    @Override
    public boolean withdraw(int amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }
}

class FeeBankAccount extends BasicBankAccount {
    protected UnaryOperator<Integer> feeFunction;

    public FeeBankAccount(UnaryOperator<Integer> feeFunction) {
        this.feeFunction = feeFunction;
    }

    @Override
    public boolean withdraw(int amount) {
        int fee = feeFunction.apply(amount);
        if (amount + fee > getBalance()) {
            return false;
        }
        balance -= amount + fee;
        return true;
    }
}

class CreditBankAccount extends BasicBankAccount {
    private Predicate<Integer> allowedCredit;
    private UnaryOperator<Integer> rateFunction;

    public CreditBankAccount(Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateTimeFunction) {
        this.allowedCredit = allowedCredit;
        this.rateFunction = rateTimeFunction;
    }

    @Override
    public boolean withdraw(int amount) {
        int newBalance = getBalance() - amount;
        if (allowedCredit.test(newBalance)) {
            balance = newBalance;
            int fee = rateFunction.apply(-newBalance); // newBalance is negative if in overdraft
            balance -= fee;
            return true;
        }
        return false;
    }
}

class BlockBankAccount extends BasicBankAccount {
    private BiPredicate<Integer, Integer> blockingPolicy;
    private boolean blocked = false;

    public BlockBankAccount(BiPredicate<Integer, Integer> blockingPolicy) {
        this.blockingPolicy = blockingPolicy;
    }

    @Override
    public boolean withdraw(int amount) {
        if (blocked || blockingPolicy.test(amount, getBalance())) {
            blocked = true;
            return false;
        }
        balance -= amount;
        return true;
    }
}

class FeeAndCreditBankAccount extends FeeBankAccount {
    private Predicate<Integer> allowedCredit;
    private UnaryOperator<Integer> rateFunction;

    public FeeAndCreditBankAccount(UnaryOperator<Integer> feeFunction, Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction) {
        super(feeFunction);
        this.allowedCredit = allowedCredit;
        this.rateFunction = rateFunction;
    }

    @Override
    public boolean withdraw(int amount) {
        int fee = feeFunction.apply(amount);
        int actualWithdrawal = amount + fee;
        int newBalance = getBalance() - actualWithdrawal;
        if (allowedCredit.test(newBalance)) {
            balance -= actualWithdrawal;
            int tax = rateFunction.apply(-newBalance);
            balance -= tax;
            return true;
        }
        return false;
    }
}
