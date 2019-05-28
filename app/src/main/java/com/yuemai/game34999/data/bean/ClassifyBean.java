package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  11:59
 * @email : 923080261@qq.com
 * @description : 游戏分类实体类
 */
public class ClassifyBean {
    private String gameType;
    private int gameIcon;
    private int gameBg;

    public ClassifyBean(String gameType, int gameIcon, int gameBg) {
        this.gameType = gameType;
        this.gameIcon = gameIcon;
        this.gameBg = gameBg;
    }

    public ClassifyBean(){

    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public int getGameIcon() {
        return gameIcon;
    }

    public void setGameIcon(int gameIcon) {
        this.gameIcon = gameIcon;
    }

    public int getGameBg() {
        return gameBg;
    }

    public void setGameBg(int gameBg) {
        this.gameBg = gameBg;
    }
}
