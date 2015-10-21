package com.puto.test;

import android.app.Instrumentation;
import android.test.InstrumentationTestCase;

/**
 * Created by GZK on 2015/10/10.
 */
public class TestCaseClass extends InstrumentationTestCase {
    @Override
    public Instrumentation getInstrumentation() {
        return super.getInstrumentation();
    }

    @Override
    public void injectInsrumentation(Instrumentation instrumentation) {
        super.injectInsrumentation(instrumentation);
    }

    @Override
    public void injectInstrumentation(Instrumentation instrumentation) {
        super.injectInstrumentation(instrumentation);
    }

    public TestCaseClass() {
        super();
    }

    @Override
    protected void runTest() throws Throwable {
        super.runTest();
    }

    @Override
    public void runTestOnUiThread(Runnable r) throws Throwable {
        super.runTestOnUiThread(r);
    }

    @Override
    public void sendKeys(int... keys) {
        super.sendKeys(keys);
    }

    @Override
    public void sendKeys(String keysSequence) {
        super.sendKeys(keysSequence);
    }

    @Override
    public void sendRepeatedKeys(int... keys) {
        super.sendRepeatedKeys(keys);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
