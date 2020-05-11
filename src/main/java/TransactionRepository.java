import data.Transaction;
import data.Type;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class TransactionRepository {

    TransactionDao transactionDao = new TransactionDao();
    Scanner scanner = new Scanner(System.in);

    public void run(){

        while(true){
            System.out.println("What do you want to do?");
            System.out.println("0. Exit");
            System.out.println("1. Save record to the base");
            System.out.println("2. Print all expenses");
            System.out.println("3. Print all incomes");
            System.out.println("4. Update record from the base");
            System.out.println("5. Delete record from the base");

            String operation = scanner.nextLine();

            switch(operation){
                case "0":
                    transactionDao.close();
                    break;
                case "1":
                    saveTransaction();
                    break;
                case "2":
                    readAllExpenses();
                    break;
                case "3":
                    readAllIncomes();
                    break;
                case "4":
                    updateRecord();
                    break;
                case "5":
                    delete();
                    break;
            }
        }
    }

    private void saveTransaction() {

        System.out.println("Type of transaction (EXPENSE or INCOME)");
        String type = scanner.nextLine();
        type = checkIfTypeIsCorrect(type);

        System.out.println("Description (max.50 characters)");
        String description = scanner.nextLine();

        System.out.println("Amount of transaction");
        BigDecimal amount = scanner.nextBigDecimal();

        System.out.println("Date of transaction");
        scanner.nextLine();
        String date = scanner.nextLine();
        date = validateDate(date);

        Transaction transaction = new Transaction(type, description, amount, date);

        if("".equals(transaction.getType()) || "".equals(transaction.getDescription()) || "".equals(String.valueOf(transaction.getAmount()))){
            System.out.println("ERROR");
        } else {
            transactionDao.save(transaction);
            System.out.println("Success");
        }
    }

    private String checkIfTypeIsCorrect(String type) {
        if (!type.equals(Type.EXPENSE.name()) && !type.equals(Type.INCOME.name())) {
            System.out.println("\"EXPENSE\" or \"INCOME\" allowed only");
            type = scanner.nextLine();
        }
        return type;
    }
    private String validateDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(date.trim());
        } catch (ParseException pe) {
            System.out.println("Date format is incorrect. Correct is format dd-MM-yyyy. Try again");
            date = scanner.nextLine();
            return date;
        }
        return date;
    }

    private void updateRecord(){

        System.out.println("Pass ID of transaction, which you want to update");
        long id = scanner.nextLong();

        System.out.println("New type of transaction (EXPENSE or INCOME)");
        scanner.nextLine();
        String type = scanner.nextLine();
        type = checkIfTypeIsCorrect(type);

        System.out.println("New description (max.50 characters)");
        String description = scanner.nextLine();

        System.out.println("New amount of transaction");
        BigDecimal amount = scanner.nextBigDecimal();

        System.out.println("New date of transaction");
        scanner.nextLine();
        String date = scanner.nextLine();
        date = validateDate(date);

        Transaction transaction = new Transaction(type, description, amount, date);
        transaction.setId(id);

        if("".equals(transaction.getType()) || "".equals(transaction.getDescription()) ||
                "".equals(String.valueOf(transaction.getAmount())) || 0 == transaction.getId()){
            System.out.println("ERROR");
        } else {
            transactionDao.update(transaction);
            System.out.println("Successfully updated");
        }
    }

    private void delete(){
        System.out.println("Pass ID of transaction, which you want to delete");
        long id = scanner.nextLong();
        if(0 == id){
            System.out.println("ERROR");
        } else {
            transactionDao.delete(id);
            System.out.println("Successfully deleted");
        }
    }

    private void readAllExpenses(){
        List<Transaction> read = transactionDao.read(Type.EXPENSE);
        read.forEach(System.out::println);
    }

    private void readAllIncomes(){
        List<Transaction> read = transactionDao.read(Type.INCOME);
        read.forEach(System.out::println);
    }
}