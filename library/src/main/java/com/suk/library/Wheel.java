package com.suk.library;

/**
 * 轮子
 *
 * @author Felix.Zhong
 */
public class Wheel {
    /**
     * 内容
     */
    private String[] texts;
    /**
     * 单位
     */
    private String unit;
    /**
     * 焦点文字
     */
    private String focusText;
    /**
     * 焦点文字颜色
     */
    private int focusTextColor;
    /**
     *非焦点文字颜色
     */
    private int optionTextColor;
    /**
     * 单位颜色
     */
    private int unitTextColor;
    /**
     * 焦点字体大小
     */
    private int focusTextsize;
    /**
     *非焦点文字颜色
     */
    private int optionTextsize;
    /**
     * 单位字体大小
     */
    private int unitTextsize;

    public Wheel(String[] texts, String unit, String focusText,
                 int focusTextColor, int optionTextColor, int unitTextColor,
                 int focusTextsize, int optionTextsize, int unitTextsize) {
        super();
        this.texts = texts;
        this.unit = unit;
        this.focusText = focusText;
        this.focusTextColor = focusTextColor;
        this.optionTextColor = optionTextColor;
        this.unitTextColor = unitTextColor;
        this.focusTextsize = focusTextsize;
        this.optionTextsize = optionTextsize;
        this.unitTextsize = unitTextsize;
    }

    public String[] getTexts() {
        return texts;
    }

    public String getUnit() {
        return unit;
    }

    public int getFocusTextPosition() {
        int position = 0;
        int count = texts.length;
        if (count > 0) {
            for (int i = 0; i < texts.length; i++) {
                if (texts[i].equals(focusText)) {
                    position = i;
                }
            }
            if (position == 0) {
                position = -1;
            }
        } else {
            position = -1;
        }

        return position;
    }

    public int getFocusTextColor() {
        return focusTextColor;
    }

    public int getOptionTextColor() {
        return optionTextColor;
    }

    public int getUnitTextColor() {
        return unitTextColor;
    }

    public int getFocusTextsize() {
        return focusTextsize;
    }

    public int getOptionTextsize() {
        return optionTextsize;
    }

    public int getUnitTextsize() {
        return unitTextsize;
    }
}
