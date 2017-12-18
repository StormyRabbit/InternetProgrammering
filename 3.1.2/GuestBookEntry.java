public class GuestBookEntry {
    private String name;
    private String email;
    private String homepage;
    private String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GuestBookEntry that = (GuestBookEntry) o;

        if (!name.equals(that.name)) return false;
        if (!email.equals(that.email)) return false;
        if (!homepage.equals(that.homepage)) return false;
        return message.equals(that.message);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + homepage.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n"
                + "Email: " + email + "\n"
                + "Hompage: " + homepage + "\n"
                + "Message: " + message + "\n\n";
    }
}
