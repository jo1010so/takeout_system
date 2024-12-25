package com.jojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName Delivery
 */
@TableName(value ="Delivery")
@Data
public class Delivery implements Serializable {
    /**
     * 
     */
    private Integer deliverymanId;

    /**
     * 
     */
    @TableId
    private Integer orderId;

    /**
     * 
     */
    private Date arrivalTime;

    /**
     * 
     */
    private BigDecimal deliveryAmount;

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
        Delivery other = (Delivery) that;
        return (this.getDeliverymanId() == null ? other.getDeliverymanId() == null : this.getDeliverymanId().equals(other.getDeliverymanId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getArrivalTime() == null ? other.getArrivalTime() == null : this.getArrivalTime().equals(other.getArrivalTime()))
            && (this.getDeliveryAmount() == null ? other.getDeliveryAmount() == null : this.getDeliveryAmount().equals(other.getDeliveryAmount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeliverymanId() == null) ? 0 : getDeliverymanId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getArrivalTime() == null) ? 0 : getArrivalTime().hashCode());
        result = prime * result + ((getDeliveryAmount() == null) ? 0 : getDeliveryAmount().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deliverymanId=").append(deliverymanId);
        sb.append(", orderId=").append(orderId);
        sb.append(", arrivalTime=").append(arrivalTime);
        sb.append(", deliveryAmount=").append(deliveryAmount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}