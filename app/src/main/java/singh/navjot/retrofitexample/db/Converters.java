package singh.navjot.retrofitexample.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.room.TypeConverter;
import singh.navjot.retrofitexample.model.Address;
import singh.navjot.retrofitexample.model.Company;

public class Converters {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(Address optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Address>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter
    public Address toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Address>() {
        }.getType();
        Address productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

    public static class ConvertersCompnay {
        @TypeConverter // note this annotation
        public String fromOptionValuesList(Company optionValues) {
            if (optionValues == null) {
                return (null);
            }
            Gson gson = new Gson();
            Type type = new TypeToken<Company>() {
            }.getType();
            String json = gson.toJson(optionValues, type);
            return json;
        }

        @TypeConverter
        public Company toOptionValuesList(String optionValuesString) {
            if (optionValuesString == null) {
                return (null);
            }
            Gson gson = new Gson();
            Type type = new TypeToken<Company>() {
            }.getType();
            Company company = gson.fromJson(optionValuesString, type);
            return company;
        }
    }
}



