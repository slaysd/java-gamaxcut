package io.github.hashbox.ga.selection;

import io.github.hashbox.ga.Chromosome;
import io.github.hashbox.ga.Selection;

import java.util.List;

/**
 * 한 세대는 2명이 다 만든다!!
 */
public class HighOrderSelection extends Selection {

    @Override
    public List<Chromosome> select(List<Chromosome> chromosomes) {
        this.left = chromosomes.get(0);
        this.right = chromosomes.get(1);

        return chromosomes;
    }
}
