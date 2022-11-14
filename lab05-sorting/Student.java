
// lam the sole author of the work in this repository.
public class Student {

	protected String name;
	protected String address;
	protected long campusPhone;
	protected int suBoxNum;
	protected long homePhone;

	/**
	 * Constructor to initialize all the student protected fields.
	 * @param fullname    Name of student.
	 * @param campAddress the campus address of the student.
	 * @param campPhone   The student campus phone number.
	 * @param suBox       The student su box number.
	 * @param cell        The student home phone number.
	 */
	public Student(String fullname, String campAddress, long campPhone, int suBox, long cell) {
		name = fullname;
		address = campAddress;
		campusPhone = campPhone;
		suBoxNum = suBox;
		homePhone = cell;
	}

	/**
	 * Function to get student name
	 * @return returns the student name.
	 * @post returns the name of the student.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Function to get student address
	 * @return the student address
	 * @post returns the student address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Function to get student campus phone.
	 * @return the student campus phone
	 * @post returns the student campus phone.
	 */
	public long getCampusPhone() {
		return campusPhone;
	}

	/**
	 * Function to get student SU box number.
	 * @return student su box number.
	 * @post returns the student SU box number
	 */
	public int getSuBoxNum() {
		return suBoxNum;
	}

	/**
	 * Function to get student home phone.
	 * @return the student home phone.
	 * @post returns the student home phone.
	 */
	public long getHomePhone() {
		return homePhone;
	}

	/**
	 * Function that returns String representation of the Student object.
	 * @return string representation of the object.
	 * @post returns string representation of the object.
	 */
	public String toString() {
		return "Name: " + getName() + " \n"
				+ "Address: " + getAddress() + " \n"
				+ "Campus Phone: " + String.valueOf(getCampusPhone()) + "\n"
				+ "Su Box Number: " + String.valueOf(getSuBoxNum()) + "\n"
				+ "Home Phone: " + String.valueOf(getHomePhone());
	}

}
