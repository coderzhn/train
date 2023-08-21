package com.zhn.train.common.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResp<T> implements Serializable {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 当前页的列表
     */
    private List<T> list;

    @Override
    public String toString() {
        return "PageResp{" +
                "total=" + total +
                ", list=" + list +
                '}';
    }
}
