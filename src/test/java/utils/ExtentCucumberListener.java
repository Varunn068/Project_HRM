package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

import java.util.HashMap;
import java.util.Map;

public class ExtentCucumberListener implements ConcurrentEventListener {

    private static ExtentReports extent;
    private static Map<String, ExtentTest> featureMap = new HashMap<>();
    private static ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, this::runStarted);
        publisher.registerHandlerFor(TestCaseStarted.class, this::scenarioStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::stepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::scenarioFinished);
        publisher.registerHandlerFor(TestRunFinished.class, this::runFinished);
    }

    private void runStarted(TestRunStarted event) {
        extent = ExtentReportManager.createInstance("test-output/ExtentReport.html");
    }

    private void scenarioStarted(TestCaseStarted event) {
        // Extract feature name
        String uri = event.getTestCase().getUri().toString();
        String[] parts = uri.split("/");
        String featureName = parts[parts.length - 1].replace(".feature", "");

        // Create feature node only once per feature
        ExtentTest feature = featureMap.get(featureName);
        if (feature == null) {
            feature = extent.createTest(featureName);
            featureMap.put(featureName, feature);
        }

        // Create scenario node
        ExtentTest scenario = feature.createNode(event.getTestCase().getName());
        scenarioTest.set(scenario);

        // Assign tags as categories
        for (String tag : event.getTestCase().getTags()) {
            scenario.assignCategory(tag);
        }
    }

    private void stepFinished(TestStepFinished event) {
        // Only process Gherkin steps
        if (!(event.getTestStep() instanceof PickleStepTestStep)) {
            return;
        }

        PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
        String stepText = step.getStep().getText();
        Status logStatus;

        // Map Cucumber Status → Extent Status
        switch (event.getResult().getStatus()) {
            case PASSED:
                logStatus = Status.PASS;
                break;
            case FAILED:
                logStatus = Status.FAIL;
                break;
            case SKIPPED:
                logStatus = Status.SKIP;
                break;
            case PENDING:
                logStatus = Status.WARNING;
                break;
            default:
                logStatus = Status.INFO;
        }

        // Log step
        ExtentTest scenario = scenarioTest.get();
        if (scenario != null) {
            scenario.log(logStatus, stepText);

            // If failed, attach error
            if (event.getResult().getError() != null) {
                scenario.fail(event.getResult().getError());
            }
        }
    }

    private void scenarioFinished(TestCaseFinished event) {
        ExtentTest scenario = scenarioTest.get();

        if (scenario != null) {
            switch (event.getResult().getStatus()) {
                case PASSED:
                    scenario.pass(MarkupHelper.createLabel("Scenario PASSED", ExtentColor.GREEN));
                    break;

                case FAILED:
                    scenario.fail(MarkupHelper.createLabel("Scenario FAILED", ExtentColor.RED));
                    break;

                case SKIPPED:
                    scenario.skip(MarkupHelper.createLabel("Scenario SKIPPED", ExtentColor.ORANGE));
                    break;

                default:
                    scenario.info("Scenario finished with status: " + event.getResult().getStatus());
            }
        }

        // Clean up ThreadLocal to prevent memory leaks
        scenarioTest.remove();
    }

    private void runFinished(TestRunFinished event) {
        if (extent != null) {
            extent.flush();
        }
        // Clean up static maps
        featureMap.clear();
    }
}