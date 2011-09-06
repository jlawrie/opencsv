package au.com.bytecode.opencsv.bean;

/**
 * Created by IntelliJ IDEA.
 * User: sconway
 * Date: 9/5/11
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockUserBean {
    public static final String DEFAULT_BLANK_FIELD = "";
    private String profile_id = "";
    private String email = "";
    private String secondary_email = "";
    private String first_name = DEFAULT_BLANK_FIELD;
    private String last_name = DEFAULT_BLANK_FIELD;

    public String getProfile_Id() {
        return profile_id;
    }

    public void setProfile_Id(String userId) {
        this.profile_id = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_Name() {
        return first_name;
    }

    public void setFirst_Name(String firstName) {
        this.first_name = firstName;
    }

    public String getLast_Name() {
        return last_name;
    }

    public void setLast_Name(String lastName) {
        this.last_name = lastName;
    }

    public String toString() {
        String userId = isEmpty(getProfile_Id()) ? "" : getProfile_Id().trim();
        String firstname = isEmpty(getFirst_Name()) ? "" : getFirst_Name().trim();
        String lastname = isEmpty(getLast_Name()) ? "" : getLast_Name().trim();
        String email = isEmpty(getEmail()) ? "" : getEmail().trim();
        StringBuffer value = new StringBuffer();
        value.append(",user id:" + userId);
        value.append(",email:" + email);
        value.append(",first name:" + firstname);
        value.append(",last name:" + lastname);

        return value.toString();
    }

    public boolean equals(Object obj) {
        if ((null == obj) || !(obj instanceof MockUserBean)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        return ((MockUserBean) obj).toString().equalsIgnoreCase(this.toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean isFirstNameEmpty() {
        return isNameEmpty(getFirst_Name());
    }

    public boolean isLastNameEmpty() {
        return isNameEmpty(getLast_Name());
    }

    private boolean isNameEmpty(String name) {
        if (name == null || name.isEmpty() || DEFAULT_BLANK_FIELD.equals(name)) {
            return true;
        }

        return false;
    }

    public void setSecondary_Email(String secondaryEmail) {
        this.secondary_email = secondaryEmail;
    }

    public String getSecondary_Email() {
        return secondary_email;
    }

    /**
     * This method simply check if a String is null or blank
     *
     * @param value
     * @return
     */
    public boolean isEmpty(final String value) {
        if ((null == value) || (value.trim().length() == 0)) {
            return true;
        }

        return false;
    }

}
