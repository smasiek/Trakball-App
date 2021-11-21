package com.momot.trakball.repository;

import com.momot.trakball.dao.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SearchRepository extends Repository<Place,Long> {

    @Query("select distinct p.city from Place p where lower(p.city) LIKE lower(concat('%', concat(:cityInput, '%'))) ")
    Iterable<String> findCities(@Param("cityInput") String cityInput);

    @Query("select distinct p.city from Place p where lower(p.city) LIKE lower(concat('%', concat(:cityInput, '%'))) " +
            "and lower(p.street) LIKE lower(:streetInput) " +
            "and lower(p.name) LIKE lower(:placeInput) ")
    Iterable<String> findCitiesWithStreetAndPlace(@Param("cityInput")String city, @Param("streetInput") String street,@Param("placeInput") String place);

    @Query("select distinct p.city from Place p where lower(p.city) LIKE lower(concat('%', concat(:cityInput, '%'))) " +
            "and lower(p.street) LIKE lower(:streetInput) ")
    Iterable<String> findCitiesWithStreet(@Param("cityInput") String city, @Param("streetInput") String street);

    @Query("select distinct p.city from Place p where lower(p.city) LIKE lower(concat('%', concat(:cityInput, '%'))) " +
            "and lower(p.name) LIKE lower(:placeInput) ")
    Iterable<String> findCitiesWithPlace(@Param("cityInput") String city,@Param("placeInput") String place);


    @Query("select distinct p.street from Place p where lower(p.street) LIKE lower(concat('%', concat(:streetInput, '%'))) ")
    Iterable<String> findStreets(@Param("streetInput") String street);

    @Query("select distinct p.street from Place p where lower(p.street) LIKE lower(concat('%', concat(:streetInput, '%'))) " +
            "and lower(p.name) LIKE lower(:placeInput) " +
            "and lower(p.city) LIKE lower(:cityInput) ")
    Iterable<String> findStreetsWithCityAndPlace( @Param("streetInput") String street,@Param("cityInput")String city,@Param("placeInput") String place);

    @Query("select distinct p.street from Place p where lower(p.street) LIKE lower(concat('%', concat(:streetInput, '%'))) " +
            "and lower(p.city) LIKE lower(:cityInput) ")
    Iterable<String> findStreetsWithCity( @Param("streetInput") String street,@Param("cityInput") String city);

    @Query("select distinct p.street from Place p where lower(p.street) LIKE lower(concat('%', concat(:streetInput, '%'))) " +
            "and lower(p.name) LIKE lower(:placeInput) ")
    Iterable<String> findStreetsWithPlace( @Param("streetInput") String street,@Param("placeInput") String place);


    @Query("select distinct p.name from Place p where lower(p.name) LIKE lower(concat('%', concat(:placeInput, '%'))) ")
    Iterable<String> findPlaces(@Param("placeInput") String place);

    @Query("select distinct p.name from Place p where lower(p.name) LIKE lower(concat('%', concat(:placeInput, '%'))) " +
            "and lower(p.street) LIKE lower(:streetInput) " +
            "and lower(p.city) LIKE lower(:cityInput) ")
    Iterable<String> findPlacesWithCityAndStreet( @Param("placeInput") String place,@Param("cityInput")String city,@Param("streetInput") String street);

    @Query("select distinct p.name from Place p where lower(p.name) LIKE lower(concat('%', concat(:placeInput, '%'))) " +
            "and lower(p.city) LIKE lower(:cityInput) ")
    Iterable<String> findPlacesWithCity( @Param("placeInput") String place,@Param("cityInput") String city);

    @Query("select distinct p.name from Place p where lower(p.name) LIKE lower(concat('%', concat(:placeInput, '%'))) " +
            "and lower(p.street) LIKE lower(:streetInput) ")
    Iterable<String> findPlacesWithStreet( @Param("placeInput") String place,@Param("streetInput") String street);

    @Query("select distinct p.name from Place p where lower(p.city) LIKE lower(concat('%', concat(:cityInput, '%'))) " +
            "and lower(p.street) LIKE lower(:streetInput) ")
    Iterable<String> findPlacesWithCityAndStreetWithoutPlace( @Param("cityInput") String city,@Param("streetInput") String street);
}
