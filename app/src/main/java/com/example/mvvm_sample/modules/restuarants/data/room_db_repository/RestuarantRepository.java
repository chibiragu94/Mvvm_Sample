package com.example.mvvm_sample.modules.restuarants.data.room_db_repository;

import android.os.AsyncTask;

import com.example.mvvm_sample.database.RestuarantDataBase;
import com.example.mvvm_sample.modules.restuarants.data.model.RestuarantDb;
import com.example.mvvm_sample.modules.restuarants.listener.OnRestuarantResponseTriggeredListener;

import java.util.ArrayList;
import java.util.List;

/*
 * Restuarant Repository which handles all Inserting and Fetching of data's from the local DB
 * */
public class RestuarantRepository {

    private RestuarantDao restuarantDao;
    private OnRestuarantResponseTriggeredListener onResponseTriggered;

    public RestuarantRepository(OnRestuarantResponseTriggeredListener onResponseTriggered) {
        RestuarantDataBase restuarantDataBase = RestuarantDataBase.getDatabase();
        restuarantDao = restuarantDataBase.restuarantDao();
        this.onResponseTriggered = onResponseTriggered;
    }

    public void insertRestuarants(RestuarantDb restuarantDb) {
        new InsertAsynTask(restuarantDao).execute(restuarantDb);
    }

    public void getAllRestuarants() {
        new FetchRestuarantAsynTask(restuarantDao, onResponseTriggered).execute();
    }

    public void deleteAllRestuarants()
    {

    }


    /*
     * ************************* AsynTask's class ************************************
     * */
    private static class InsertAsynTask extends AsyncTask<RestuarantDb, Void, Void> {
        private RestuarantDao restuarantDao;

        InsertAsynTask(RestuarantDao restuarantDao) {
            this.restuarantDao = restuarantDao;
        }

        @Override
        protected Void doInBackground(RestuarantDb... restuarantDbs) {
            restuarantDao.insertRestuarants(restuarantDbs[0]);
            return null;
        }
    }

    // Fetch Restuarants from Db
    private static class FetchRestuarantAsynTask extends AsyncTask<Void, Void, List<RestuarantDb>> {

        private RestuarantDao restuarantDao;

        private OnRestuarantResponseTriggeredListener onResponseTriggered;

        FetchRestuarantAsynTask(RestuarantDao restuarantDao, OnRestuarantResponseTriggeredListener onResponseTriggered) {
            this.restuarantDao = restuarantDao;
            this.onResponseTriggered = onResponseTriggered;
        }

        @Override
        protected List<RestuarantDb> doInBackground(Void... voids) {
            return new ArrayList<>(restuarantDao.loadAllRestuarants());
        }

        @Override
        protected void onPostExecute(List<RestuarantDb> restuarantDbList) {
            super.onPostExecute(restuarantDbList);
            onResponseTriggered.showRestuarantListFromDb(restuarantDbList);
        }
    }

}
