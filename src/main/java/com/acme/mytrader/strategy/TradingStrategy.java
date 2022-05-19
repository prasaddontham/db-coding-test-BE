package com.acme.mytrader.strategy;

import java.util.HashMap;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy implements PriceListener {

    private ExecutionService executionBroker;

    private Integer volume;
    private HashMap<String, Double> buyThresholds;
    private HashMap<String, Double> sellThresholds;

    public TradingStrategy(ExecutionService executionBroker, Integer volume) {
        this.executionBroker = executionBroker;
        this.buyThresholds = new HashMap<String, Double>();
        this.sellThresholds = new HashMap<String, Double>();
        this.volume = volume;
    }

    public void priceUpdate(String security, double price) {
        AutomateOrdersStrategy(security, price);
    }

    /**
     * @param security Security name
     * @param price    The current market price.
     */
    private void AutomateOrdersStrategy(String security, Double price) {
        Double buyThreshold = buyThresholds.get(security);
        Double sellThreshold = sellThresholds.get(security);
        if (buyThreshold != null && price < buyThreshold) {
            executionBroker.buy(security, price, getVolume());
        }
        if (sellThreshold != null && price > sellThreshold) {
            executionBroker.sell(security, price, getVolume());
        }
    }

    /**
     * @param security     Security name
     * @param buyThreshold below which a buy order to be processed.
     */
    public void setBuyThreshold(String security, Double buyThreshold) {
        buyThresholds.put(security, buyThreshold);
    }

    /**
     * @param security      ecurity (instrument)
     * @param sellThreshold bove which a buy order to be processed.
     */
    public void setSellThreshold(String security, Double sellThreshold) {
        sellThresholds.put(security, sellThreshold);
    }

    /**
     * @return The volume we would like to trade.
     */
    public Integer getVolume() {
        return volume;
    }
}
