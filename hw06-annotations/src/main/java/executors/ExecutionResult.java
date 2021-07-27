package executors;

class ExecutionResult {

    private final TestExecutionResult result;
    private final String methodName;

    public ExecutionResult(TestExecutionResult result, String methodName) {
        this.result = result;
        this.methodName = methodName;
    }

    public boolean isPassed() {
        return this.result.equals(TestExecutionResult.PASSED);
    }

    @Override
    public String toString() {
        return this.methodName + " : " + this.result.name();
    }

}
