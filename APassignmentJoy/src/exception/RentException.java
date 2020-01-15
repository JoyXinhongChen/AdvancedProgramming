package exception;

public class RentException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RentException(int i) {
		//possible exceptions when rent property
		if (i == 1) {
			new view.NewWindowForAlert("The apartment should not be rented for more than 28 days!");
		}

		if (i == 2) {
			new view.NewWindowForAlert("Due to the booking policy, please reserve a few more day");
		}
		
		if (i ==3 ) {
			new view.NewWindowForAlert("Rent date should not be a passed date");
		}
		
		if (i == 4) {
			new view.NewWindowForAlert("This Premium Suite needs to be maintained ");
		}

	}

}
