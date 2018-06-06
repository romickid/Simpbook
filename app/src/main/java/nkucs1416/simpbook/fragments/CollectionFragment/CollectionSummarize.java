package nkucs1416.simpbook.fragments.CollectionFragment;

public class CollectionSummarize {
    private String text;

    /**
     * 构建CollectionSummarize实例
     *
     * @param tText 名称
     */
    public CollectionSummarize(String tText) {
        text = tText;
    }

    /**
     * 构建空的实例, 将其作为占位使用
     */
    public CollectionSummarize() {
        text = "";
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
