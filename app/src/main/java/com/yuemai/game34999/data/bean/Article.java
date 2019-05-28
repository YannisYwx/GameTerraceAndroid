package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/26  16:30
 * @email : 923080261@qq.com
 * @description : 文章详情
 * "ID": 1907,
 * "NewsTitle": "四个月实验：VR环境下工作可大大提高效率",
 * "NewsIcon": "http://img1.shuowan.com/d/file/vrxw/2016-12-15/e5bcf37bc967e4978b8920e6aeed9074.jpg",
 * "NewsContent": "<p>　　如果你愿意带上头显和耳机，虚拟现实可以让你沉浸在另一个世界中。这种传送行为很适合游戏，但这也让我陷入沉思：VR这种沉浸效果能否让员工远离喋喋不休的同事，专注于工作并提高效率呢?</p><p></p><p>　　在尝试过几个可以把PC“搬进”VR之中的应用，并与已经在这种环境中工作过几个月的早期普及者聊过之后，我确信VR至少可以提高员工的效率。你可以将该技术视为一个巨大的屏幕，包裹着你的头部，为你提供世界上最大的多显示器设置。</p><p></p><p style=\"text-align:center\"><img style=\"max-width:100%;\" src=\"http://img1.shuowan.com/upload/image/20161215/1481783771272434.jpg\" title=\"1481783771272434.jpg\" alt=\"2.jpg\"/></p><p></p><p>　　纽约的软件工程师杰克·多诺万(Jack \r\nDonovan)说：“我在一个开放式办公室工作，有典型的噪音和荧光灯，但虚拟现实可以设置成我所希望的工作环境。”多诺万在工作时经常会戴着Oculus Rift \r\nVR头显。他继续说到， “我就这样一直工作了大约四个月，它对编程这样的工作真的有帮助，我想在一件事上高度集中，避免分心。”</p><p></p><p>　　Virtual \r\nDesktop、Envelop和Bigscreen都是这样的VR办公软件，可以把你的PC传送到虚拟世界。目前这三个应用只支持Windows，并需要连接高端VR头显，如Oculus \r\nRift或HTC Vive。</p><p></p><p>　　Virtual \r\nDesktop是三者之中最基础的应用。基本上，这款应用看起来就像一个环绕的工作区，比市场上的任何显示器都要打。作为最简单的VR桌面程序，Virtual \r\nDesktop最适合那些想要大屏幕体验但又不想投入太多学习时间的用户。</p><p></p><p http://img1.shuowan.com/upload/image/20161215/1481783792983577.jpg\" title=\"1481783792983577.jpg\" alt=\"3.jpg\"/></p><p></p><p>　　Envelop则更好地利用了VR的功能。“如果你将电脑显示器视为一个进入虚拟世界的窗口，使用Envelop就像是呆在窗口的另一边，”多诺万如是说道，“你可以自由真实地‘触控’，移动和操作你的程序，没有显示器的限制。”</p><p></p><p>　　Bigscreen是专为更多的协同工作而设计。该程序创建了一个共享虚拟环境(通过虚拟沙发实现)，其他用户可以挂出并共享屏幕。 \r\n多诺万坦言：“我喜欢它可以让你选择一个环境，帮助调整你工作的氛围。所以你可以决定是坐在屋顶上，还是坐在海滩上，甚至是在一个带壁炉的维多利亚豪宅中工作。”</p><p></p><p>　　那么什么样的工作才最适合VR呢，什么仍然适合在现实世界工作呢?我所采访的早期普及者都指出诸如Photoshop这样的视觉任务很适合在VR中操作。多诺万解释道：“如果你正在设计像广告牌这样的东西，在VR你可以调整它的尺寸，让其像一个真正的广告牌那样大。”</p><p></p><p style=\"text-align: center;\"><img style=\"max-width:100%;\" src=\"http://img1.shuowan.com/upload/image/20161215/1481783810202378.jpg\" title=\"1481783810202378.jpg\" alt=\"1481783810202378.jpg\"   border=\"0\" vspace=\"0\" /></p><p></p><p>　　但对于涉及大量文本处理的工作而言，这项技术还显得有点挣扎。虽然VR头显的分辨率正在提高，但文字的渲染还是会显得有点模糊。另外，在当前的VR环境中你是看不到鼠标和键盘。如果要在VR中完成大量的文本输入任务，你需要成为一名出色的触控输入者，或者你的盲打技能十分优秀，而这对某些用户而言还是存在一定的挑战。Envelop可通过摄像头把你的手部和键盘鼠标实时传输在视场之内，在一定程度上可让键盘操作更为轻松。</p><p></p><p>　　最后，长时间佩戴头显会令人感到不适。</p><p>　　目前的VR行业还处于起步阶段，但随着头显的价格不断下降，而办公软件的功能和性能也越发完善，如果VR将来没有成为办公室的解决方案，那么YiVian会感到十分意外。</p><p></p>",
 * "LikeNum": 0,
 * "NewsSource": "说玩网",
 * "ReadNum": 1329,
 * "CollectNum": 0,
 * "Author": "说玩小编      ",
 * "CategoryName": "VR新闻",
 * "CreateTime": "2016-12-15 00:00:00"
 */
public class Article {
    private int ID;
    private String NewsTitle;
    private String NewsIcon;
    private String NewsContent;
    private int LikeNum;
    private String NewsSource;
    private int ReadNum;
    private int CollectNum;
    private String Author;
    private String CategoryName;
    private String CreateTime;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getNewsIcon() {
        return NewsIcon;
    }

    public void setNewsIcon(String newsIcon) {
        NewsIcon = newsIcon;
    }

    public String getNewsContent() {
        return NewsContent;
    }

    public void setNewsContent(String newsContent) {
        NewsContent = newsContent;
    }

    public int getLikeNum() {
        return LikeNum;
    }

    public void setLikeNum(int likeNum) {
        LikeNum = likeNum;
    }

    public String getNewsSource() {
        return NewsSource;
    }

    public void setNewsSource(String newsSource) {
        NewsSource = newsSource;
    }

    public int getReadNum() {
        return ReadNum;
    }

    public void setReadNum(int readNum) {
        ReadNum = readNum;
    }

    public int getCollectNum() {
        return CollectNum;
    }

    public void setCollectNum(int collectNum) {
        CollectNum = collectNum;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    @Override
    public String toString() {
        return "Article{" +
                "ID=" + ID +
                ", NewsTitle='" + NewsTitle + '\'' +
                ", NewsIcon='" + NewsIcon + '\'' +
                ", NewsContent='" + NewsContent + '\'' +
                ", LikeNum=" + LikeNum +
                ", NewsSource='" + NewsSource + '\'' +
                ", ReadNum=" + ReadNum +
                ", CollectNum=" + CollectNum +
                ", Author='" + Author + '\'' +
                ", CategoryName='" + CategoryName + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                '}';
    }
}
