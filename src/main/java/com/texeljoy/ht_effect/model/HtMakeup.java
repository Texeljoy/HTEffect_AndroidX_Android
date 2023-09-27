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



    public static final HtMakeup NO_MAKEUP = new HtMakeup("", "无", "", "", 2, 0);

    public HtMakeup(String name, String title, String category, String icon, int download, int type) {

        this.name = name;
        this.title = title;
        this.category = category;
        this.icon = icon;
        this.download = download;
        this.type = type;

    }

    @Override public String toString() {
        return "HtMakeup{" +
            "name='" + name + '\'' +
            ", title='" + title + '\'' +
            ", category='" + category + '\'' +
            ", icon='" + icon + '\'' +
            ", download=" + download + '\'' +
            ", type=" + type +
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
    private int type;

    public String getUrl() {

        return HTEffect.shareInstance().getMakeupUrl(type) + name + ".zip";
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}

    public String getIcon() {
        return HTEffect.shareInstance().getMakeupPath(type) + "/ICON/" + this.icon;
    }

    public void setThumb(String icon) {this.icon = icon;}

    public int isDownloaded() {return download;}

    public void setDownloaded(int download) {
        this.download = download;
    }
    public int getType() {return type;}

    public void setType(int type) {this.type = type;}
    /**
     * 下载完成更新缓存数据
     */
    public void downloaded() {
        switch (type){
            case 0:
                HtLipstickConfig htLipstickConfig = HtConfigTools.getInstance().getLipstickList();

                for (HtMakeup makeup : htLipstickConfig.getMakeups()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(type,new Gson().toJson(htLipstickConfig));
                break;
            case 1:
                HtEyebrowConfig htEyebrowConfig = HtConfigTools.getInstance().getEyebrowList();

                for (HtMakeup makeup : htEyebrowConfig.getMakeups()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(type,new Gson().toJson(htEyebrowConfig));
                break;
            case 2:
                HtBlushConfig htBlushConfig = HtConfigTools.getInstance().getBlushList();

                for (HtMakeup makeup : htBlushConfig.getMakeups()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(type,new Gson().toJson(htBlushConfig));
                break;
            case 3:
                HtEyeshadowConfig htEyeshadowConfig = HtConfigTools.getInstance().getEyeshadowList();

                for (HtMakeup makeup : htEyeshadowConfig.getMakeups()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(type,new Gson().toJson(htEyeshadowConfig));
                break;
            case 4:
                HtEyelineConfig htEyelineConfig = HtConfigTools.getInstance().getEyelineList();

                for (HtMakeup makeup : htEyelineConfig.getMakeups()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(type,new Gson().toJson(htEyelineConfig));
                break;
            case 5:
                HtEyelashConfig htEyelashConfig = HtConfigTools.getInstance().getEyelashList();

                for (HtMakeup makeup : htEyelashConfig.getMakeups()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(type,new Gson().toJson(htEyelashConfig));
                break;
            case 6:
                    HtPupilsConfig htPupilsConfig = HtConfigTools.getInstance().getPupilsList();

                for (HtMakeup makeup : htPupilsConfig.getMakeups()) {
                    if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
                        makeup.setDownloaded(2);
                    }
                }
                HtConfigTools.getInstance().makeupDownload(type,new Gson().toJson(htPupilsConfig));
                break;

        }


    }
}
