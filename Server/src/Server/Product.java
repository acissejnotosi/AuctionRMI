/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author Jessica
 */
public class Product {
    public static String produtcName;
    public static Float initialPrice;
    public static String Description;
    public static Integer timeOfAuction;
    public static Float newPrice;

    public static String getProdutcName() {
        return produtcName;
    }

    public static Float getInitialPrice() {
        return initialPrice;
    }

    public static String getDescription() {
        return Description;
    }

    public static Integer getTimeOfAuction() {
        return timeOfAuction;
    }

    public static Float getNewPrice() {
        return newPrice;
    }

    public static void setProdutcName(String produtcName) {
        Product.produtcName = produtcName;
    }

    public static void setInitialPrice(Float initialPrice) {
        Product.initialPrice = initialPrice;
    }

    public static void setDescription(String Description) {
        Product.Description = Description;
    }

    public static void setTimeOfAuction(Integer timeOfAuction) {
        Product.timeOfAuction = timeOfAuction;
    }

    public static void setNewPrice(Float newPrice) {
        Product.newPrice = newPrice;
    }
    
}
