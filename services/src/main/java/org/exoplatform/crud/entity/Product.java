package org.exoplatform.crud.entity;


import javax.annotation.Generated;
import javax.persistence.*;

/**
 * @author <a href="mailto:obouras@exoplatform.com">Omar Bouras</a>
 * @version ${Revision}
 * @date 03/09/15
 */
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "category")
    private String category;
    @Column(name = "company")
    private String company;
    @Column(name= "label")
    private String label;
    @Column(name="price")
    private double price;

    public Product(){

    }

    public Product(int id,String category,String company,String label,float price){
        this.id=id;
        this.category=category;
        this.company=company;
        this.label=label;
        this.price=price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
