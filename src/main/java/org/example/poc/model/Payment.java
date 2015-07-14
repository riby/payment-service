package org.example.poc.model;

/**
 * Created by riby on 7/14/15.
 */
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
@Table
public class Payment {
    @PrimaryKey
    private Long id;
    private String cardType;
    private String customerName;
    private String creditCardNumber;
    private String expireDate;
    private String securityCode;
    public Payment(Long id, String cardType,String customerName,String creditCardNumber,String expireDate,String securityCode)
    {

        this.id=id;
        this.cardType=cardType;
        this.customerName=customerName;
        this.creditCardNumber=creditCardNumber;
        this.expireDate=expireDate;
        this.securityCode=securityCode;
    }
    public Long getId()
    {return id;}

    public String getCardType()
    {
            return cardType;
    }
    public String getCustomerName()
    {
        return customerName;
    }
    public String getCreditCardNumber()
    {
        return creditCardNumber;
    }
    public String getExpireDate()
    {
        return expireDate;
    }
    public String getSecurityCode()
    {
        return securityCode;
    }
}


