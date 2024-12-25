package com.jojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName Feedback
 */
@TableName(value ="Feedback")
@Data
public class Feedback implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer orderId;

    /**
     * 
     */
    private Integer rating;

    /**
     * 
     */
    private String merchantReview;

    /**
     * 
     */
    private String deliveryManReview;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Feedback other = (Feedback) that;
        return (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getRating() == null ? other.getRating() == null : this.getRating().equals(other.getRating()))
            && (this.getMerchantReview() == null ? other.getMerchantReview() == null : this.getMerchantReview().equals(other.getMerchantReview()))
            && (this.getDeliveryManReview() == null ? other.getDeliveryManReview() == null : this.getDeliveryManReview().equals(other.getDeliveryManReview()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getRating() == null) ? 0 : getRating().hashCode());
        result = prime * result + ((getMerchantReview() == null) ? 0 : getMerchantReview().hashCode());
        result = prime * result + ((getDeliveryManReview() == null) ? 0 : getDeliveryManReview().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", rating=").append(rating);
        sb.append(", merchantReview=").append(merchantReview);
        sb.append(", deliveryManReview=").append(deliveryManReview);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}