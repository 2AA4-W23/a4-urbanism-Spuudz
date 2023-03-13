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
            case Forest:
                color = "75,122,71";
                tileProperty = "Forest";
                break;
            case Lagoon:
                color = "55,198,255";
                tileProperty = "Lagoon";
                break;
            case Beach:
                color = "252,244,163";
                tileProperty = "Beach";
                break;
            default:
                color = "231,215,201";
                tileProperty = "null";
        }
    }
}
