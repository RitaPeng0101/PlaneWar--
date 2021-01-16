/**
 * class Trophy是成就类，用来记录用户所有的成就
 * @author xyh
 *
 */
public class Trophy {
    String trophyName = "";
    boolean hasTrophy = false;
    public Trophy(String name) {
    	trophyName = name;
    }
    public String getTrophyName() {
    	return trophyName;
    }
    public void setTrophyTrue() {
    	hasTrophy = true;
    }
    public boolean hasTrophy() {
    	return hasTrophy;
    }
    public void showTrophy() {
    	System.out.println("您已经获得'" + trophyName + "'的称号!");
    }
    
}