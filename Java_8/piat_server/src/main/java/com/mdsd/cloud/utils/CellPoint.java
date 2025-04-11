package com.mdsd.cloud.utils;

import lombok.Data;

/**
 * 图片坐标类
 */
@Data
public class CellPoint {
    private int row1;
    private int col1;
    private int row2;
    private int col2;

    public CellPoint(int row1, int col1, int row2, int col2) {
        this.row1 = row1;
        this.col1 = col1;
        this.row2 = row2;
        this.col2 = col2;
    }
}
