package io.github.hashbox;

import io.github.hashbox.ga.*;
import io.github.hashbox.ga.chromosome.BinaryChromosome;
import io.github.hashbox.ga.crossover.MultiPointCut;
import io.github.hashbox.ga.mutation.UniformMutation;
import io.github.hashbox.ga.replacement.HighAndLowReplacement;
import io.github.hashbox.ga.selection.RouletteWheelSelection;
import io.github.hashbox.structure.Graph;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//
//    99        :  unweighted_50
//    358      :  unweighted_100
//    3314    :  unweighted_500
//    4743    :  weighted_500
//    9340    :  weighted_chimera_297

//chimera_946(31856)
//cubic_1000(896)
//planar_800(3064)
//random_1000(9524)
//toroidal_800(564)

// BEST
// K=500, P=1000, Noble(0.3), Multi(3), Uni(0.01), HighLow(1.0)

// unweight_50 : [99] K 1500 10000 ran multi(20) uni(0) highlow(0.1)
// unweight_100 : [357] K 1500 10000 ran multi(20) uni(0) highlow(0.1)

// unweight_500 : [3190] K 200 P 1000 ran multi(20) uni highlow(0.1)
// wiehgt_500: [4576] K 500 P 500 noble multi(20) uni(0.005) highlow(0.2)
// 키메라 : [8952] K 500 P 1000 noble multi(3) uni(0.015) highlow(0.7)

public class App {
    //    private static final String INPUT_FILE_PATH = "input/unweighted_500.txt";
//    private static final String INPUT_FILE_PATH = "input/weighted_500.txt";
//    private static final String INPUT_FILE_PATH = "input/weighted_chimera_297.txt";
    private static final String INPUT_FILE_PATH = "input/chimera_946.txt";                //31604
//    private static final String INPUT_FILE_PATH = "input/cubic_1000.txt";                 //872
//    private static final String INPUT_FILE_PATH = "input/planar_800.txt";                 //3043
//    private static final String INPUT_FILE_PATH = "input/random_500.txt";                 //4725
//    private static final String INPUT_FILE_PATH = "input/random_1000.txt";                //9424
//    private static final String INPUT_FILE_PATH = "input/toroidal_800.txt";               //546
    private static final boolean DEBUG = true;
//    private static final String INPUT_FILE_PATH = "maxcut.in";
    private static final String OUTPUT_FILE_PATH = "maxcut.out";

    private static final int K = 80;
    private static final int POPULATION = 100;
    private static final double SIMILAR_THRESHOLD = 0.95;
    private static final int LIMIT_TIME = 175 * 1000;

    public static void main(String[] args) {
        String inputPath = "";
        String outputPath = "";
        if (args.length < 1) {
            inputPath = INPUT_FILE_PATH;
            outputPath = OUTPUT_FILE_PATH;
        } else if (args.length == 2) {
            inputPath = args[0];
            outputPath = args[1];
        } else {
            System.out.println("잘못된 인자입니다.");
            System.exit(0);
        }

        Graph g = Utils.loadGraph(inputPath);
        Chromosome result = ga(g, outputPath);
//        Utils.saveMaxcutResult(outputPath, result.getScore(), result.getLocalSearchCnt());
//        Utils.saveResult(outputPath, result.getGroupNode(0));

//        List<String> input = Arrays.asList(
//                "input/chimera_946.txt",
//                "input/cubic_1000.txt",
//                "input/planar_800.txt",
//                "input/random_500.txt",
//                "input/random_1000.txt",
//                "input/toroidal_800.txt"
//        );
//        List<String> output = Arrays.asList(
//                "chimera946.out",
//                "cubic1000.out",
//                "planar800.out",
//                "random500.out",
//                "random1000.out",
//                "toroidal800.out"
//        );
//        List<Integer> localCnt = Arrays.asList(
//                30184,
//                26941,
//                19459,
//                22736,
//                5373,
//                62781
//        );

//        for (int i = 0; i < input.size(); i++) {
//            Graph g = Utils.loadGraph(input.get(i));
//            for (int j = 0; j < 5; j++) {
//                List<Chromosome> chromosomes = makePool(g.getNodeSize(), localCnt.get(i));
//                for (int k = 0; k < localCnt.get(i); k++) {
//
//                    Chromosome target = new BinaryChromosome(g.getNodeSize());
//                    target.doLocalSearch();
//                    if (max == null || target.getScore() > max.getScore()) {
//                        max = target;
//                        System.out.println(max.getScore());
//                    }
//                }
//                Chromosome max = chromosomes.stream()
//                        .max(Chromosome::compareTo).orElse(null);
//                System.out.println(max.getScore());
//                Utils.saveMaxcutResult(output.get(i), max.getScore(), 0);
//            }
//            System.out.println(input.get(i));
//        }
//        for (int i = 0; i < input.size(); i++) {
//            Graph g = Utils.loadGraph(input.get(i));
//            for (int j = 0; j < 30; j++) {
//                Chromosome result = ga(g, output.get(i));
//                Utils.saveMaxcutResult(output.get(i),result.getScore(), result.getLocalSearchCnt());
//            }
//            System.out.println(input.get(i));
//        }
    }

    public static Chromosome ga(Graph g, String name) {
        Map<String, Object> info = new HashMap<>();
        long startTime = System.currentTimeMillis();

        if (DEBUG) System.out.println(g);

        Selection selection = new RouletteWheelSelection(3);
        Crossover crossover = new MultiPointCut(1);
        Mutation mutation = new UniformMutation(0.015);
        Replacement replacement = new HighAndLowReplacement();

        Chromosome.resetLocalSearchCnt();
        List<Chromosome> chromosomes = makePool(g.getNodeSize(), POPULATION);
        List<Chromosome> newGeneration = new ArrayList<>(K);

        int generation = 0;
        long cnt;
        Chromosome max;
        do {
            generation++;
            newGeneration.clear();
            selection.sort(chromosomes);
            selection.init(chromosomes);
            for (int k = 0; k < K; k++) {
                selection.select(chromosomes);
                Chromosome o1 = selection.pickLeft();
                Chromosome o2 = selection.pickRight();
//                o1.doLocalSearch();
//                o2.doLocalSearch();
                Chromosome child = crossover.run(o1, o2);
                child = mutation.run(child);
                child.doLocalSearch();
                newGeneration.add(child);
            }
            chromosomes = replacement.run(chromosomes, newGeneration);
//            for (Chromosome chromosome : chromosomes) {
//                chromosome.doLocalSearch();
//            }

            max = chromosomes.stream()
                    .max(Chromosome::compareTo).get();
//            max.doLocalSearch();
            final Chromosome finalMax = max;
            cnt = chromosomes.stream()
                    .map(chromosome -> Utils.getCosineSimlarity(finalMax.getChromosome(), chromosome.getChromosome()))
                    .filter(sim -> sim > SIMILAR_THRESHOLD)
                    .count();
            if (info.isEmpty() || (int) info.get("score") < max.getScore()) {
                info.put("gen", generation);
                info.put("sim", (float) cnt / POPULATION);
                info.put("chromosome", max);
                info.put("score", max.getScore());
                info.put("avg", (double) chromosomes.stream().map(chromosome -> chromosome.getScore()).reduce((o1, o2) -> o1 + o2).orElse(0) / chromosomes.size());
                Utils.saveSequenceInfo(name + ".sequence", info);

                if (DEBUG) System.out.println("\t\t\t" + info);
            }
            if (generation % 25 == 0 && DEBUG) {
                System.out.println("Gen: " + generation + "\tSim: " + (float) cnt / POPULATION + "(" + cnt + "," + POPULATION + ")");
            }
        } while ((System.currentTimeMillis() - startTime) <= LIMIT_TIME);

        if (DEBUG) {
            System.out.println("=======RESULT=======");
            System.out.println("Gen: " + generation + "\tSim: " + (float) cnt / POPULATION + "(" + cnt + "," + POPULATION + ")");
            System.out.println(max.getChromosome());
            System.out.println("Score: " + max.getScore());
            System.out.println("Elapsed time: " + (System.currentTimeMillis() - startTime) / 1000 + "s");
            System.out.println("LocalCnt: " + max.getLocalSearchCnt());
        }

        return max;
    }

    public static List<Chromosome> makePool(int size, int population) {
        List<Chromosome> chromosomes = new ArrayList<>(population);

        for (int p = 0; p < population; p++) {
            Chromosome chromosome = new BinaryChromosome(size);
//            chromosome.doLocalSearch();
            chromosomes.add(chromosome);
//            if (p % 50 == 0) {
//                System.out.println(p + "/" + population + "\t\t" + (float)p/population);
//            }
        }
        return chromosomes;
    }
}
