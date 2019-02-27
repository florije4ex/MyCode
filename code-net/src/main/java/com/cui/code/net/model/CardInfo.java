package com.cui.code.net.model;

/**
 * 预订卡信息
 */
public class CardInfo {
    private String cardName;
    private String cardId;
    private String cardNo;

    public CardInfo(String cardName, String cardId, String cardNo) {
        this.cardName = cardName;
        this.cardId = cardId;
        this.cardNo = cardNo;
    }

    public CardInfo(String cardId, String cardNo) {
        this.cardId = cardId;
        this.cardNo = cardNo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public String toString() {
        return "CardInfo{" +
                "cardName='" + cardName + '\'' +
                ", cardId='" + cardId + '\'' +
                ", cardNo='" + cardNo + '\'' +
                '}';
    }
}
