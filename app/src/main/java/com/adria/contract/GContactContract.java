package com.adria.contract;

/**
 * Represents the View and Presenter contract
 */
public interface GContactContract {
    interface View {
    }

    interface Presenter {
        void setUpForPeopleAPI();
        void getCOnnections();
    }
}
