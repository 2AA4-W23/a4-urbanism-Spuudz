# Assignment A2: Mesh Generator

  - Dylan Garner [garned3@mcmaster.ca]
  - Harman Bassi [bassih3@mcmaster.ca]
  - Matthew Bradbury [bradbm1@mcmaster.ca]

## How to run the product
To run this project, first run "java -jar generator/target/2aa4.mesh.generator-jar-with-dependencies.jar generator/sample.mesh" in the command line with the relavent arguements. To see the format and possible configurations use the command line arguement -h or --help.

Next, run "java -jar visualizer/target/2aa4.mesh.visualizer-jar-with-dependencies.jar generator/sample.mesh visualizer/sample.svg" with either no command line argument (to see colored vertices and segments), or with the command line argument "-X" for debug mode.

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

