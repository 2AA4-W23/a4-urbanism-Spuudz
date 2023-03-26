package importer;
import IslandADT.*;

public interface importInterface {
    public Island convert();
    public void buildVertices();
    public void buildEdges();
    public void buildTiles();
}
