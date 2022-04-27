package com.DLY.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 
 * @TableName tb_clazz
 */
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_clazz")
public class Clazz implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 班级人数
     */
    private Integer number;

    /**
     * 班级介绍
     */
    private String introducation;

    /**
     * 班主任的名字
     */
    private String headmaster;

    /**
     * 班主任邮箱
     */
    private String email;

    /**
     * 班主任电话
     */
    private String telephone;

    /**
     * 所属的年级
     */
    private String gradeName;

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
        Clazz other = (Clazz) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getNumber() == null ? other.getNumber() == null : this.getNumber().equals(other.getNumber()))
            && (this.getIntroducation() == null ? other.getIntroducation() == null : this.getIntroducation().equals(other.getIntroducation()))
            && (this.getHeadmaster() == null ? other.getHeadmaster() == null : this.getHeadmaster().equals(other.getHeadmaster()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getTelephone() == null ? other.getTelephone() == null : this.getTelephone().equals(other.getTelephone()))
            && (this.getGradeName() == null ? other.getGradeName() == null : this.getGradeName().equals(other.getGradeName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getNumber() == null) ? 0 : getNumber().hashCode());
        result = prime * result + ((getIntroducation() == null) ? 0 : getIntroducation().hashCode());
        result = prime * result + ((getHeadmaster() == null) ? 0 : getHeadmaster().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getTelephone() == null) ? 0 : getTelephone().hashCode());
        result = prime * result + ((getGradeName() == null) ? 0 : getGradeName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", number=").append(number);
        sb.append(", introducation=").append(introducation);
        sb.append(", headmaster=").append(headmaster);
        sb.append(", email=").append(email);
        sb.append(", telephone=").append(telephone);
        sb.append(", gradeName=").append(gradeName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}