package com.mehdi.crud;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        db.connect();
        // Instead of using Stack:
        List<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("name");
        fields.add("email");

        List<String> value1 = new ArrayList<>();
        value1.add("10");
        value1.add("Khalied");
        value1.add("Kehalid@Khaleid.com");
          List<String> value2 = new ArrayList<>();
        value2.add("9");
        value2.add("Mehdi");
        value2.add("mehdi@mehdi.com");

        List<List<String>> vals=new ArrayList<>();
        vals.add(value1 ) ;
        vals.add( value2);
        try {
//        new com.mehdi.crud.QueryBuilder().insertOne("users",fields,values);
//            new com.mehdi.crud.QueryBuilder().insertMany("users" , fields,vals);
//            new com.mehdi.crud.QueryBuilder().delete("users", "id", "=", "10");
//            List<String> fieldsToUpdate = List.of("name", "email");
    //            List<String> newValues = List.of("mehdi_bk", "mehdi@example.com");
    //
    //            new com.mehdi.crud.QueryBuilder().update("users", "id", "=", "2", fieldsToUpdate, newValues);
//            new com.mehdi.crud.FluentQuery().table("users").insertOne(fields,value1);
            FluentQuery fq = new FluentQuery();

            fq.table("users")
                    .insertOne(List.of( "name", "email"), List.of("mehdzi", "mehdzi@example.com"))
                    .execute();

            List<String> user = fq.table("users")
                    .readOne()
                    .execute()
                    .getResult();

            System.out.println(user);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(db.getColumnNames("users"));
            System.out.println(new QueryBuilder().readAll("users"));
            System.out.println(new QueryBuilder().readOne("users"));
        }catch (SQLException e){

        }
    }

}