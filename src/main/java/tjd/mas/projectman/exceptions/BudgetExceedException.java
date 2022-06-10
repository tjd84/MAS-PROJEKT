package tjd.mas.projectman.exceptions;

public class BudgetExceedException extends Exception {
    public BudgetExceedException(String errorMessage){
        super(errorMessage);
    }
}
