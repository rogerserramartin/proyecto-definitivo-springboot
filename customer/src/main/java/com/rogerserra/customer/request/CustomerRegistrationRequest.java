package com.rogerserra.customer.request;

// with record we gain immutability with strings, equals and all that stuff
/*
Java records were introduced with the intention to be used as a fast way to create data carrier classes,
i.e. the classes whose objective is to simply contain data and carry it between modules

Los usamos porque no vamos a operar con estos datos, no se van a modificar, no se van a grabar en base de datos,
nada, solo llevan informacion.
Ademas los objetos inmutables consumen menos memoria y son mas rapidos, como el StringBuilder al concatenar
 */

// n many cases, this data is immutable, since immutability ensures the validity of the data without synchronization.
// https://www.baeldung.com/java-record-keyword))
public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
