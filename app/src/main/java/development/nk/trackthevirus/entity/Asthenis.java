package development.nk.trackthevirus.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Created by NKdevelopment on 28/4/2020.
 */


public class Asthenis implements Parcelable {

    private long vat;
    private String name;
    private String surname;
    private String age;
    private String email;
    private String gender;
    private String mobile;
    private int number_of_contacts;
    private String phone_home;
    private String phone_work;
    private int highRiskContacts;
    private int lowRiskContacts;
    private int mediumRiskContacts;
    private String country;
    private double geo_lat;
    private double geo_lan;
    private String region;
    private String street_name;
    private String street_number;
    private String town;
    private String zip;
    private long date_long;
    private String paratiriseis;



    /************************* Constructors ***********************************/

    public Asthenis(long vat, String name, String surname, String age, String email, String gender,
                    String mobile, int number_of_contacts, String phone_home, String phone_work,
                    int highRiskContacts, int lowRiskContacts, int mediumRiskContacts, String country,
                    double geo_lat, double geo_lan, String region, String street_name,
                    String street_number, String town, String zip, long date_long, String paratiriseis) {
        this.vat = vat;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.mobile = mobile;
        this.number_of_contacts = number_of_contacts;
        this.phone_home = phone_home;
        this.phone_work = phone_work;
        this.highRiskContacts = highRiskContacts;
        this.lowRiskContacts = lowRiskContacts;
        this.mediumRiskContacts = mediumRiskContacts;
        this.country = country;
        this.geo_lat = geo_lat;
        this.geo_lan = geo_lan;
        this.region = region;
        this.street_name = street_name;
        this.street_number = street_number;
        this.town = town;
        this.zip = zip;
        this.date_long = date_long;
        this.paratiriseis = paratiriseis;

    }

    public Asthenis(long vat) {
        this.vat = vat;
    }

    public Asthenis() {
    }

    public Asthenis(String name) {
        this.name = name;
    }


    /********************************Getters and Setters implementation********************/

    public long getVat() {
        return vat;
    }

    public void setVat(long vat) {
        this.vat = vat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getNumber_of_contacts() {
        return number_of_contacts;
    }

    public void setNumber_of_contacts(int number_of_contacts) {
        this.number_of_contacts = number_of_contacts;
    }

    public String getPhone_home() {
        return phone_home;
    }

    public void setPhone_home(String phone_home) {
        this.phone_home = phone_home;
    }

    public String getPhone_work() {
        return phone_work;
    }

    public void setPhone_work(String phone_work) {
        this.phone_work = phone_work;
    }

    public int getHighRiskContacts() {
        return highRiskContacts;
    }

    public void setHighRiskContacts(int highRiskContacts) {
        this.highRiskContacts = highRiskContacts;
    }

    public int getLowRiskContacts() {
        return lowRiskContacts;
    }

    public void setLowRiskContacts(int lowRiskContacts) {
        this.lowRiskContacts = lowRiskContacts;
    }

    public int getMediumRiskContacts() {
        return mediumRiskContacts;
    }

    public void setMediumRiskContacts(int mediumRiskContacts) {
        this.mediumRiskContacts = mediumRiskContacts;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getGeo_lat() {
        return geo_lat;
    }

    public void setGeo_lat(double geo_lat) {
        this.geo_lat = geo_lat;
    }

    public double getGeo_lan() {
        return geo_lan;
    }

    public void setGeo_lan(double geo_lan) {
        this.geo_lan = geo_lan;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public long getDate_long() {
        return date_long;
    }

    public void setDate_long(long date_long) {
        this.date_long = date_long;
    }

    public String getParatiriseis() {
        return paratiriseis;
    }

    public void setParatiriseis(String paratiriseis) {
        this.paratiriseis = paratiriseis;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Asthenis)) return false;
        Asthenis asthenis = (Asthenis) o;
        return getVat() == asthenis.getVat() &&
                getNumber_of_contacts() == asthenis.getNumber_of_contacts() &&
                getHighRiskContacts() == asthenis.getHighRiskContacts() &&
                getLowRiskContacts() == asthenis.getLowRiskContacts() &&
                getMediumRiskContacts() == asthenis.getMediumRiskContacts() &&
                Objects.equals(getName(), asthenis.getName()) &&
                Objects.equals(getSurname(), asthenis.getSurname()) &&
                Objects.equals(getAge(), asthenis.getAge()) &&
                Objects.equals(getEmail(), asthenis.getEmail()) &&
                Objects.equals(getGender(), asthenis.getGender()) &&
                Objects.equals(getMobile(), asthenis.getMobile()) &&
                Objects.equals(getPhone_home(), asthenis.getPhone_home()) &&
                Objects.equals(getPhone_work(), asthenis.getPhone_work()) &&
                Objects.equals(getCountry(), asthenis.getCountry()) &&
                Objects.equals(getGeo_lat(), asthenis.getGeo_lat()) &&
                Objects.equals(getGeo_lan(), asthenis.getGeo_lan()) &&
                Objects.equals(getRegion(), asthenis.getRegion()) &&
                Objects.equals(getStreet_name(), asthenis.getStreet_name()) &&
                Objects.equals(getStreet_number(), asthenis.getStreet_number()) &&
                Objects.equals(getTown(), asthenis.getTown()) &&
                Objects.equals(getZip(), asthenis.getZip()) &&
                Objects.equals(getDate_long(), asthenis.getDate_long()) &&
                Objects.equals(getParatiriseis(), asthenis.getParatiriseis());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVat(), getName(), getSurname(), getAge(), getEmail(), getGender(), getMobile(),
                getNumber_of_contacts(), getPhone_home(), getPhone_work(), getHighRiskContacts(), getLowRiskContacts(),
                getMediumRiskContacts(), getCountry(), getGeo_lat(), getGeo_lan(), getRegion(), getStreet_name(),
                getStreet_number(), getTown(), getZip(), getDate_long(), getParatiriseis());
    }

    @Override
    public String toString() {
        return "Asthenis{" +
                "vat=" + vat +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age='" + age + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", mobile='" + mobile + '\'' +
                ", number_of_contacts=" + number_of_contacts +
                ", phone_home='" + phone_home + '\'' +
                ", phone_work='" + phone_work + '\'' +
                ", highRiskContacts=" + highRiskContacts +
                ", lowRiskContacts=" + lowRiskContacts +
                ", mediumRiskContacts=" + mediumRiskContacts +
                ", country='" + country + '\'' +
                ", geo_lat='" + geo_lat + '\'' +
                ", geo_lan='" + geo_lan + '\'' +
                ", region='" + region + '\'' +
                ", street_name='" + street_name + '\'' +
                ", street_number='" + street_number + '\'' +
                ", town='" + town + '\'' +
                ", zip='" + zip + '\'' +
                ", date_long='" + date_long + '\'' +
                ", paratiriseis='" + paratiriseis + '\'' +
                '}';
    }

    /********************************Parcelable implementation********************/

    private Asthenis(Parcel in) {
        this.vat = in.readLong();
        this.name = in.readString();
        this.surname = in.readString();
        this.age = in.readString();
        this.email = in.readString();
        this.gender = in.readString();
        this.mobile = in.readString();
        this.number_of_contacts = in.readInt();
        this.phone_home = in.readString();
        this.phone_work = in.readString();
        this.highRiskContacts = in.readInt();
        this.lowRiskContacts = in.readInt();
        this.mediumRiskContacts = in.readInt();
        this.country = in.readString();
        this.geo_lat = in.readDouble();
        this.geo_lan = in.readDouble();
        this.region = in.readString();
        this.street_name = in.readString();
        this.street_number = in.readString();
        this.town = in.readString();
        this.zip = in.readString();
        this.date_long = in.readLong();
        this.paratiriseis = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.vat);
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeString(this.age);
        dest.writeString(this.email);
        dest.writeString(this.gender);
        dest.writeString(this.mobile);
        dest.writeInt(this.number_of_contacts);
        dest.writeString(this.phone_home);
        dest.writeString(this.phone_work);
        dest.writeInt(this.highRiskContacts);
        dest.writeInt(this.lowRiskContacts);
        dest.writeInt(this.mediumRiskContacts);
        dest.writeString(this.country);
        dest.writeDouble(this.geo_lat);
        dest.writeDouble(this.geo_lan);
        dest.writeString(this.region);
        dest.writeString(this.street_name);
        dest.writeString(this.street_number);
        dest.writeString(this.town);
        dest.writeString(this.zip);
        dest.writeLong(this.date_long);
        dest.writeString(this.paratiriseis);
    }

    public static final Creator<Asthenis> CREATOR = new Creator<Asthenis>() {
        @Override
        public Asthenis createFromParcel(Parcel source) {
            return new Asthenis(source);
        }

        @Override
        public Asthenis[] newArray(int size) {
            return new Asthenis[size];
        }
    };


}
