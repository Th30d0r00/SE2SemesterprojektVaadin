package org.hbrs.se2.project.hellocar.util;

import com.vaadin.flow.component.Component;

public class Globals {
    public static String CURRENT_USER = "current_User";

    public static class Pages {
        public static final String SHOW_CARS = "show";
        public static final String ENTER_CAR = "enter";

        public static final String LOGIN_VIEW = "login";
        public static final String MAIN_VIEW = "";

        public static final String SHOW_JOBPOSTINGS = "jobpostings";

        public static final String SHOW_COMPANIES = "showcompanies";

        public static final String COMPANY_DETAILS = "companydetails";

        public static final String SHOW_MY_JOBPOSTINGS = "showmyjobpostings";
        public static final String JOB_APPLY = "jobapply";

        //public static final String SHOW_SEARCH = "suche";
        public static final String SHOW_APPLICATION_DETAILS = "applicationdetailview";

        public static final String SHOW_APPLICATIONS = "showapplications";
        public static final String EDIT_PROFILE = "editprofileview";
        public static final String SHOW_MY_APPLICATIONS = "showmyapplications";
    }

    public static class Roles {
        public static final String ADMIN = "admin";
        public static final String USER = "user";
    }

    public static class Errors {
        public static final String NOUSERFOUND = "nouser";
        public static final String SQLERROR = "sql";
        public static final String DATABASE = "database";
    }

}
