
# Assignment A2: Mesh Generator

  - Dylan Garner [garned3@mcmaster.ca]
  - Harman Bassi [bassih3@mcmaster.ca]
  - Matthew Bradbury [bradbm1@mcmaster.ca]

## How to install?

```
mosser@azrael A2 % mvn install
```

It creates two jars:

  1. `generator/generator.jar` to generate meshes
  2. `visualizer/visualizer.jar` to visualize such meshes as SVG files
## Examples of execution

### Generating a mesh, grid or irregular

```
mosser@azrael A2 % java -jar generator/generator.jar -k grid -h 1080 -w 1920 -p 1000 -s 20 -o img/grid.mesh
mosser@azrael A2 % java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1000 -s 20 -o img/irregular.mesh
java -jar generator/target/2aa4.mesh.generator-jar-with-dependencies.jar -k irregular -h 1080 -w 1920 -p 1000 -s 20 -o img/irregular.mesh
```

One can run the generator with `-help` as option to see the different command line arguments that are available

### Visualizing a mesh, (regular or debug mode)

```
mosser@azrael A2 % java -jar visualizer/visualizer.jar -i img/grid.mesh -o img/grid.svg          
mosser@azrael A2 % java -jar visualizer/visualizer.jar -i img/grid.mesh -o img/grid_debug.svg -x
mosser@azrael A2 % java -jar visualizer/visualizer.jar -i img/irregular.mesh -o img/irregular.svg   
mosser@azrael A2 % java -jar visualizer/visualizer.jar -i img/irregular.mesh -o img/irregular_debug.svg -x
java -jar visualizer/target/2aa4.mesh.visualizer-jar-with-dependencies.jar -i img/island.mesh -o img/island.svg
```

### Generating an Island
java -jar island/target/2aa4.mesh.island-jar-with-dependencies.jar -i img/irregular.mesh -o img/island.mesh

Note: PDF versions of the SVG files were created with `rsvg-convert`.

## Backlog

### Definition of Done

Functional features that have been properly tested.

### Product Backlog

| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| F01   |  Draw Segments Between Vertices with color being average of vertices |  Matthew, Dylan, Harman    |  01/02/23  | 08/02/23 | D |
| F02   |  Creating Mesh ADT | Matthew, Dylan | 09/02/23 | 15/02/23 | D |
| F03   | Producing full meshes | Dylan, Harman | 09/02/23 | 15/02/23 | D |
| F04   | Playing with rendering (color and thickness for polygons) | Matthew, Harman | 17/02/23 | 17/02/23 | D | 
| F05   | Visualization Mode | Matthew | 17/02/23 | 25/02/23 | D |
| F06   | Generate Irregular Grid | Dylan |20/02/23 |22/02/23 | D |
| F07   | Compute Neighborhood Relations | Harman | 23/02/23 | 23/02/23 | D |
| F08   | Control Generation through Command Line | Matthew | 23/02/23 | 24/02/23 | D |
| F09 (MVP) | Create Lagoon Island  | All | 06/03/23 | | S |
| F10 | Different island shapes | All | | | B (F09) |
| F11 | elevation profiles | Dylan | | | B (F10) |
| F12 | Lake Generation | Matthew/Dylan | | | B(F11) |
| F13 | Rivers/Rivers Flow | Dylan | | | B(F11) |
| F14 | Aquifers | Matthew | | | B(F10) |
| F15 | Soil Absorption | Matthew | | | B(F14, F13, F12) | 
| F16 | Biomes | Harman | | | B(F17) |
| F17 | Whittaker Diagrams | Harman | | | B(10) |
| F18 | Reproducability | All | | | B(F10-17) |  



