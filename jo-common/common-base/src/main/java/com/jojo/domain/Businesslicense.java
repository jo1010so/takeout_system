package com.jojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName BusinessLicense
 */
@TableName(value ="BusinessLicense")
@Data
public class Businesslicense implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 
     */
    private String idNo;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String licenseUrl;

    /**
     * 
     */
    private String bucket;

    /**
     * 
     */
    private String object;

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
        Businesslicense other = (Businesslicense) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIdNo() == null ? other.getIdNo() == null : this.getIdNo().equals(other.getIdNo()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getLicenseUrl() == null ? other.getLicenseUrl() == null : this.getLicenseUrl().equals(other.getLicenseUrl()))
            && (this.getBucket() == null ? other.getBucket() == null : this.getBucket().equals(other.getBucket()))
            && (this.getObject() == null ? other.getObject() == null : this.getObject().equals(other.getObject()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIdNo() == null) ? 0 : getIdNo().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getLicenseUrl() == null) ? 0 : getLicenseUrl().hashCode());
        result = prime * result + ((getBucket() == null) ? 0 : getBucket().hashCode());
        result = prime * result + ((getObject() == null) ? 0 : getObject().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", idNo=").append(idNo);
        sb.append(", name=").append(name);
        sb.append(", licenseUrl=").append(licenseUrl);
        sb.append(", bucket=").append(bucket);
        sb.append(", object=").append(object);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}