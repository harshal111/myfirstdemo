package pkg.android.rootways.cleaning;

/**
 * Created by NK on 11-09-2015.
 */


public class DrawerItems {

    String titel;
    String nmae;
    int image;
    String headerTitle;
    int headerIcon;
    String count="0";
    int jj;
    String add="";
    private boolean isCounterVisible = false;


    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public int getHeaderIcon() {
        return headerIcon;
    }

    public void setHeaderIcon(int headerIcon) {
        this.headerIcon = headerIcon;
    }

    public int getJj() {
        return jj;
    }

    public void setJj(int jj) {
        this.jj = jj;
    }

    public boolean getCounterVisibility(){
        return this.isCounterVisible;
    }

    public void setCounterVisibility(boolean isCounterVisible){
        this.isCounterVisible = isCounterVisible;
    }
    public DrawerItems() {
        // TODO Auto-generated constructor stub
    }
    public DrawerItems(String title) {
        // TODO Auto-generated constructor stub
        titel=title;

    }

    public DrawerItems(String name, int imag) {
        // TODO Auto-generated constructor stub
        nmae=name;
        image=imag;
    }

    public DrawerItems(String name, int imag, Boolean b, String mString) {
        // TODO Auto-generated constructor stub
        nmae=name;
        image=imag;
        count=mString;
    }
    public DrawerItems(String name, Boolean b, String mString) {
        // TODO Auto-generated constructor stub
        nmae=name;
        count=mString;
    }

    public DrawerItems(String name, int imag, String mString, String add) {
        // TODO Auto-generated constructor stub
        nmae=name;
        image=imag;
        count=mString;
        this.add=add;
    }

    public DrawerItems(String string, int i, int btnSearch) {
        // TODO Auto-generated constructor stub
        jj=i;
        headerTitle=string;
        headerIcon=btnSearch;

    }



    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }
    public String getTitel() {
        return titel;
    }
    public void setTitel(String titel) {
        this.titel = titel;
    }
    public String getNmae() {
        return nmae;
    }
    public void setNmae(String nmae) {
        this.nmae = nmae;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

}
