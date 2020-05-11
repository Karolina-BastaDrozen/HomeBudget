package data;

import java.math.BigDecimal;

public class Transaction {
    private long id;
    private Type type;
    private String description;
    private BigDecimal amount;
    private String date;

    public Transaction(String type, String description, BigDecimal amount, String date) {
        this.type = Type.valueOf(type);
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type.name();
    }

    public void setType(String typeAsString) {
        this.type = Type.valueOf(typeAsString);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Transaction() {
    }

    @Override
    public String toString() {
        return "Transaction:\n " + "id: " + id + ", type: " + type + ", description:" + description +
                ", amount: " + amount + ", date: " + date;
    }
}