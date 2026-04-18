package com.StudentMS.StudentMS.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class TestStatusWatcher implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("PASSED  → " + context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println("FAILED  → " + context.getDisplayName());
        System.out.println("REASON : " + cause.getMessage());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.println("ABORTED → " + context.getDisplayName());
    }
}