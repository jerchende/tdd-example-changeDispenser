package com.cubic.coin;

import lombok.AllArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class ChangeDispenser {

    private final DenominationsRetriever denominationsRetriever;
    private final MonetaryItemDispenser monetaryItemDispenser;


    public void dispensesChange(int amount) {
        if (amount < 0) throw new IllegalStateException("Negative Values not supported, bitch");

        List<Integer> possibleDenominations = new LinkedList<>(denominationsRetriever.getValidDenominations());

        while (amount > 0) {
            Integer highestCoin = highestCoin(possibleDenominations);
            if (amount >= highestCoin) {
                monetaryItemDispenser.dispense(highestCoin);
                amount -= highestCoin;
            } else {
                possibleDenominations.remove(highestCoin);
            }
        }
    }

    private Integer highestCoin(List<Integer> coins) {
        return coins.stream().max(Integer::compare).orElseThrow(() -> new IllegalStateException("No more coin left"));
    }
}
