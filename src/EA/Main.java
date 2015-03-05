package EA;

public class Main {

}
/*
1. A population of potential solutions (i.e. individuals) all represented in some low-level
genotypic form such as binary vectors. [OK]

2. A developmental method for converting the genotypes into phenotypes. For this general
EA, a simple routine for converting a binary genotype into a list of integers will suffice.
This can be extended for future homework modules. [Genom => list of integers? /OK]

3. A fitness evaluation method that can be applied to all phenotypes. You will want to make
this a very modular component of your EA such that a wide variety of fitness functions
can be experimented with [OK]

4. All 3 of the basic protocols for adult selection described in the ea-appendices.pdf chapter
of the lecture notes. (Full, over-production and mixing) [OK]

5. A set (of at least 4) mechanisms (also described in ea-appendices.pdf) for parent/mate
selection. These 4 must include fitness-proportionate, sigma-scaling, and tournament
selection. [==]

6. The genetic operators of mutation and crossover. For this project, you only need to
define them for binary genomes (i.e. bit vectors). But your system should be able to
handle different representations for later problems. [==]

7. A basic evolutionary loop for running the EA through many generations of evolution [OK]

8. A logging routine that shows various data while the EA is running. This can be as simple
as printing to stdout/console. For each generation, this data should be logged: generation
number, best fitness, average fitness, standard deviation (SD) of fitness, phenotype of the
best individual.

9. A plotting routine that gives a visualization of an EA run. The plot should show how
the data from the logging changes from generation to generation: Generations on the X
axis, and then plot the best, average and SD of the fitness. For this, you can either use an
existing plotting library for your language, or just copy the logging data to a spreadsheet
program like Excel and create the plots there.
*/