package Tiles;
public class TileTypeChoose {

    private String color;
    private String tileProperty;

    public String getColor(TileType tile){
        setTile(tile);
        return color;
    }
    public String getTile(TileType tile){
        setTile(tile);
        return tileProperty;
    } 
    
    public void setTile(TileType tile){

        switch(tile){
            case Ocean:
                color = "0,71,100";
                tileProperty = "Ocean";
                break;
            case Land:
                color = "75,122,71";
                tileProperty = "Forest";
                break;
            case Lake:
                color = "0,71,100";
                tileProperty = "Lake";
                break;
            case Aquifer:
                tileProperty = "Aquifer";
                break;
            default:
                color = "231,215,201";
                tileProperty = "null";
        }
    }
}
