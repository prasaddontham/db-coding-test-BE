package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class TradingStrategyTest {

    TradingStrategy tradingStrategy;
    @Mock
    ExecutionService executionServiceBroker;

    @Before
    public void init() {
        tradingStrategy = new TradingStrategy(executionServiceBroker, 150);
        tradingStrategy.setBuyThreshold("IBM", 45.0);
        tradingStrategy.setSellThreshold("IBM", 545.0);
    }

    @Test
    public void testPriceUpdate() {
        tradingStrategy.priceUpdate("IBM", 46.0);

        Mockito.verify(executionServiceBroker, Mockito.times(0)).buy(anyString(), anyDouble(), anyInt());
        tradingStrategy.priceUpdate("IBM", 44.0);

        Mockito.verify(executionServiceBroker, Mockito.times(1)).buy("IBM", 44.0, 150);
    }
}