package exception;

public class AddPropertyException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddPropertyException(int i) {
		//possible exceptions when add new property
		if (i==1) {
			new view.NewWindowForAlert("Repeated ID");			

		}
		
		if (i==2) {
			new view.NewWindowForAlert("Please input valid value");			

		}
		
		if (i==3) {
			new view.NewWindowForAlert("Please input a past date for Maintenance Date");			

		}
		
		if (i==4) {
			new view.NewWindowForAlert("Please input a valid date format");

		}
		
		if (i==5) {
			new view.NewWindowForAlert("Please input a valid bedroom number between 1 to 3");

		}
	}
}
