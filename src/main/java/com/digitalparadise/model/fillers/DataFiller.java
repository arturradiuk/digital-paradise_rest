package com.digitalparadise.model.fillers;

import java.util.List;

public interface DataFiller <T> {
    //    public void Fill(List<Good> goodList);
    public List<T> Fill();
}
