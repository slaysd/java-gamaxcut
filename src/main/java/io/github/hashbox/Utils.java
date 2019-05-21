package io.github.hashbox;

import io.github.hashbox.structure.Edge;
import io.github.hashbox.structure.Graph;
import io.github.hashbox.structure.Node;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Utils {
    public static Graph loadGraph(String filePath) {
        Optional<Integer> nV = Optional.empty();
        Optional<Integer> nE = Optional.empty();

        File file = new File(filePath);
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("파일이 없습니다.");
            e.printStackTrace();
        }

        Graph myGraph;
        nV = Optional.ofNullable(scan.nextInt());
        nE = Optional.ofNullable(scan.nextInt());
        myGraph = Graph.getInstance(nV.get(), nE.get());
        while (scan.hasNextLine()) {
            if (!scan.hasNext()) break;

            int node1_id = scan.nextInt();
            int node2_id = scan.nextInt();
            int weight = scan.nextInt();
            Node node1 = myGraph.getNode(node1_id).orElse(new Node(node1_id));
            Node node2 = myGraph.getNode(node2_id).orElse(new Node(node2_id));
            myGraph.addNode(node1);
            myGraph.addNode(node2);
            myGraph.addEdge(new Edge(weight, node1, node2));
        }

        return myGraph;
    }

    public static double getCosineSimlarity(List<Integer> o1, List<Integer> o2) {
        double sumProduct = 0;
        double sumASq = 0;
        double sumBSq = 0;
        for (int i = 0; i < o1.size(); i++) {
            sumProduct += o1.get(i) * o2.get(i);
            sumASq += Math.pow(o1.get(i), 2);
            sumBSq += Math.pow(o2.get(i), 2);
        }
        if (sumASq == 0 && sumBSq == 0) {
            return 2.0;
        }
        return sumProduct / (Math.sqrt(sumASq) * Math.sqrt(sumBSq));
    }

    public static void saveResult(String path, List<Integer> value) {
        File file = new File(path);

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            String result = value.stream().map(target -> String.valueOf(target)).collect(Collectors.joining(" "));
            pw.println(result);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSequenceInfo(String path, Map<String, Object> value) {
        File file = new File(path);

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            pw.println(value);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMaxcutResult(String path, int value, int localSearchCnt) {
        File file = new File(path);

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            pw.println(value + "\t" + localSearchCnt);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
