package net.joobjoob.app.seoul_culture_api.Common;

/**
 * Created by kgh on 2018. 8. 11..
 */

//comment Consert Catagory Info Model
public class ConsertSubjectCatalogInfo {
    private String CODE;
    private String CODENAME;

    public ConsertSubjectCatalogInfo(String CODE, String CODENAME) {
        this.CODE = CODE;
        this.CODENAME = CODENAME;
    }

    public String getCODE() {
        return this.CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getCODENAME() {
        return this.CODENAME;
    }

    public void setCODENAME(String CODENAME) {
        this.CODENAME = CODENAME;
    }
}
