package ch.bziswiler.cube.model.address;

public class City {

    private String name;
    private String zip;
    private String state;
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final City city = (City) o;
        if (country != null ? !country.equals(city.country) : city.country != null) {
            return false;
        }
        if (name != null ? !name.equals(city.name) : city.name != null) {
            return false;
        }
        if (state != null ? !state.equals(city.state) : city.state != null) {
            return false;
        }
        if (zip != null ? !zip.equals(city.zip) : city.zip != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
