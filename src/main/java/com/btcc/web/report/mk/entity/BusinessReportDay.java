package com.btcc.web.report.mk.entity;


/**
 * Created by peiyou on 2016/10/31.
 */
public class BusinessReportDay {
    //统计时间
    private String statisDate;

    // '类型1表示按天'
    private int type ;

    //充值比特币次数
    private Integer fundbtcCount ;

    //'充值比特币金额'
    private Double fundbtcAmount ;

    //'充值比特币人数',
    private Integer fundbtcUsers ;

    // '提现比特币次数',
    private Integer withdrawbtcCount ;

    //'提现比特币总量'
    private Double withdrawbtcAmount;

    //'提现比特币手续费'
    private Double withdrawbtcFee;

    //'提现比特币人数'
    private Integer withdrawbtcUsers;

    //'充值人民币次数'
    private Integer fundcnyCount;

    //'充值人民币总量'
    private Double fundcnyAmount;

    //'充值人民币人数'
    private Integer fundcnyUsers;

    //'提现人民币次数'
    private Integer withdrawcnyCount;

    //'提现人民币金额',
    private Double withdrawcnyAmount;

    //'提现人民币手续费'
    private Double withdrawcnyFee;

    //'提现人民币用户数'
    private Integer withdrawcnyUsers;

    //'交易用户数'
    private Integer tradeUsers;

    //'交易量'
    private Double tradeAmount;

    //'api交易量'
    private Double apiTradeAmount ;

    //'api交易人数'
    private Integer apiTradeUsers;

    //'借贷人民币用户数'
    private Integer borrowedCnyUsers;

    //'借贷人民币量'
    private Double borrowedCnyAmount;

    //'借货比特币用户数'
    private Integer borrowedBtcUsers;

    //'借贷比特币量'
    private Double borrowedBtcAmount;

    //'人民币借贷利息（已收取）'
    private Double borrowedCnyInterestCollected;

    //'比特币借贷利息（已收取）'
    private Double borrowedBtcInterestCollected;

    public String getStatisDate() {
        return statisDate;
    }

    public void setStatisDate(String statisDate) {
        this.statisDate = statisDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getFundbtcCount() {
        return fundbtcCount;
    }

    public void setFundbtcCount(Integer fundbtcCount) {
        this.fundbtcCount = fundbtcCount;
    }

    public Double getFundbtcAmount() {
        return fundbtcAmount;
    }

    public void setFundbtcAmount(Double fundbtcAmount) {
        this.fundbtcAmount = fundbtcAmount;
    }

    public Integer getFundbtcUsers() {
        return fundbtcUsers;
    }

    public void setFundbtcUsers(Integer fundbtcUsers) {
        this.fundbtcUsers = fundbtcUsers;
    }

    public Integer getWithdrawbtcCount() {
        return withdrawbtcCount;
    }

    public void setWithdrawbtcCount(Integer withdrawbtcCount) {
        this.withdrawbtcCount = withdrawbtcCount;
    }

    public Double getWithdrawbtcAmount() {
        return withdrawbtcAmount;
    }

    public void setWithdrawbtcAmount(Double withdrawbtcAmount) {
        this.withdrawbtcAmount = withdrawbtcAmount;
    }

    public Double getWithdrawbtcFee() {
        return withdrawbtcFee;
    }

    public void setWithdrawbtcFee(Double withdrawbtcFee) {
        this.withdrawbtcFee = withdrawbtcFee;
    }

    public Integer getWithdrawbtcUsers() {
        return withdrawbtcUsers;
    }

    public void setWithdrawbtcUsers(Integer withdrawbtcUsers) {
        this.withdrawbtcUsers = withdrawbtcUsers;
    }

    public Integer getFundcnyCount() {
        return fundcnyCount;
    }

    public void setFundcnyCount(Integer fundcnyCount) {
        this.fundcnyCount = fundcnyCount;
    }

    public Double getFundcnyAmount() {
        return fundcnyAmount;
    }

    public void setFundcnyAmount(Double fundcnyAmount) {
        this.fundcnyAmount = fundcnyAmount;
    }

    public Integer getFundcnyUsers() {
        return fundcnyUsers;
    }

    public void setFundcnyUsers(Integer fundcnyUsers) {
        this.fundcnyUsers = fundcnyUsers;
    }

    public Integer getWithdrawcnyCount() {
        return withdrawcnyCount;
    }

    public void setWithdrawcnyCount(Integer withdrawcnyCount) {
        this.withdrawcnyCount = withdrawcnyCount;
    }

    public Double getWithdrawcnyAmount() {
        return withdrawcnyAmount;
    }

    public void setWithdrawcnyAmount(Double withdrawcnyAmount) {
        this.withdrawcnyAmount = withdrawcnyAmount;
    }

    public Double getWithdrawcnyFee() {
        return withdrawcnyFee;
    }

    public void setWithdrawcnyFee(Double withdrawcnyFee) {
        this.withdrawcnyFee = withdrawcnyFee;
    }

    public Integer getWithdrawcnyUsers() {
        return withdrawcnyUsers;
    }

    public void setWithdrawcnyUsers(Integer withdrawcnyUsers) {
        this.withdrawcnyUsers = withdrawcnyUsers;
    }

    public Integer getTradeUsers() {
        return tradeUsers;
    }

    public void setTradeUsers(Integer tradeUsers) {
        this.tradeUsers = tradeUsers;
    }

    public Double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Double getApiTradeAmount() {
        return apiTradeAmount;
    }

    public void setApiTradeAmount(Double apiTradeAmount) {
        this.apiTradeAmount = apiTradeAmount;
    }

    public Integer getApiTradeUsers() {
        return apiTradeUsers;
    }

    public void setApiTradeUsers(Integer apiTradeUsers) {
        this.apiTradeUsers = apiTradeUsers;
    }

    public Integer getBorrowedCnyUsers() {
        return borrowedCnyUsers;
    }

    public void setBorrowedCnyUsers(Integer borrowedCnyUsers) {
        this.borrowedCnyUsers = borrowedCnyUsers;
    }

    public Double getBorrowedCnyAmount() {
        return borrowedCnyAmount;
    }

    public void setBorrowedCnyAmount(Double borrowedCnyAmount) {
        this.borrowedCnyAmount = borrowedCnyAmount;
    }

    public Integer getBorrowedBtcUsers() {
        return borrowedBtcUsers;
    }

    public void setBorrowedBtcUsers(Integer borrowedBtcUsers) {
        this.borrowedBtcUsers = borrowedBtcUsers;
    }

    public Double getBorrowedBtcAmount() {
        return borrowedBtcAmount;
    }

    public void setBorrowedBtcAmount(Double borrowedBtcAmount) {
        this.borrowedBtcAmount = borrowedBtcAmount;
    }

    public Double getBorrowedCnyInterestCollected() {
        return borrowedCnyInterestCollected;
    }

    public void setBorrowedCnyInterestCollected(Double borrowedCnyInterestCollected) {
        this.borrowedCnyInterestCollected = borrowedCnyInterestCollected;
    }

    public Double getBorrowedBtcInterestCollected() {
        return borrowedBtcInterestCollected;
    }

    public void setBorrowedBtcInterestCollected(Double borrowedBtcInterestCollected) {
        this.borrowedBtcInterestCollected = borrowedBtcInterestCollected;
    }


    @Override
    public String toString() {
        return "BusinessReportDay{" +
                "statisDate='" + statisDate + '\'' +
                ", type=" + type +
                ", fundbtcCount=" + fundbtcCount +
                ", fundbtcAmount=" + fundbtcAmount +
                ", fundbtcUsers=" + fundbtcUsers +
                ", withdrawbtcCount=" + withdrawbtcCount +
                ", withdrawbtcAmount=" + withdrawbtcAmount +
                ", withdrawbtcFee=" + withdrawbtcFee +
                ", withdrawbtcUsers=" + withdrawbtcUsers +
                ", fundcnyCount=" + fundcnyCount +
                ", fundcnyAmount=" + fundcnyAmount +
                ", fundcnyUsers=" + fundcnyUsers +
                ", withdrawcnyCount=" + withdrawcnyCount +
                ", withdrawcnyAmount=" + withdrawcnyAmount +
                ", withdrawcnyFee=" + withdrawcnyFee +
                ", withdrawcnyUsers=" + withdrawcnyUsers +
                ", tradeUsers=" + tradeUsers +
                ", tradeAmount=" + tradeAmount +
                ", apiTradeAmount=" + apiTradeAmount +
                ", apiTradeTsers=" + apiTradeUsers +
                ", borrowedCnyUsers=" + borrowedCnyUsers +
                ", borrowedCnyAmount=" + borrowedCnyAmount +
                ", borrowedBtcUsers=" + borrowedBtcUsers +
                ", borrowedBtcAmount=" + borrowedBtcAmount +
                ", borrowedCnyInterestCollected=" + borrowedCnyInterestCollected +
                ", borrowedBtcInterestCollected=" + borrowedBtcInterestCollected +
                '}';
    }
}
