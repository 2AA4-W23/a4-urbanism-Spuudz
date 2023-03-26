
# Assignment A2: Mesh Generator

  - Dylan Garner [garned3@mcmaster.ca]
  - Harman Bassi [bassih3@mcmaster.ca]
  - Matthew Bradbury [bradbm1@mcmaster.ca]

## How to install?

```
mosser@azrael A2 % mvn install
```

It creates three jars:

  1. `generator/generator.jar` to generate meshes
  2. `island/island.jar` to create an island on the mesh
  3. `visualizer/visualizer.jar` to visualize such meshes as SVG files
## Examples of execution

### Generating a mesh, grid or irregular

```
java -jar generator/target/2aa4.mesh.generator-jar-with-dependencies.jar -k irregular -h 500 -w 500 -p 1000 -s 20 -o img/input.mesh
java -jar generator/target/2aa4.mesh.generator-jar-with-dependencies.jar -k grid -h 500 -w 500 -p 1000 -s 20 -o img/input.mesh
```

One can run the generator with `-help` as option to see the different command line arguments that are available

### Generating an Island
```
java -jar island/target/2aa4.mesh.island-jar-with-dependencies.jar -i img/input.mesh -o img/island.mesh -shape circle -altitude volcano
java -jar island/target/2aa4.mesh.island-jar-with-dependencies.jar -i img/input.mesh -o img/island.mesh -shape crescent -altitude volcano

```
### Necessary Command Line Arguments for Island
```
-shape : circle OR square OR crescent
-altitude : volcano OR hills OR plateau
-lakes : number of lakes
-aquifers : number of aquifers
-rivers : number of rivers
-soil : arid OR temperate OR moist
-biome : arctic OR desert OR jungle
-i input file name
-0 output mesh name

```

### Visualizing a mesh, (regular or debug mode)

```
java -jar visualizer/target/2aa4.mesh.visualizer-jar-with-dependencies.jar -i img/island.mesh -o img/island.svg
java -jar visualizer/target/2aa4.mesh.visualizer-jar-with-dependencies.jar -i img/island.mesh -o img/island.svg -x
```

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
| F09 (MVP) | Create Lagoon Island  | All | 06/03/23 | 12/03/23 | D |
| F10 | Different island shapes | All | 12/03/23 | 22/03/23 | F |
| F11 | elevation profiles | Dylan | 3/17/23 | 3/22/23 | D |
| F12 | Lake Generation | Matthew | 16/03/23 | 25/03/23 | D |
| F13 | Rivers/Rivers Flow | Dylan | 24/03/23 | | S |
| F14 | Aquifers | Matthew | 17/03/23 | 23/03/23 | D |
| F15 | Soil Absorption | Matthew | 23/03/23 | 26/03/23 | D | 
| F16 | Biomes | Harman | | | B(F17) |
| F17 | Whittaker Diagrams | Harman | 21/03/23 | 26/03/23 | D |
| F18 | Reproducability | All | | | B(F10-17) |  



