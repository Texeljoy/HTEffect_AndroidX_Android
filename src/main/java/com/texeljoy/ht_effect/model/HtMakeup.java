package com.texeljoy.ht_effect.model;

import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.hteffect.HTEffect;

/**
 * @ClassName HtMakeup
 * @Description TODO
 * @Author lu guaiguai
 * @Date 2023/9/14 10:51
 */
public class HtMakeup {
    public static final HtMakeup NO_MAKEUP = new HtMakeup("", "无", "NONE", "", "", 2, 0, -1);

    public HtMakeup(String name, String title, String titleEn, String category, String icon, int download, int idCard, int type) {

        this.name = name;
        this.title = title;
        this.title_en = titleEn;
        this.category = category;
        this.icon = icon;
        this.download = download;
        this.idCard = idCard;
        this.type = type;

    }

    @Override public String toString() {
        return "HtMakeup{" +
            "name='" + name + '\'' +
            ", title='" + title + '\'' +
            ", title_en='" + title_en + '\'' +
            ", category='" + category + '\'' +
            ", icon='" + icon + '\'' +
            ", download=" + download + '\'' +
            ", idCard=" + idCard + '\'' +
            ", type=" + type + '\'' +
            '}';
    }

    /**
     * name
     */
    private String name;
    /**
     * title
     */
    private String title;
    private String title_en;
    /**
     * category
     */
    private String category;
    /**
     * icon
     */
    private String icon;
    /**
     * downloaded
     */
    private int download;
    /**
     * type
     */
    private int idCard;

    private int type;
    public String getUrl() {

        return HTEffect.shareInstance().getMakeupUrl(idCard) + name + ".zip";
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getTitleEn() {return title_en;}

    public void setTitleEn(String titleEn) {this.title_en = titleEn;}

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}

    public String getIcon() {
        //return HTEffect.shareInstance().getMakeupPath(idCard) + "/ICON/" + this.icon;
        return HTEffect.shareInstance().getMakeupUrl(idCard) + this.icon;
    }

    public void setThumb(String icon) {this.icon = icon;}

    public int isDownloaded() {return download;}

    public void setDownloaded(int download) {
        this.download = download;
    }

    public int getIdCard(){return idCard;}

    public void setIdCard(int idCard){this.idCard = idCard;}

    public int getType() {return type;}

    public void setType(int type) {this.type = type;}

    /*public int getType() {return idCard;}

    public void setType(int type) {this.idCard = type;}*/
    /**
     * 下载完成更新缓存数据
     */
    public void downloaded() {
        switch (idCard){
            case 0:
                HtLipstickConfig htLipstickConfig = HtConfigTools.getInstance().getLipstickList();

                for (HtMakeup makeup : htLipstickConfig.getLipsticks()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(idCard,new Gson().toJson(htLipstickConfig));
                break;
            case 1:
                HtEyebrowConfig htEyebrowConfig = HtConfigTools.getInstance().getEyebrowList();

                for (HtMakeup makeup : htEyebrowConfig.getEyebrows()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(idCard,new Gson().toJson(htEyebrowConfig));
                break;
            case 2:
                HtBlushConfig htBlushConfig = HtConfigTools.getInstance().getBlushList();

                for (HtMakeup makeup : htBlushConfig.getBlushes()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(idCard,new Gson().toJson(htBlushConfig));
                break;
            case 3:
                HtEyeshadowConfig htEyeshadowConfig = HtConfigTools.getInstance().getEyeshadowList();

                for (HtMakeup makeup : htEyeshadowConfig.getEyeshadows()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(idCard,new Gson().toJson(htEyeshadowConfig));
                break;
            case 4:
                HtEyelineConfig htEyelineConfig = HtConfigTools.getInstance().getEyelineList();

                for (HtMakeup makeup : htEyelineConfig.getEyeliners()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(idCard,new Gson().toJson(htEyelineConfig));
                break;
            case 5:
                HtEyelashConfig htEyelashConfig = HtConfigTools.getInstance().getEyelashList();

                for (HtMakeup makeup : htEyelashConfig.getEyelashes()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(idCard,new Gson().toJson(htEyelashConfig));
                break;
            case 6:
                    HtPupilsConfig htPupilsConfig = HtConfigTools.getInstance().getPupilsList();

                for (HtMakeup makeup : htPupilsConfig.getPupils()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(idCard,new Gson().toJson(htPupilsConfig));
                break;

        }


    }
}
