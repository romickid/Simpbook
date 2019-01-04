package com.romickid.simpbook.fragments.Collection;

public class CollectionSummarize {
    private String text;


    /**
     * 构建CollectionSummarize实例
     *
     * @param tText 名称
     */
    CollectionSummarize(String tText) {
        text = tText;
    }

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getText() {
        return text;
    }

}
