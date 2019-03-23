# Neuroevolution
This is a project I completed for my Introduction to Artificial Intelligence class at Indiana University. 
It uses a genetic algorithm to train feed-forward neural networks control a population of worm-like creatures to crawl toward a target.
To run the simulation, run Master.

## The Genetic Algorithm
The genetic algorithm is a two-parent weighted lottery selection with crossover and mutations. Fitness is evaluated based on how close 
each individual gets to the goal position in the simulation.

## The Physics
The creatures exist in the Box2D physics world simulation. They cannot interact with each other and they are driven by friction and impact 
physics.
