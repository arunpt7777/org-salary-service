package com.motta.salary_service.util;

public class SalaryConstants {
    public static final String LOG_MESSAGE_SALARY_NOT_FOUND = "Salary id not found. Please enter different id: {}";
    public static final String LOG_MESSAGE_SALARY_PERSISTED = "Salary id = {} persisted";
    public static final String LOG_MESSAGE_SALARY_UPDATE_FAILED = "Updating scheme id = {} has failed.";
    public static final String LOG_MESSAGE_SALARY_DELETE_FAILED = "Deleting scheme id = {} has failed.";
    public static final String LOG_FETCHING_ASSOCIATIONS_FAILED = "Failed fetching associations for scheme Id.";
    public static final String LOG_MESSAGE_SALARY_ALREADY_EXISTS = "Salary id = {} already Exists!";
    public static final String LOG_MESSAGE_ATTENDANCE_NOT_FOUND = "No attendance records found!";


    public static final String EXCEPTION_MESSAGE_SALARY_ALREADY_EXISTS = "Salary id = {} already Exists!";
    public static final String EXCEPTION_MESSAGE_SALARY_NOT_FOUND = "Salary id = {} already Exists!";
    public static final String EXCEPTION_MESSAGE_EMPLOYEE_NOT_FOUND = "Salary id = {} not found Exists!";
    public static final String EXCEPTION_SALARY_ID_MANDATORY = "Salary id is mandatory";
    public static final String EXCEPTION_CURRENCY_MANDATORY = "Currency is mandatory";
    public static final String EXCEPTION_SALARY_PER_DAY_MANDATORY = "Salary per Day is mandatory";
    public static final String EXCEPTION_SALARY_PER_DAY_BELOW_MIN_INVALID = "Salary per Day is less than minimum value";
    public static final String EXCEPTION_TOTAL_SALARY_BELOW_MIN_INVALID = "Salary per Day is less than minimum value";
    public static final String EXCEPTION_SALARY_ID_INVALID = "Salary Id must not be less than the initial value of: ";


    public static final String URL_GET_SALARY_BY_EMPLOYEE_BY_ID = "http://localhost:8080/employees/";
    public static final String URL_GET_ATTENDANCE_BY_EMPLOYEE_ID = "http://localhost:9000/attendancesByEmployeeId/";

}
